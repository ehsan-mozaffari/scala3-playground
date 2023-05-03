# Azure DevOps

Is a SaaS platform for entire DevOps lifecycles. 
Other names for Azure DevOps: Team Foundation Server and Visual Studio Team Service. So, DevOps means that anything
(concepts, tools, practices) that makes developing and releasing application FAST, AUTOMATED and HIGH-QUALITY.

There are work flows that you can have in the Azure:

## Boards
Azure provides you a Board section for planning your project before coding, and it supports Agile and Scrum.

## Repository
Hosting your codes in the Azure repos. In the repository there are multiple features like Code Reviews, Pull Requests,
Branch Policies for having high quality codes. There are multiple Git flows supported like: Centralized, 
Feature branching, Trunk-based, Git flow, etc.

## Pipeline
Azure pipeline is for implementing CI (Continuous Integration) like Test, Package and Deploy process. Pipeline can
be written with .yml files ,and you can have you pipeline script as part of you code.

Steps: Smallest building block of a pipeline for example: test, package, build docker image, push to docker repository.
A step can be either **script** or **task**

```yaml
steps:
  - script: sbt test
    displayName: Run unit tests
  - script: sbt package
    displayName: build jar file
  - script: docker build -t my-image:v1.0
    displayName: Build docker image
  - script: docker push my-image:v1.0
    displayName: Push docker image
```
Instead of writing script you can use task: in Azure you have lots of different predefined task that you could use for
different use cases. You can use them from Azure yml assistant.
```yaml
steps: 
  - task: DotNetCoreCLI@2 #it is for testing dot Net app like dotnet test
    inputs: 
      commands: 'test'
```
The above example was with a single job with multiple steps. But sometimes we need more than a job.
So Job is a group of multiple steps. and we could write the above like the following:

```yaml
jobs:
- job: Test and Build
  steps:
  - script: sbt test
    displayName: Run unit tests
  - script: sbt package
    displayName: build jar file
  - script: docker build -t my-image:v1.0
    displayName: Build docker image
  - script: docker push my-image:v1.0
    displayName: Push docker image
```
We could run jobs on multiple environment called agent. Agent is computing infrastructure with agent software install
on it. So, you could get one of the agent machines from agent pool for example from windows or linux or macOS pools.
So, that's why we have to define **pool** section in the job to define what kind of machine we want to dedicate for that job.

```yaml
jobs:
- job: Run on Windows
  pool: 
    vmImage: 'windows-latest'
  steps:
  - scripts: dotnet test
    displayName: Run unit tests
- job: Run on Linux
  pool:
    vmImage: 'linux-latest'
  steps:
    - scripts: dotnet test
      displayName: Run unit tests
```
Note: You can run steps in parallel without waiting for the other ones in a job.

## Artifacts
Artifact is a package structures that differs from programming languages like for java is a jar/war file, for dotNet is
nuget and for type script is zip. For storing and sharing these artifacts you need somewhere in Azure that is called 
Azure Artifacts. Azure currently supports maven, nuget, and npm packages. However, in modern development we use DOCKER
image instead of that artifact packages and for store them you can use docker repository (docker hub, 
Azure container registry, amazon ECR).

CD stands for Continuous Delivery, and it happens before deployment.

So, each multiple jobs can categorize az a separate stage and you could separate Deploy stage from Build stage. And
by default they run after each other one by one.

We could use a specific type of job called deployment to have some features like doesn't check out the code, 
deployment history and apply deployment strategy.

```yaml
- stage: Deploy to Dev
  jobs:
    - job: Deploy to development environment
      steps:
        - task: AzuewWebApp@1
          #...
- stage: Deploy to Test
  jobs:
    - job: Deploy to test environment
      steps:
        - task: AzuewWebApp@1
          #...
- stage: Deploy to Prod
  jobs:
    - job: Deploy to production environment
      steps:
        - task: AzuewWebApp@1
          #...
```

To write common configuration for the pipeline we can write a template and reuse it in our pipelines.

```yaml
parameters:
  env: Dev

jobs:
- job: Deploy
  environment: ${{ parameters.env }}
  steps:
  - task: AzureWebApp@1
    inputs:
      appName: myapp
      package: '$(System.DefaultWorkingDirectory)/**/*.zip'
```
to use it in the following pipeline:
```yaml
- stage: Deploy to Dev
  jobs:
    - templates: /Deploy/Jobs/deploy.yml
      parameters:
        env: Dev

- stage: Deploy to Test
  jobs:
    - templates: /Deploy/Jobs/deploy.yml
      parameters:
        env: Test

- stage: Deploy to Prod
  jobs:
    - templates: /Deploy/Jobs/deploy.yml
      parameters:
        env: Prod
```

Note: You can have a template for a job, stage or step. And you can have templates within a templates.

### Environments
You can create environment in azure environments and for each stage you could add environment: <env name> in your job

### Release
On the contrary of Gitlab or Jenkins you could separate Azure CD pipeline from CI pipeline via release section.
You can create Release pipeline via using build or azure repo or etc. Note: the release pipeline could just create
with UI not yml file.
Note: usually you have to have one CI/CD pipeline for entire release so unless for specific reason don't use release 
CD pipeline.


## Test Plans
You could create a manual test documentation or create automated tests as part of CI/CD pipeline.

## Architecture
Where are those agent machines in which environment? Pipeline tasks are run on separate machines called agents which
are connected to the azure services platform. There are two types of agents: one of them is microsoft hosted agents
and another one is a self-hosted agents

### Setting
In setting you have an option called service connection that you could connect to your third-party services like github
and also you can find in pipeline section an Agent pools config so you could define your pool.