---
title: azkaban单机模式安装使用
date: 2019-03-05 13:56:31
categories:  #分类
  - 工具
tags:
  - azkaban
---

## 0.前提

这个安装教程是通过编译Azkaban源码的方案方式
- 需要安装git
- 无需安装gradle（会自动下载安装）

- 下载azkaban
下载地址： https://github.com/azkaban/azkaban/releases
使用的版本：azkaban-3.67.0

## 1.安装git

> wget https://www.kernel.org/pub/software/scm/git/git-2.7.2.tar.gz
> tar xzf git-2.7.2.tar.gz

进入git目录编译执行

> cd git-2.7.2

编译安装

> sudo make prefix=/usr/local/git all
> sudo make prefix=/usr/local/git install


把git加入到环境变量中

> echo "export PATH=$PATH:/usr/local/git/bin" >> /etc/bashrc
> source /etc/bashrc
或是
> echo "export PATH=$PATH:/usr/local/git/bin" >> /etc/profile
> source /etc/profile

## 1.安装Azkaban

- Azkaban有三种部署模式
solo-server模式 单机模式
> DB使用的是一个内嵌的H2，Web Server和Executor Server运行在同一个进程里。这种模式包含Azkaban的所有特性，但一般用来学习和测试。

two-server模式 集群模式
> DB使用的是MySQL，MySQL支持master-slave架构，Web Server和Executor Server运行在不同的进程中。

multiple-executor模式
> DB使用的是MySQL，MySQL支持master-slave架构，Web Server和Executor Server运行在不同机器上，且有多个Executor Server。


这里针对的是单机模式


> cd azkaban-3.67.0

> ./gradlew build

(
这里会自动下载gradle到用户目录.gradle下面。
建议修改azkaban-3.67.0目录下的build.gradle仓库为国内阿里云或是自己的私服nexus,不然下载时间会比较久
```
repositories {
    mavenLocal()
    maven { url "http://10.10.203.93:8082/repository/maven-public/" }
}
```
两个地方都有这个配置。都改了。
)

> ./gradlew clean
> ./gradlew installDist
> ./gradlew test
> ./gradlew build -x test



> cd azkaban-solo-server/build/install/azkaban-solo-server;
> bin/start-solo.sh


停止
> bin/shutdown-solo.sh


默认用户名和密码：azkaban和azkaban

访问http://localhost:8081即可

## 异常情况


可能编译过程会报FAILURE: Build failed with an exception. 之类的错误，需要更新JDK的加密码类（我在不同环境安装过三次都报错了，所以估计都需要更新）
解决方法：下载JCEhttps://www.oracle.com/technetwork/cn/java/javase/downloads/jce8-download-2133166-zhs.html我这边使用的JDK8，包含了JCE所需要的jre8解压搜下载好的文件，放置到java的以下目录：
/usr/local/jdk1.8.0_74/jre/lib/security
覆盖后重新编译

## 参考文章
https://blog.csdn.net/chenliyu0518/article/details/84786283

执行./gradlew命令如何不去下载gradle
https://blog.csdn.net/yzpbright/article/details/53492458
