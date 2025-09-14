def call(String image) {
    stage("Trivy Scan") {
        sh """
        echo "🔎 Scanning Docker image: ${image}"
        trivy image --exit-code 1 --severity HIGH,CRITICAL ${image} || true
        """
    }
}
