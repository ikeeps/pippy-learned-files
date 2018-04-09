# pippy-learned-files
https://github.com/sqshq/PiggyMetrics 
概念学习，以及部署到kubernetes，产生的一些文件。

# Piggy Metrics

**A simple way to deal with personal finances**

This is a [proof-of-concept application](http://my-piggymetrics.rhcloud.com), which demonstrates [Microservice Architecture Pattern](http://martinfowler.com/microservices/) using Spring Boot, Spring Cloud and Docker.
With a pretty neat user interface, by the way.

![](https://cloud.githubusercontent.com/assets/6069066/13864234/442d6faa-ecb9-11e5-9929-34a9539acde0.png)
![Piggy Metrics](https://cloud.githubusercontent.com/assets/6069066/13830155/572e7552-ebe4-11e5-918f-637a49dff9a2.gif)

## conf-server
集中配置文件

## k8s
kubernetes的部署文件。除了pippy的服务在pippy文件夹里，还有rabbitmq,mongodb,mysql,redis,zipkin,elastic。以及一些k8s的基本概念的配置。

## pippy
pippy的源码，用gradle进行项目管理，以及一些小的改动：加上了sleuth，和prometheus，logstash logback配置。

## 可能以后有用的文件
k8s的部署文件，log.xml，spring cloud对的配置管理。gradle的docker image build（远程不成功，生成的文件可以作为docker build 的目录。

## conf-server
是用ssh git后端，需要事先配置一个或者改成github的。