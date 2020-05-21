FROM payara/server-full:173

# Setup configuration
USER payara
COPY postgresql-9.4.1212.jar /opt/payara41/glassfish/domains/domain1/lib
COPY domain.xml /opt/payara41/glassfish/domains/domain1/config