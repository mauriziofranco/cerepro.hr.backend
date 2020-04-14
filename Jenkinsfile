pipeline {
    agent any
    environment {
        PACKAGE_FILE_NAME = readMavenPom().getProperties().getProperty('package.file.name')
        MAVEN_FILE = readMavenPom()
        PACKAGING = readMavenPom().getPackaging()
        PACKAGE_FULL_FILE_NAME = "${PACKAGE_FILE_NAME}.${PACKAGING}"
        DEV_ENVIRONMENT_HOSTNAME = "eltanin"
        STAGE_ENVIRONMENT_HOSTNAME = "ndraconis"
        ENVIRONMENT_TARGET_DIR = "cerepro_resources"
        ENVIRONMENT_DEPLOY_DIR = "tomcat_webapps"
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
        stage ("DELIVERY ON DEV ENVIRONMENT") {
            environment {
                NAME = "dev"                
<<<<<<< HEAD
                ENVIRONMENT_HOSTNAME = "${DEV_ENVIRONMENT_HOSTNAME}"
=======
                ENVIRONMENT_HOSTNAME = ${DEV_ENVIRONMENT_HOSTNAME}
>>>>>>> branch 'master' of https://github.com/MaurizioFranco/cerepro.hr.backend
            }
            steps {
                echo "Preparing ${PACKAGE_FULL_FILE_NAME} for ${env.NAME} environment"
                sh "./mvnw package -P ${env.NAME} -DskipTests"
	            sh "mkdir -p ./dist/${BUILD_NUMBER}/${env.NAME}" 
	            sh "cp ./target/${PACKAGE_FULL_FILE_NAME} ./dist/${BUILD_NUMBER}/${env.NAME}"
	            sh "mkdir -p ${JENKINS_HOME}/jobs/${JOB_NAME}/dist/${BUILD_NUMBER}/${env.NAME}"
	            sh "cp ./dist/${BUILD_NUMBER}/${env.NAME}/*.* ${JENKINS_HOME}/jobs/${JOB_NAME}/dist/${BUILD_NUMBER}/${env.NAME}"
                echo "STARTING TO PROMOTE TO ${env.NAME} ENVIRONMENT"
				sh "/cerepro_resources/scp_put@env.sh ${JOB_NAME} ${BUILD_NUMBER} ${env.NAME} ${PACKAGE_FULL_FILE_NAME} ${ENVIRONMENT_TARGET_DIR} ${ENVIRONMENT_HOSTNAME}"
				sh "/cerepro_resources/delivery@env.sh ${PACKAGE_FULL_FILE_NAME} ${ENVIRONMENT_TARGET_DIR} ${ENVIRONMENT_HOSTNAME} ${ENVIRONMENT_DEPLOY_DIR}"
				echo "PROMOTION TO ${env.NAME} ENVIRONMENT SUCCESSFULLY EXECUTED" 
            }
        }
        stage ("DELIVERY ON STAGE ENVIRONMENT") {
            environment {
                NAME = "stage"
                ENVIRONMENT_HOSTNAME = "${STAGE_ENVIRONMENT_HOSTNAME}"
            }
            steps {
                echo "Preparing ${PACKAGE_FULL_FILE_NAME} for ${env.NAME} environment"
                sh "./mvnw package -P ${env.NAME} -DskipTests"
	            sh "mkdir -p ./dist/${BUILD_NUMBER}/${env.NAME}" 
	            sh "cp ./target/${PACKAGE_FULL_FILE_NAME} ./dist/${BUILD_NUMBER}/${env.NAME}"
	            sh "mkdir -p ${JENKINS_HOME}/jobs/${JOB_NAME}/dist/${BUILD_NUMBER}/${env.NAME}"
	            sh "cp ./dist/${BUILD_NUMBER}/${env.NAME}/*.* ${JENKINS_HOME}/jobs/${JOB_NAME}/dist/${BUILD_NUMBER}/${env.NAME}"
                echo "STARTING TO PROMOTE TO ${env.NAME} ENVIRONMENT"
				sh "/cerepro_resources/scp_put@env.sh ${JOB_NAME} ${BUILD_NUMBER} ${env.NAME} ${PACKAGE_FULL_FILE_NAME} ${ENVIRONMENT_TARGET_DIR} ${ENVIRONMENT_HOSTNAME}"
				sh "/cerepro_resources/delivery@env.sh ${PACKAGE_FULL_FILE_NAME} ${ENVIRONMENT_TARGET_DIR} ${ENVIRONMENT_HOSTNAME} ${ENVIRONMENT_DEPLOY_DIR}"
				echo "PROMOTION TO ${env.NAME} ENVIRONMENT SUCCESSFULLY EXECUTED" 
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