pipeline {
    agent any
    environment {
        PACKAGE_FILE_NAME = readMavenPom().getProperties().getProperty('package.file.name')
        MAVEN_FILE = readMavenPom()
        PACKAGING = readMavenPom().getPackaging()
        PACKAGE_FULL_FILE_NAME = "${PACKAGE_FILE_NAME}.${PACKAGING}"
        DEV_ENVIRONMENT_HOSTNAME = "eltanin"
        STAGE_ENVIRONMENT_HOSTNAME = "ndraconis"
        PROD_ENVIRONMENT_HOSTNAME = "thuban"
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
        stage ("DELIVERY ON DEV ENVIRONMENT") {
            environment {
                NAME = "dev"                
                ENVIRONMENT_HOSTNAME = "${DEV_ENVIRONMENT_HOSTNAME}"
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
        stage("DELIVERY ON PRODUCTION ENVIRONMENT") {
            environment {
                NAME = "prod"
                ENVIRONMENT_HOSTNAME = "${PROD_ENVIRONMENT_HOSTNAME}"
            }
            steps {
                echo "Provides application.prod.properties"
                cp /cerepro_resources/properties/cerepro.hr.backend/application.prod.properties $WORKSPACE/src/main/resources
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