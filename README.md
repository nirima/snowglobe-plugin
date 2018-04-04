# snowglobe-plugin
Snowglobe plugin for Jenkins

This allows Jenkins jobs to control a SnowGlobe instance (see https://nirima.github.io/SnowGlobe/).

## Operations


The operations are relatively simple:

### Clone
```groovy
snowglobe_clone createAction: true, sourceId: 'ci-template', targetId: 'new-globe-name'
```       
### Apply
```groovy
snowglobe_apply createAction: true, globeId: 'my-globe', settings: 'key=value'
```
### State
```groovy
data = snowglobe_state createAction: false, globeId: 'my-globe', settings: 'key=value'
```

### Destroy
```groovy
snowglobe_clone remove: true, globeId: 'my-globe'
```

Remove: set to true to also remove the SnowGlobe after destruction.


In all cases - createAction controls whether to add an action to the build, which will also remove the globe when the CI build
is complete.
