FROM payara/server-full
COPY postgresql-42.2.12.jar $PAYARA_DIR/glassfish/domains/production/lib
COPY domain.xml $PAYARA_DIR/glassfish/domains/production/config
COPY target/projektmanagement.war $DEPLOY_DIR/