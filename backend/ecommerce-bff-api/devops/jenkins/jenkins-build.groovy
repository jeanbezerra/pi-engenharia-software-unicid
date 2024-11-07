pipeline {
	
    agent any

    environment {
        // Variáveis de ambiente opcionais
        MAVEN_HOME = tool 'Maven' // Configurar o nome correto da instalação do Maven no Jenkins
        JAVA_HOME = tool 'JDK11'  // Configurar o nome correto da instalação do JDK no Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://seu-repositorio.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Configuração do Maven para a build
                    withMaven(maven: 'Maven') {
                        sh 'mvn clean install'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Executa os testes
                    sh 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    // Criação do pacote JAR
                    sh 'mvn package'
                }
            }
        }

        stage('Deploy') {
            when {
                expression { env.BRANCH_NAME == 'main' }
            }
            steps {
                script {
                    // Comando para deploy, pode ser ajustado conforme sua estratégia de deploy
                    sh 'echo "Deploy step: Adicione seu comando de deploy aqui"'
                }
            }
        }
    }

    post {
        success {
            echo 'Build e testes concluídos com sucesso!'
        }
        failure {
            echo 'Houve uma falha na build ou nos testes.'
        }
        always {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            junit '**/target/surefire-reports/*.xml'
        }
    }
}