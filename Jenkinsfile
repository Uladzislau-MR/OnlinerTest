pipeline {
    agent any

    parameters {
        choice(name: 'TEST_TASK', choices: ['test', 'sanityTest', 'negativeTest'], description: 'Какую задачу запустить (test=все, sanityTest=Sanity, negativeTest=Negative)')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'В каком браузере запустить тесты')
    }

    stages {
        stage('Check network') {
            steps {
                sh 'ping -c 3 github.com || true'
                sh 'nslookup github.com || true'
                sh 'curl -I https://github.com || true'
            }
        }

        stage('Checkout') {
            steps {
                git 'https://github.com/Uladzislau-MR/OnlinerTest.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew build -x test'
            }
        }

        stage('Debug Environment') {
            steps {
                echo "--- Checking Environment ---"
                sh 'google-chrome --version || echo "!!! Google Chrome is NOT INSTALLED !!!"'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'chmod +x ./gradlew'
                script {
                    try {
                        def browser = params.BROWSER
                        def task = params.TEST_TASK

                        echo "Running Gradle task '${task}' on browser '${browser}'"
                        sh "./gradlew ${task} -Dbrowser=${browser}"

                    } catch (Exception e) {
                        echo "Tests failed! Marking build as FAILURE."
                        currentBuild.result = 'FAILURE'
                        echo "--- Displaying chromedriver.log ---"
                        sh 'cat chromedriver.log || echo "chromedriver.log not found."'
                    }
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh './gradlew allureReport'
            }
        }

        stage('Archive Allure Results') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: "build/allure-results"]]
                ])
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