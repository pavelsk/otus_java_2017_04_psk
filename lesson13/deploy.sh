#!/usr/bin/env bash

mvn clean package

JETTY_HOME=~/development/software/jetty-distribution-9.4.6.v20170531

cp target/lesson13.war $JETTY_HOME/lesson13.war
cp lesson13.xml $JETTY_HOME/webapps/lesson13.xml
cp security $JETTY_HOME/security