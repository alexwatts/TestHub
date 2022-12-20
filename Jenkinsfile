pipeline {

    agent {
        kubernetes{
            yaml '''
                apiVersion: v1
                kind: Pod
                spec:
                  securityContext:
                    runAsUser: 0
                  containers:
                  - name: docker
                    image: docker:latest
                    command:
                    - cat
                    tty: true
                    volumeMounts:
                    - mountPath: /var/run/docker.sock
                      name: docker-sock
                  - name: kubectl
                    image: bitnami/kubectl
                    command:
                    - cat
                    tty: true
                  - name: gradle
                    image: registry.digitalocean.com/do-k8s-ecr/gradle-cache
                    command:
                    - cat
                    tty: true
                  - name: node
                    image: node:latest
                    command:
                    - cat
                    tty: true
                  volumes:
                  - name: docker-sock
                    hostPath:
                      path: /var/run/docker.sock
                '''
        }
    }

    node ('pool-fv2ocqhbe-mt9wx') {
        stages {
            stage('clone') {
                steps {
                    sh "git clone https://github.com/alexwatts/TestHub"
                    sh "git clone https://github.com/alexwatts/TestHubUI"
                }
            }
            stage('build') {
                steps {
                  container('node') {
                      sh 'yarn --cwd TestHubUI install && yarn --cwd TestHubUI build && mv TestHubUI/build TestHub/src/main/resources/public'
                  }
                  container('gradle') {
                      sh 'cp -r /cache /home/gradle/.gradle'
                      sh "gradle clean build -p TestHub --info"
                  }
                }
            }
            stage('package') {
                steps {
                     container('docker') {
                        withCredentials([usernamePassword(credentialsId: 'DigitalOcean-ECR-Credentials', passwordVariable: 'password', usernameVariable: 'username')]) {
                             sh 'docker build --build-arg JAR_FILE=build/libs/TestHub-1.0-SNAPSHOT.jar -t registry.digitalocean.com/do-k8s-ecr/test-hub TestHub'
                             sh 'docker login -u $username -p $password registry.digitalocean.com'
                             sh 'docker push registry.digitalocean.com/do-k8s-ecr/test-hub'
                        }
                    }
                }
            }
            stage('deploy') {
                steps {
                     container('kubectl') {
                        withCredentials([string(credentialsId: 'DigitalOcean-KubeCtl-Token', variable: 'token')]) {
                             sh 'kubectl delete -f TestHub/testhub.yaml --insecure-skip-tls-verify --kubeconfig="/dev/null" --server=https://65e9bf92-c0ae-46bb-bcd7-edd36ecba1dc.k8s.ondigitalocean.com --token=$token -ntesthub'
                             sh 'kubectl apply -f TestHub/testhub.yaml --insecure-skip-tls-verify --kubeconfig="/dev/null" --server=https://65e9bf92-c0ae-46bb-bcd7-edd36ecba1dc.k8s.ondigitalocean.com --token=$token -ntesthub'
                        }
                    }
                }
            }
        }
    }
}
