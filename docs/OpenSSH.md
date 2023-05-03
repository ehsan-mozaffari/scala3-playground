# Open SSH
Is a remote management tool to connect to a remote server and bring shell command line in your machine as you are in 
the remote server. It is developed by OpenBSD(Unix) project. And it is standardized in all linux distros.
To use ssh you need two application:
1. An OpenSSH server (to serve the request)
2. An OpenSSH client (to connect to the server)

You can connect to the OpenSSH server via:
1. username and password
2. key file 
   1. Public key: is stored on server
   2. Private key: is stored on client

I use terms of `SSH` instead of `OpenSSH` interchangeably.


## OpenSSH client
You could install it in your OS. Most of the OS have already this built-in but in windows you could install it locally.

```sh
ssh <server-username>@<server-ip-address> # by default it uses port 22 but you could change it to be more secure
ssh <server-username>@<server-ip-address> -i <private-key-file-dir># by default it uses port 22 but you could change it to be more secure
ssh root@192.168.1.50
```

Note: If you don't use that `-i` parameter it will automatically search in the `.ssh` folder to find the file

It connects to the server with that username and for the first time it asks you to add the finger-print.
The terms of finger-print for the first time and if you get it in the future it means that the OS of your
server either reinstalled or got attacked by a person. If you click yes it will permanently add to the 
list of known hosts in the `.ssh/known_hosts`. The reason is that if someone created a new server with that
ip address to stole your credentials you get this finger-print message, and you know that it is now your sever
and refuse to connect with.

You could see the log of connections in the server in the `/var/log/auth.log` directory to see what happens in
the server.

You could press the `Ctrl+D` to disconnect.


## Generating OpenSSH public and private keys

To generate public and private key you could generate it via ssh-keygen command:

```sh
ssh-keygen #press enter. It will generate public/private rsa key pair and it shows the dir to save both
ssh-keygen -t ed25519 # It will generate like above with ed25519 algorithm 
ssh-keygen -C "your email address and timestamp"# You could comment knowing this key is for what account and when is generated
```
You can add a passphrase to have second factor authentication and also change the directory of 
saving public/private keys.

Note: `ed25519` algorithm is a bit performant way for generating public/private keys.

You could copy public key via the following command:
```shell
cat ~/.ssh/id_ed255519.pub | ssh username@ip "mkdir -p ~/.ssh && chmod 700 ~/.ssh cat >> ~/.ssh/authorized_keys"
```


## Use the public/private keys

You could add your public key in the `.ssh/authorized_keys` directory in your server. 
and you could have multiple public key in that directory on each line.


## Move file from or to a server

you could use `scp` command to move file between your server and client.
```sh
scp <fromdir> <remoteserver>:<remote-server-dir>
scp D:\test.txt root@192.168.1.50:/temp/test2.txt
scp root@192.168.1.50:/temp/test2.txt F:\test.txt 
scp -r testfolder/ root@192.168.1.50:/temp/test2folder/ #it will copy all contents of testfolder in your local to the test2folder in your server
```

## ssh-agent to store password

Is for storing your password or even forward your stored password to another remote agent server you are working with.
On Linux and macOS the `ssh-agent` is already running but on Windows you have to configure it to auto run and run the service.

You could add the private key password to the `ssh-agent` by using:
```shell
ssh-add <your-ssh-priavate-key-file-dir> # and then put your password
ssh-add .ssh/id_ed25519
ssh-add -L # to list the passwords that already added to the ssh-agent so better to comment that key to see which one is which
```
And then you could connect to your server without entering password. The private key and password are stored 
in the `ssh-agent` and you don't need for having your private key in a file.


## OpenSSH config file

Config file can simplify your multiple ssh credentials and addresses for connecting to the servers without pain.
Imagine you want to connect with your private github and your company github and for each environment and 
your home office at 192,168.1.60. You have a separate keys and IPs to connect with so here is the example:

First you have to create a config file in the `.ssh` dir
```shell
touch config
```

