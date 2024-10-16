## Getting started

Notes for deploying skupper on the demolab vm

Create local storage for DB
```
mkdir /home/heroes/data
```
Need to change permissions of the folder
````
podman unshare chown 26:26 $PWD/data
```
podman run --name heroes-db -d -p 5432:5432 \
     -v $HOME/data:/var/lib/pgsql/data:Z \
     -e POSTGRESQL_USER=superman \
     -e POSTGRESQL_PASSWORD=superman \
     -e POSTGRESQL_DATABASE=heroes_database \
     rhel9/postgresql-16
```

```
podman run --name rest-heroes -d -p 8083:8083 \
     -e QUARKUS_DATASOURCE_REACTIVE_URL=postgresql://$HOSTNAME:5432/heroes_database \
     -e QUARKUS_HIBERNATE_ORM_DATABASE_GENERATION=drop-and-create \
     -e QUARKUS_DATASOURCE_USERNAME=superman \
     -e QUARKUS_DATASOURCE_PASSWORD=superman \
     -e QUARKUS_HIBERNATE_ORM_SQL_LOAD_SCRIPT=import.sql \
     -e QUARKUS_OTEL_EXPORTER_OTLP_TRACES_ENDPOINT=http://otel-collector:4317 \
     quay.io/quarkus-super-heroes/rest-heroes:native-latest
```

```
skupper init --site-name heroes
```

on openshift side, create the token 

```
skupper token create demolabvm.token
```

copy token to demolab vm

```
skupper link create demolabvm.token --name heroservice-to-conroe
```

```
skupper expose host  heroservice.pprosser.demolab.local --address rest-heroes  --port 8083  --container-name cont-rest-heroes
```

on openshift 

delete heroes from openshift

```
oc delete all -l application=heroes-service -n heroes
```

```
create skupper rest-heroes service

skupper service create rest-heroes 8083
```

To run, using Quadlets rather than doing podman run 

```
mkdir ~/.config/containers/systemd
```

copy the quadlets/rest-heroes.container to ~/.config/containers

```
systemctl --user daemon-reload
```
