def call(Map config = [:]) {
    def image    = config.image ?: error("You must provide image")
    def appName  = config.appName ?: "app"
    def portMap  = config.portMap ?: "3000:3000"

    stages {
        stage("Deploy") {
            steps {
                script {
                    sh """
                    echo "Pulling image: ${image}"
                    docker pull ${image}

                    echo "Stopping old containers for ${appName}..."
                    docker ps -q --filter "ancestor=${image.split(':')[0]}" | xargs -r docker stop
                    docker ps -a -q --filter "ancestor=${image.split(':')[0]}" | xargs -r docker rm

                    echo "Removing old container with same name (if any)..."
                    docker rm -f ${appName} || true

                    echo "Running new container..."
                    docker run -d --name ${appName} -p ${portMap} ${image}
                    """
                }
            }
        }
    } 
}
