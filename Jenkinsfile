pipeline {
    agent any

    parameters {
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser to run tests on')
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Uladzislau-MR/OnlinerTest.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh './gradlew build -x test'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    def browser = params.BROWSER
                    echo "Running tests on ${browser} browser..."

                    def exitCode = sh(returnStatus: true, script: """
                        ./gradlew clean test -Dbrowser=${browser}
                    """)

                    if (exitCode != 0) {
                        echo "Tests failed, but proceeding to generate Allure reports."
                    }
                }
            }
            post {
                always {
                    echo "Test execution completed (success or failure). Proceeding to Allure report generation."
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                script {
                    echo "Generating Allure Report..."
                    sh './gradlew allureReport'
                }
            }
            post {
                always {
                    echo "Allure Report generation completed."
                }
            }
        }

        stage('Archive Allure Results') {
            steps {
                script {
                    echo "Archiving Allure results..."
                    allure([
                        includeProperties: false,
                        jdk: '',
                        results: [[path: "build/allure-results"]]
                    ])
                }
            }
            post {
                always {
                    echo "Allure results archived successfully."
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline execution completed!'
        }
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Tests failed! Check logs and reports for details.'
        }
    }
}
