FROM bigtruedata/sbt

ENV DOWNLOAD www.h2database.com/h2-2018-03-18.zip
ENV DATA_DIR /opt/h2-data

EXPOSE 81 1521

# install h2 database
RUN curl ${DOWNLOAD} -o h2.zip \
    && unzip h2.zip -d /opt/ \
    && rm h2.zip \
    && mkdir -p ${DATA_DIR}

# install mongodb
RUN apt-get update
RUN apt-get install -y mongodb

# install Xvfb
RUN apt-get install -y xvfb

RUN service mongodb start
