# eda-toomany-apperrors

## Configuration

The following table lists the configurable parameters and their default values.

| Parameter | Description | Default |
|  ---  |  ---  |  ---  |
| `app.envs.JAVA_APP_JAR` |   | /deployments/quarkus-run.jar |
| `app.envs.kafkabootstrapservers` |   |   |
| `app.envs.registryurl` |   |   |
| `app.envs.toomanyerrorscount` |   |   |
| `app.envs.windowsecondsapperrors` |   |   |
| `app.image` | The container image to use. | quay.io/edaansible/eda-toomany-apperrors:v1 |
| `app.openjdk17.builderImage` | The base image to be used when a container image is being built. | registry.access.redhat.com/ubi8/openjdk-17 |
| `app.ports.http` | The port number to use for http. | 8080 |
| `app.s2iJava.builderImage` | The base image to be used when a container image is being built. | fabric8/s2i-java |

Specify each parameter using the `--set key=value[,key=value]` argument to `helm install`.
Alternatively, a YAML file that specifies the values for the above parameters can be provided while installing the chart. For example,
```
$ helm install --name chart-name -f values.yaml .
```
> **Tip**: You can use the default [values.yaml](values.yaml)
