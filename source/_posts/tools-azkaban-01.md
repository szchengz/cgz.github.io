---
title: azkaban安装使用
date: 2018-12-17 08:56:31
categories:  #分类
  - 工具
tags:
  - azkaban
---

## 0.概述

- 官网介绍
> Azkaban is a batch workflow job scheduler created at LinkedIn to run Hadoop jobs. Azkaban resolves the ordering through job dependencies and provides an easy to use web user interface to maintain and track your workflows.

- 特征Features
> Compatible with any version of Hadoop(兼容任何版本的Hadoop)
> Easy to use web UI(易于使用的web UI)
> Simple web and http workflow uploads(简单的web和http工作流上传)
> Project workspaces(项目工作区)
> Scheduling of workflows(调度的工作流)
> Modular and pluginable(模块化和插件化)
> Authentication and Authorization（身份验证和授权）
> Tracking of user actions（跟踪用户操作）
> Email alerts on failure and successes（失败和成功的电子邮件提醒）
> SLA alerting and auto killing（SLA警报和自动终止）
> Retrying of failed jobs(失败作业的重试)

- Azkaban三种部署模式
solo-server模式
> DB使用的是一个内嵌的H2，Web Server和Executor Server运行在同一个进程里。这种模式包含Azkaban的所有特性，但一般用来学习和测试。

two-server模式
> DB使用的是MySQL，MySQL支持master-slave架构，Web Server和Executor Server运行在不同的进程中。

multiple-executor模式
> DB使用的是MySQL，MySQL支持master-slave架构，Web Server和Executor Server运行在不同机器上，且有多个Executor Server。

-


## 1.环境准备
> yum install git
> yum install gcc-c++
> wget https://github.com/azkaban/azkaban/archive/3.42.0.tar.gz
> mv 3.42.0 azkaban-3.42.0.tar.gz
> tar -zxvf azkaban-3.42.0.tar.gz

> ./gradlew build installDist -x test #Gradle是一个基于Apache Ant和Apache Maven的项目自动化构建工具。-x test 跳过测试
