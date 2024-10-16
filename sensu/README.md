deploy and configure sensu
=========

For OCP we need to add this to the automation at some point as the deploy fails without this.

```bash
oc adm policy add-scc-to-user anyuid -z default
```

To deploy sensu:

```bash
ansible-playbook deploy-sensu-ocp.yml
```

To configure sensu:

```bash
ansible-playbook sensu_config.yml
```
