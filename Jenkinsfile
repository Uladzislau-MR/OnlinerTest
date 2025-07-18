pipeline {
    agent any

    parameters {
        choice(name: 'TEST_SCENARIO', choices: ['test', 'sanityTest', 'negativeTest'], description: 'Какую задачу запустить')
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

        // --- ДОБАВЛЕН НОВЫЙ ЭТАП ДЛЯ ПРОВЕРКИ ---
        stage('Verify Source Code') {
            steps {
                echo "--- Verifying content of WebDriverFactory.java ---"
                // Печатаем содержимое файла, который использует Jenkins
                sh 'cat src/test/java/com/vladislav/onlinertest/core/driver/WebDriverFactory.java'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew build -x test'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'chmod +x ./gradlew'
                script {
                    try {
                        def browser = params.BROWSER
                        def task = params.TEST_SCENARIO

                        echo "Jenkins parameter 'TEST_SCENARIO' is set to: '${task}'"
                        echo "Running Gradle task '${task}' on browser '${browser}'"

                        sh "./gradlew clean ${task} -Dbrowser=${browser}"

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