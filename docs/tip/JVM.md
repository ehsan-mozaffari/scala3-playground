# JVM

## Certificates
Why we have to use certificate independently? Because JVM uses its own
certificates management instead of OS managed certificate management.
So If you have a self-signed certificate you have to add it in the JVM
also. And because of the nature of JVM if you have multiple versions
of JVM installed in your system you have to add this certificates 
manually to all of them.

Certificate hierarchy: Root Certificate -> Intermediate Certificate -> Site Certificate

There is a command line utility called `keytool` in java to add this
certificates by command into a file called `keystore` file.

When the SSL certificate of a remote proxy repository is not trusted, 
the repository may be automatically blocked inbound/outbound requests fail with 
a message similar to "PKIX path building failed".

This problem is therefore caused by a certificate that is 
self-signed (a CA did not sign it) or a certificate chain that 
does not exist within the Java _truststore_. Java does not 
trust the certificate and fails to connect to the application.

If you are implementing an SSL connection on the Server-side 
say Tomcat you need both keyStore and trustStore, both can 
be the same file, though. keyStore will be used to store 
server certificates which the server will present to the 
client on SSL connection.


Read more: https://javarevisited.blogspot.com/2012/03/add-list-certficates-java-keystore.html#ixzz7RzaPulju

Note: You need to restart your system after doing some changes in certificates
because some apps just read the certs on the OS start up!!!

### keystore
The keystore is a file used by an application server 
to store its private key and site certificate.
A keystore/certificate is who you identify yourself as. 
The job of the keystore is to save key value certificates in your 
pc. The keystore is used by Java application servers such as 
Tomcat to serve the certificates.

### truststore
The truststore is used whenever our Java code establishes 
a connection over SSL. The certificates must be verified 
in JRE `trustStore` and it is located almost always on the following dirs:
```shell
$JAVA_HOME/jre/lib/security/cacerts # JDK 8
$JAVA_HOME/lib/security/cacerts # JDK 11
```

This contains a list of all known Certificate Authority (CA) 
certificates, and Java will only trust certificates that are 
signed by one of those CAs or public certificates that exist 
within that keystore. For example, if we look at the certificate 
for Atlassian, we can see that the _*.atlassian.com_ certificate 
has been signed by the intermediate certificates, 
_DigiCert High Assurance EV Root CA and DigiCert High Assurance CA-3_. 
These intermediate certificates have been signed by the root
_Entrust.net Secure Server CA._

Note: If you decide to choose the custom one you have to add all
other public verified certificates that are in the `cacerts` file 
unless your public ssl URLs do work!! So, you could point the 
default truststore to your custom one in sbt or java command
with the following attribute:
```shell
-Djavax.net.ssl.trustStore=<your custom truststore dir>/truststore.jks
```
If the truststore password is different than “changeit”, 
then also specify the password:
```shell
-Djavax.net.ssl.trustStorePassword=myTrustStorePassword
```
To create truststore without losing other certificates copy
default JVM truststore file `$JAVA_HOME/jre/lib/security/cacerts` 
to `$data-dir/custom-truststore.jks` for editing.

### Add certificates via keytool
First, you have to get the certificate (for example self-signed one):
```shell
openssl s_client -showcerts -servername nexus.company.com -connect nexus.company.com:8443 < /dev/null 2>&1 > nexus_cert.crt
```

Apply this to all JDK versions:
```shell
sudo keytool -importcert -alias <alias of the cert> -file certfile.cer -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit
```
Note: The password will either be changeme or changeit

Or if you want to add a certificate to the keystore:
```shell
keytool -import -file /path/to/certificate.pem -alias NameYouWantToGiveOfYourCertificate -keystore /path/to/copy/of/default/truststore.jks -storepass changeit
```

See the certificates list in truststore:
```shell
keytool -list -keystore truststore.jks
keytool -list -keystore $JAVA_HOME/lib/security/cacerts # Example
```

### Force JVM to use OS cert manager
You could invoke sbt with` -D` attributes like this example:
```shell
sbt "-Djavax.net.ssl.trustStore=/path/to/included/proxycert/cacerts" compile
```

#### Windows
```shell
-Djavax.net.ssl.trustStore=C:\\Windows\\win.ini
-Djavax.net.ssl.trustStoreType=Windows-ROOT
```

#### macOS
I recommend using /dev/null as some existing file 
as it is present on any Mac:
```shell
-Djavax.net.ssl.trustStore=/dev/null
-Djavax.net.ssl.trustStoreType=KeychainStore
```