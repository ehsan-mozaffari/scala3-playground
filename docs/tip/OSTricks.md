# OS Tricks

## Certificates
On macOS if you want to add certificates without paint in your JDKs 
or event with SDK man copy your certificates in 
```shell
/etc/ssl/certs
/etc/ssl/java/certs
```
I don't know which one is the right path yet so please update me after
that. You can remove the self-signed certificates from **Keychain access**
in the certificates tab.