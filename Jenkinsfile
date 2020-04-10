pipeline {
    agent any
    environment {
        POM_APP = readMavenPom().getProperties().getProperty('checkstyle-maven-plugin.version')
    }
    stages {        
        stage("Compile") {
            steps {
                sh "./mvnw clean compile"
            }
        }
        stage("Provides application property file for Integration tests") {
            steps {                
                sh "rm ./src/main/resources/mail.properties"
                //sh "rm ./src/main/resources/application.properties"
                //echo "Original ./src/test/resources/mail.properties and ./src/test/resources/application.properties successfully removed!!"
                //sh "cp /cerepro_resources/properties/cerepro.mail.manager/mail.test.properties ./src/test/resources/mail.properties"
                sh "cp /cerepro_resources/properties/cerepro.mail.manager/mail.test.properties ./src/main/resources/mail.properties"
                sh "cp /cerepro_resources/properties/cerepro.hr.backend/application.test.properties ./src/test/resources/application.properties"

            }
        } 
        stage("Unit test") {
            steps {
                sh "./mvnw test"
            }
        }
        stage("Code coverage") {
            steps {              
				jacoco(execPattern: 'target/jacoco.exec')
				publishHTML (target: [
				        reportDir: 'target/site/jacoco',
				        reportFiles: 'index.html',
				        reportName: "JaCoCo Report"
				    ]
				)
            }
        }
        stage("Build and publish code check-style report") {
            steps {
                sh "./mvnw site"
                publishHTML (target: [
					reportDir: 'target/site/',
					reportFiles: 'checkstyle.html',
					reportName: "Checkstyle Report"
				])
            }
        }
        /*
        stage("Install for All Environments") {
            steps {              
				sh "./mvnw install -DskipTests"
            }
        }
        stage("Provides application property files for ?????? Integration tests(trying to send mails))") {
            steps {
                sh "rm ./src/test/resources/mail.properties"
                echo "Original ./src/test/resources/mail.properties successfully removed!!"
                sh "cp /cerepro_resources/properties/cerepro.mail.manager/mail.test.properties ./src/test/resources/mail.properties"
                cp /cerepro_resources/properties/cerepro.hr.backend/application.prod.properties $WORKSPACE/src/main/resources
                cp /cerepro_resources/properties/cerepro.mail.manager/mail.test.properties $WORKSPACE/src/main/resources/mail.properties
            }
        } 
        */
        stage("Prepare DEV package") {
            steps {
                echo "${POM_APP}"
                sh "./mvnw package -P dev -DskipTests"
	            sh "mkdir -p ./dist/${BUILD_NUMBER}/dev" 
	            sh "cp ./target/cerepro.hr.backend.war ./dist/${BUILD_NUMBER}/dev"
	            sh "mkdir -p ${JENKINS_HOME}/jobs/${JOB_NAME}/dist/${BUILD_NUMBER}/dev"
	            sh "cp ./dist/${BUILD_NUMBER}/dev/*.* ${JENKINS_HOME}/jobs/${JOB_NAME}/dist/${BUILD_NUMBER}/dev"
	        }
        }
    }
    post {
		always {
			mail to: 'm.franco@proximanetwork.it',
			subject: "Completed Pipeline: ${currentBuild.fullDisplayName}",
			body: "Your build completed, please check: ${env.BUILD_URL}"
		}
	}
}