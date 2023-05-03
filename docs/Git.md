# Git

## Git fork and update tags from original repo to forked repo
```bash
# Repo: someuser/myframework
# Fork: superteam/myframework

# Track:
git clone https://github.com/superteam/myframework.git
cd myframework
git remote add upstream https://github.com/someuser/myframework.git

# Update:
git fetch upstream
git rebase upstream/master
git push
git push --tags
```
Test the following:
```shell
git fetch --tags <remote-url>/<defined upstream>
git push --tags # your repo
```
