FROM openjdk:17-oracle
RUN curl -o /tmp/apache-activemq-5.18.2-bin.tar.gz "https://downloads.apache.org/activemq/5.18.2/apache-activemq-5.18.2-bin.tar.gz" \
    && tar -xzf /tmp/apache-activemq-5.18.2-bin.tar.gz -C /opt \
    && ln -s /opt/apache-activemq-5.18.2 /opt/apache-activemq \
    && sed -i 's|value="127.0.0.1"|value="0.0.0.0"|g' /opt/apache-activemq/conf/jetty.xml \
    && rm /tmp/apache-activemq-5.18.2-bin.tar.gz
CMD /opt/apache-activemq/bin/activemq start && sleep infinity
