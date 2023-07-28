# Windows CMD

## Set HTTP(s) Proxy in Windows Command Line

In Windows Command Line environment (NOT powershell), you can use below commands to set http and https proxy.
```shell
set http_proxy=protocol://ip:port
set https_proxy=protocol://ip:port
# Examples
set http_proxy=http://localhost:1080
set https_proxy=http://localhost:1080
set https_proxy=socks5://localhost:1080
set https_proxy=socks5h://localhost:1080
```

To cancel set variable as empty to cancel the proxy:
```shell
set http_proxy=
set https_proxy=
```
