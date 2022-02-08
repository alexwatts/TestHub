# TestHub

This is the back end for TestHub. It supports receiving test reports from CI jobs and stores those reports so that they can be retreived by a UI that displays them in a time series style table for at a glance aggregated view.

You can find the front end code here (https://github.com/alexwatts/TestHubUI)

There is a staging version of the UI and backend deployed here (http://138.68.170.14:30001/)

## Getting Started

You can run the backend on localhost:8080 with this gradle command

```
gradle bootRun --args='--spring.profiles.active=local'
```

There is a kubernetes stateful set and a service template that you can use to deploy a dockerised image of TestHub.
There is a jenkins job that is doing that into staging for the UI and backend here. It's named TestHub-pipeline

The stateful set contains a persistent volume mount that allows pods to be destroyed/moved to other nodes without losing the reports store

```
username: testhub
password: contributor

http://138.68.170.14:30000
```

The steps can be extracted from Jenkinsfile though be run against a local kubernetes cluster or run locally with docker

There is another jenkins job in the same instance that builds a bare gradle docker image, with just the spring boot / third party gradle dependencies for TestHub to avoid having to pull these dependencies ever time TestHub pipeline is built. It's named TestHub-dependencies cache. This image is the base image for the TestHub-pipeline and contains a gradle dependcies cache

## Authors

* **Alex Watts** - (https://github.com/alexwatts)

## License

This project is licensed under the MIT License

## Acknowledgments

* https://tech.ebayinc.com/engineering/failbot
