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

## Git proxy config
tested with Git 1.8.2 and SOCKS v5 proxy, following setting works for me:
```shell
git config --global http.proxy 'socks5://127.0.0.1:7070'
```
According to the document, despite the name `http.proxy`, it should work for both HTTP and HTTPS 
repository urls.

To disable the proxy, run command:

```shell
git config --global --unset http.proxy
```
If you also want the host name to be resolved using the proxy, use `socks5h` instead of `socks5`

or in the commandline use the following:
```shell
git clone https://github.com/xxxxx --config 'http.proxy=socks5://127.0.0.1:1234'
```
