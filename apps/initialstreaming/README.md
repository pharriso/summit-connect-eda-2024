# kafka-json-schema-quickstart

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/kafka-json-schema-quickstart-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides

- SmallRye Reactive Messaging - Kafka Connector ([guide](https://quarkus.io/guides/kafka-reactive-getting-started)): Connect to Kafka with Reactive Messaging



Example CURL with passing

```
curl -X 'POST' \
  'http://localhost:8080/sensu' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{"check":{"command":"check-http.rb -u https://hub.demolab.local/ui/login -w","handlers":["kafka"],"high_flap_threshold":0,"interval":60,"low_flap_threshold":0,"publish":true,"runtime_assets":["sensu-plugins/sensu-plugins-http","sensu/sensu-ruby-runtime"],"subscriptions":["hub"],"proxy_entity_name":"","check_hooks":null,"stdin":false,"subdue":null,"ttl":0,"timeout":0,"round_robin":false,"duration":0.129471317,"executed":1709050520,"history":[{"status":0,"executed":1709050388},{"status":2,"executed":1709050403},{"status":2,"executed":1709050417},{"status":2,"executed":1709050477},{"status":0,"executed":1709050520}],"issued":1709050520,"output":"CheckHttp OK: 200, 360 bytes\n\u003c!doctype html\u003e\u003chtml lang=\"en-US\"\u003e\u003chead\u003e\u003cmeta charset=\"UTF-8\"\u003e\u003ctitle\u003eAutomation Hub\u003c/title\u003e\u003clink rel=\"icon\" href=\"/static/galaxy_ng/favicon.ico\"\u003e\u003cscript defer=\"defer\" src=\"/static/galaxy_ng/js/App.5489105562cd9da1a2a2.js\"\u003e\u003c/script\u003e\u003clink href=\"/static/galaxy_ng/css/App.be73969e312997e46cd3.css\" rel=\"stylesheet\"\u003e\u003c/head\u003e\u003cbody\u003e\u003cdiv id=\"root\"\u003e\u003c/div\u003e\u003c/body\u003e\u003c/html\u003e\n","state":"passing","status":0,"total_state_change":0,"last_ok":1709050520,"occurrences":1,"occurrences_watermark":3,"output_metric_format":"","output_metric_handlers":null,"env_vars":null,"metadata":{"name":"check-hub-https","namespace":"default"},"secrets":null,"is_silenced":false},"entity":{"entity_class":"agent","system":{"hostname":"dev","os":"linux","platform":"redhat","platform_family":"rhel","platform_version":"8.9","network":{"interfaces":[{"name":"lo","addresses":["127.0.0.1/8","::1/128"]},{"name":"ens192","mac":"00:50:56:93:ee:c6","addresses":["10.50.0.126/22","fe80::250:56ff:fe93:eec6/64"]}]},"arch":"amd64","libc_type":"glibc","vm_system":"","vm_role":"","cloud_provider":"","processes":null},"subscriptions":["system","entity:dev.demolab.local","hub"],"last_seen":1709050520,"deregister":false,"deregistration":{},"user":"agent","redact":["password","passwd","pass","api_key","api_token","access_key","secret_key","private_key","secret"],"metadata":{"name":"dev.demolab.local","namespace":"default","labels":{"location":"swindon","rack":"rack1"},"created_by":"admin"},"sensu_agent_version":"6.6.6"},"id":"1b29fa20-1ca2-4013-9c6c-7d82ca2388c3","metadata":{"namespace":"default"},"timestamp":1709050520}'
```

Example CURL with failing 

```
curl -X 'POST' \
  'http://localhost:8080/sensu' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{"check":{"command":"check-http.rb -u https://hub.demolab.local/ui/login -w","handlers":["kafka"],"high_flap_threshold":0,"interval":60,"low_flap_threshold":0,"publish":true,"runtime_assets":["sensu-plugins/sensu-plugins-http","sensu/sensu-ruby-runtime"],"subscriptions":["hub"],"proxy_entity_name":"","check_hooks":null,"stdin":false,"subdue":null,"ttl":0,"timeout":0,"round_robin":false,"duration":0.129471317,"executed":1709050520,"history":[{"status":0,"executed":1709050388},{"status":2,"executed":1709050403},{"status":2,"executed":1709050417},{"status":2,"executed":1709050477},{"status":0,"executed":1709050520}],"issued":1709050520,"output":"CheckHttp OK: 200, 360 bytes\n\u003c!doctype html\u003e\u003chtml lang=\"en-US\"\u003e\u003chead\u003e\u003cmeta charset=\"UTF-8\"\u003e\u003ctitle\u003eAutomation Hub\u003c/title\u003e\u003clink rel=\"icon\" href=\"/static/galaxy_ng/favicon.ico\"\u003e\u003cscript defer=\"defer\" src=\"/static/galaxy_ng/js/App.5489105562cd9da1a2a2.js\"\u003e\u003c/script\u003e\u003clink href=\"/static/galaxy_ng/css/App.be73969e312997e46cd3.css\" rel=\"stylesheet\"\u003e\u003c/head\u003e\u003cbody\u003e\u003cdiv id=\"root\"\u003e\u003c/div\u003e\u003c/body\u003e\u003c/html\u003e\n","state":"failed","status":0,"total_state_change":0,"last_ok":1709050520,"occurrences":1,"occurrences_watermark":3,"output_metric_format":"","output_metric_handlers":null,"env_vars":null,"metadata":{"name":"check-hub-https","namespace":"default"},"secrets":null,"is_silenced":false},"entity":{"entity_class":"agent","system":{"hostname":"dev","os":"linux","platform":"redhat","platform_family":"rhel","platform_version":"8.9","network":{"interfaces":[{"name":"lo","addresses":["127.0.0.1/8","::1/128"]},{"name":"ens192","mac":"00:50:56:93:ee:c6","addresses":["10.50.0.126/22","fe80::250:56ff:fe93:eec6/64"]}]},"arch":"amd64","libc_type":"glibc","vm_system":"","vm_role":"","cloud_provider":"","processes":null},"subscriptions":["system","entity:dev.demolab.local","hub"],"last_seen":1709050520,"deregister":false,"deregistration":{},"user":"agent","redact":["password","passwd","pass","api_key","api_token","access_key","secret_key","private_key","secret"],"metadata":{"name":"dev.demolab.local","namespace":"default","labels":{"location":"swindon","rack":"rack1"},"created_by":"admin"},"sensu_agent_version":"6.6.6"},"id":"1b29fa20-1ca2-4013-9c6c-7d82ca2388c3","metadata":{"namespace":"default"},"timestamp":1709050520}'
```

```
curl -X 'POST' \
  'http://localhost:8080/sensu' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{"check":{"command":"check-http.rb -u https://hub.demolab.local/ui/login -w","handlers":["kafka"],"high_flap_threshold":0,"interval":60,"low_flap_threshold":0,"publish":true,"runtime_assets":["sensu-plugins/sensu-plugins-http","sensu/sensu-ruby-runtime"],"subscriptions":["hub"],"proxy_entity_name":"","check_hooks":null,"stdin":false,"subdue":null,"ttl":0,"timeout":0,"round_robin":false,"duration":0.129471317,"executed":1709050520,"history":[{"status":0,"executed":1709050388},{"status":2,"executed":1709050403},{"status":2,"executed":1709050417},{"status":2,"executed":1709050477},{"status":0,"executed":1709050520}],"issued":1709050520,"output":"CheckHttp OK: 200, 360 bytes\n\u003c!doctype html\u003e\u003chtml lang=\"en-US\"\u003e\u003chead\u003e\u003cmeta charset=\"UTF-8\"\u003e\u003ctitle\u003eAutomation Hub\u003c/title\u003e\u003clink rel=\"icon\" href=\"/static/galaxy_ng/favicon.ico\"\u003e\u003cscript defer=\"defer\" src=\"/static/galaxy_ng/js/App.5489105562cd9da1a2a2.js\"\u003e\u003c/script\u003e\u003clink href=\"/static/galaxy_ng/css/App.be73969e312997e46cd3.css\" rel=\"stylesheet\"\u003e\u003c/head\u003e\u003cbody\u003e\u003cdiv id=\"root\"\u003e\u003c/div\u003e\u003c/body\u003e\u003c/html\u003e\n","state":"failed","status":0,"total_state_change":0,"last_ok":1709050520,"occurrences":1,"occurrences_watermark":3,"output_metric_format":"","output_metric_handlers":null,"env_vars":null,"metadata":{"name":"check-hub-https","namespace":"default"},"secrets":null,"is_silenced":false},"entity":{"entity_class":"agent","system":{"hostname":"dev","os":"linux","platform":"redhat","platform_family":"rhel","platform_version":"8.9","network":{"interfaces":[{"name":"lo","addresses":["127.0.0.1/8","::1/128"]},{"name":"ens192","mac":"00:50:56:93:ee:c6","addresses":["10.50.0.126/22","fe80::250:56ff:fe93:eec6/64"]}]},"arch":"amd64","libc_type":"glibc","vm_system":"","vm_role":"","cloud_provider":"","processes":null},"subscriptions":["system","entity:dev.demolab.local","hub"],"last_seen":1709050520,"deregister":false,"deregistration":{},"user":"agent","redact":["password","passwd","pass","api_key","api_token","access_key","secret_key","private_key","secret"],"metadata":{"name":"dev.demolab.local","namespace":"default","labels":{"location":"swindon","rack":"rack2"},"created_by":"admin"},"sensu_agent_version":"6.6.6"},"id":"1b29fa20-1ca2-4013-9c6c-7d82ca2388c3","metadata":{"namespace":"default"},"timestamp":1709050520}'
```