and then you have to put the followings in your config file:
```
Host github-priave-dev
      Hostname github.com/yourname/dev
      User yourgithubuserfordev
      
Host github-pirvate-prod
      Hostname github.com/youcompayname/prod
      User yourgithubuserforprod
      IdentityFile ~/you/path/to/privatekey/file #you could separate and not using ssh-agnet for that
      
Host github-company-tst
      Hostname github.com/yourcompanyname/tst
      User youcompanytstuser
Host github-company-production
      Hostname github.com/youcompayname/production
      User yourcompanyproductionuser
      
      
Host home-office
      Hostname 192.168.1.50
      User yourhomeofficeuser
      IdentityFile ~/you/path/to/privatekey/file #you could separate and not using ssh-agnet for that
```

and you could access your sever in ssh with just the following command:

```shell
ssh github-priave-dev
ssh github-pirvate-prod
ssh github-company-tst
ssh github-company-production
ssh home-office
```
Note: Please have attention for the `IdentityFile` section in the config file

You could simplify the above config file to something like this:
```
Host github-private*
      User yourgithubuser # it will use this user with all host started with github-private

Host github-priave-dev
      Hostname github.com/yourname/dev
      
Host github-pirvate-prod
      Hostname github.com/yourname/prod
      IdentityFile ~/you/path/to/privatekey/file #you could separate and not using ssh-agnet for that
      
      
Host github-company*
      User yourcompanyuser
      
Host github-company-tst
      Hostname github.com/yourcompanyname/tst

Host github-company-production
      Hostname github.com/yourcompayname/production
  
      
Host home-office
      Hostname 192.168.1.50
      User yourhomeofficeuser
      IdentityFile ~/you/path/to/privatekey/file #you could separate and not using ssh-agnet for that

```

Note: Git uses the OpenSSH client in your machine. Not for Windows! for Windows, you have to change default to OpenSSH client!
```shell
git config --global core.sshcommand "C:/Windows/System32/OpenSSH/ssh.exe"
```

So based on above config if you want to clone a repo on each server you have to do the following:
```shell
git clone git@github-private-prod:yourgithubuser/your-repository-name.git # it will translate to the below command
git clone git@github.com/yourname/prod:yourgithubuser/your-repository-name.git
```

If you want to for example clone a repo in your server and need for you credentials instead of adding your 
private key into server you can forward it from your machine via ForwardAgent in your config file:

```
Host home-office
      Hostname 192.168.1.50
      User yourhomeofficeuser
      IdentityFile ~/you/path/to/privatekey/file #you could separate and not using ssh-agnet for that
      ForwardAgent yes 
```
After connecting to your server you could confirm that via ```ssh-add -L``` command on your server.

## Forwarding a port via OpenSSH
Forwarding is a good utility to forward a port from your server to your local machine to work with in 
your local machine like connecting to MySQL commandline in the server via your MySQL command line in your local
machine.

To work with that first you have to forward the port from your server to your local machine:

```shell
ssh root@192.168.1.50 -N -f -L 3307:yourmysqlserverurl:3306
```

It will connect to your home office (jump host) as I defined above and connect to your MySQL server url with the
3306 port and port it into 3307 port in your local machine. And you could easily connect
to MySQL server via:
```shell
\connect --mysql admin@localhost:3307
```
Note: please double-check the port!

You could add this in your Intellij IDE with some simple clicks.

You could read more about the ssh port forwarding at this [doc](https://www.ssh.com/academy/ssh/tunneling/example).


## Debug OpenSSH

For understanding what exactly happening when you want to connect to your server just add `-v` in 
the ssh command, and it will show you what are happening with your connection!

If you have a problem to connect to your server if you do all things right please have attention
to the `.ssh` directory permissions.

You could see the logs to see what is happening:
```shell
tail -f /var/log/auth.log
journalctl -fu ssh
```

## OpenSSH permissions
Please log in to your user in the server and do the following for the permissions:
```shell
chmod 700 ~/.ssh 
chmod 644 ~/.ssh/authorized_keys
chmod 644 ~/.ssh/id_* # for the client
chown user:user ~/.ssh/authorized_key
chown user:user ~/.ssh
service ssh restart
```