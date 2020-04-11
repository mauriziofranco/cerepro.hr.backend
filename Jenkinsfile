pipeline {
    agent any
    environment {
        PACKAGE_FILE_NAME = readMavenPom().getProperties().getProperty('package.file.name')
        MAVEN_FILE = readMavenPom()
        PACKAGING = readMavenPom().getPackaging()
        PACKAGE_FULL_FILE_NAME = "${PACKAGE_FILE_NAME}.${PACKAGING}"
        DEV_ENVIRONMENT_HOSTNAME = "eltanin"
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
        stage("Unit tests") {
            steps {
                sh "./mvnw test"
            }
        }
        stage("Publish code coverage(JaCoCo) code report") {
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
        stage("Publish checkstyle code report") {
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
            environment {
                NAME = "dev"
                
            }
            steps {
            
                echo "########### ${PACKAGE_FULL_FILE_NAME}"
                sh "./mvnw package -P dev -DskipTests"
	            sh "mkdir -p ./dist/${BUILD_NUMBER}/${env.NAME}" 
	            sh "cp ./target/${PACKAGE_FULL_FILE_NAME} ./dist/${BUILD_NUMBER}/${env.NAME}"
	            sh "mkdir -p ${JENKINS_HOME}/jobs/${JOB_NAME}/dist/${BUILD_NUMBER}/${env.NAME}"
	            sh "cp ./dist/${BUILD_NUMBER}/${env.NAME}/*.* ${JENKINS_HOME}/jobs/${JOB_NAME}/dist/${BUILD_NUMBER}/${env.NAME}"
	        }
        }
        stage ("DEPLOY ON DEV ENVIRONMENT") {
            environment {
                NAME = "dev"
                
            }
            steps {
                echo "STARTING TO PROMOTE TO DEVELOPMENT ENVIRONMENT"
				//sh "/cerepro_resources/scp_put@env.sh ${PROMOTED_JOB_FULL_NAME} ${PROMOTED_ID} ${env.NAME} ${PACKAGE_FULL_FILE_NAME} cerepro_resources ${DEV_ENVIRONMENT_HOSTNAME}"
				sh "/cerepro_resources/scp_put@env.sh ${JOB_NAME} ${BUILD_NUMBER} ${env.NAME} ${PACKAGE_FULL_FILE_NAME} cerepro_resources ${DEV_ENVIRONMENT_HOSTNAME}"
				sh "/cerepro_resources/delivery@env.sh ${PACKAGE_FULL_FILE_NAME} cerepro_resources ${DEV_ENVIRONMENT_HOSTNAME} tomcat_webapps"
				echo "PROMOTION TO DEVELOPMENT ENVIRONMENT SUCCESSFULLY EXECUTED" 
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