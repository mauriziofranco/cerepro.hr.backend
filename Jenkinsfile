pipeline {
    agent any
    options { timeout(time: 5) }
    parameters {
        booleanParam(name: 'PROMOTE_ON_PRODUCTION', defaultValue: false,
            description: 'Al termine di questa pipeline, vuoi consentire la promozione in ambiente di Produzione?')
    }
    environment {
        REMOTE_WORKING_DIR = "."
        /*
        REMOTE_WORKING_DIR = "${ARTIFACT_FILE_NAME}"
        */   
        SERVICE_SOURCE_PORT = "8080"  
        PACKAGE_FILE_NAME = readMavenPom().getProperties().getProperty('package.file.name')
        MAVEN_FILE = readMavenPom()
        PACKAGING = readMavenPom().getPackaging()
        ARTIFACT_FULL_FILE_NAME = "${PACKAGE_FILE_NAME}.${PACKAGING}"
        APPLICATION_DOCKER_HOST = "rastaban"
        DEV_SERVICES_EXPOSED_PORT="9051"
        STAGE_SERVICES_EXPOSED_PORT="9052"
        PROD_SERVICES_EXPOSED_PORT="9053"
        APPLICATION_CONTEXT_ROOT="cerepro.hr.backend"
        DOCKER_HOST_CONTAINER_NAME_PREFIX="${PACKAGE_FILE_NAME}"
        DEV_info_app_environment_PROPERTY="DEV"
        STAGE_info_app_environment_PROPERTY="STAGE"
        PROD_info_app_environment_PROPERTY="PROD"
        /*        
        DEV_ENVIRONMENT_HOSTNAME = "eltanin"
        STAGE_ENVIRONMENT_HOSTNAME = "ndraconis"
        PROD_ENVIRONMENT_HOSTNAME = "thuban"
        ENVIRONMENT_TARGET_DIR = "cerepro_resources"
        ENVIRONMENT_DEPLOY_DIR = "tomcat_webapps"
        */
    }
    stages {        
        stage("Compile") {
            steps {
                sh "./mvnw clean compile"
            }
        }
        stage("Provides application property file for Integration tests --> for mvn:test goal purpose only!!!") {
            steps {                  
                sh "cp /cerepro_resources/properties/cerepro.mail.manager/mail.test.properties      ./src/test/resources/mail.properties"
                sh "cp /cerepro_resources/properties/cerepro.hr.backend/application-test.properties ./src/test/resources/application.properties"
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
        stage ("PREPARE FULL PACKAGE") {
            steps {
                echo "Provides application.{env}.properties files"
                sh "cp /cerepro_resources/properties/cerepro.hr.backend/application-dev.properties ./src/main/resources/application-dev.properties"
                sh "cp /cerepro_resources/properties/cerepro.hr.backend/application-stage.properties ./src/main/resources/application-stage.properties"
                sh "cp /cerepro_resources/properties/cerepro.hr.backend/application-prod.properties ./src/main/resources/application-prod.properties"
                sh "cp /cerepro_resources/properties/cerepro.mail.manager/mail.prod.properties      ./src/main/resources/mail.properties"
                echo "Preparing artifact: ${ARTIFACT_FULL_FILE_NAME}"
                sh "./mvnw package -DskipTests"
                echo "Archiving artifact: ${ARTIFACT_FULL_FILE_NAME}"
                sh "cp ./target/${ARTIFACT_FULL_FILE_NAME} ./${ARTIFACT_FULL_FILE_NAME}"
                archiveArtifacts artifacts: "${ARTIFACT_FULL_FILE_NAME}", onlyIfSuccessful: true
                archiveArtifacts artifacts: "Dockerfile", onlyIfSuccessful: true
            }
        } 
        stage ("DELIVERY ON DOCKER HOST") {
            steps {
                echo "MOVING files on docker host"
                sh "/cerepro_resources/scp_on_docker_host.sh ${JOB_NAME} ${BUILD_NUMBER} ${ARTIFACT_FULL_FILE_NAME} cerepro_resources ${APPLICATION_DOCKER_HOST} archive"
                sh "/cerepro_resources/scp_on_docker_host.sh ${JOB_NAME} ${BUILD_NUMBER} Dockerfile cerepro_resources ${APPLICATION_DOCKER_HOST} archive"
            }
        }
        stage ("PROMOTE DEV AND STAGE ENVIRONMENTS") {
            steps {
                echo "EXECUTING DEV ENVIRONEMNT PROMOTION"
                sh "/cerepro_resources/delivery_on_docker@env.sh ${DEV_SERVICES_EXPOSED_PORT} dev ${DOCKER_HOST_CONTAINER_NAME_PREFIX} ${BUILD_NUMBER} ${SERVICE_SOURCE_PORT} ${REMOTE_WORKING_DIR}"
                echo "EXECUTING STAGE ENVIRONEMNT PROMOTION"
                sh "/cerepro_resources/delivery_on_docker@env.sh ${STAGE_SERVICES_EXPOSED_PORT} stage ${DOCKER_HOST_CONTAINER_NAME_PREFIX} ${BUILD_NUMBER} ${SERVICE_SOURCE_PORT} ${REMOTE_WORKING_DIR}"
            }
        }         
        stage ("HEALTH TEST ON DEV AND STAGE ENVIRONMENTS") {
            steps {
                echo "waiting for services startup...."
                sleep 60
                echo "testing health and status.....--> DEV"
                sh "/cerepro_resources/health_test.sh ${DEV_info_app_environment_PROPERTY} ${APPLICATION_DOCKER_HOST} ${DEV_SERVICES_EXPOSED_PORT} ${APPLICATION_CONTEXT_ROOT}"
                echo "testing health and status.....--> STAGE"
                sh "/cerepro_resources/health_test.sh ${STAGE_info_app_environment_PROPERTY} ${APPLICATION_DOCKER_HOST} ${STAGE_SERVICES_EXPOSED_PORT} ${APPLICATION_CONTEXT_ROOT}"
            }
        } 
        stage ("DELIVERY ON PRODUCTION") {
            when { expression { return params.PROMOTE_ON_PRODUCTION } }
            steps {
                echo "EXECUTING PRODUCTION ENVIRONEMNT PROMOTION"   
                echo "EXECUTING PRODUCTION ENVIRONEMNT PROMOTION"
                sh "/cerepro_resources/delivery_on_docker@env.sh ${PROD_SERVICES_EXPOSED_PORT} prod ${DOCKER_HOST_CONTAINER_NAME_PREFIX} ${BUILD_NUMBER} ${SERVICE_SOURCE_PORT} ${REMOTE_WORKING_DIR}"            
            }
        } 
        stage ("HEALTH TEST ON PROD ENVIRONMENT") {
            when { expression { return params.PROMOTE_ON_PRODUCTION } }
            steps {
                echo "waiting for services startup...."
                sleep 60                
                echo "testing health and status.....--> PROD"
                sh "/cerepro_resources/health_test.sh ${PROD_info_app_environment_PROPERTY} ${APPLICATION_DOCKER_HOST} ${PROD_SERVICES_EXPOSED_PORT} ${APPLICATION_CONTEXT_ROOT}"
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
	post {
		always {
		
			emailext body: 'Completed Pipeline: ${currentBuild.fullDisplayName}. /n Your build completed, please check: ${env.BUILD_URL}', 
				recipientProviders: [[$class: 'DevelopersRecipientProvider'], 
					[$class: 'RequesterRecipientProvider']], 
					subject: 'Completed Pipeline: ${currentBuild.fullDisplayName}'
		}
	}
}