#!/usr/bin/env bash

mvn clean package

JETTY_HOME=~/development/software/jetty-distribution-9.4.6.v20170531

cp target/lesson15.war $JETTY_HOME/lesson15.war
cp lesson15.xml $JETTY_HOME/webapps/lesson15.xml
cp security $JETTY_HOME/security