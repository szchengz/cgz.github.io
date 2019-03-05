---
title: Gradle
date: 2019-03-01 22:00:15
categories:  #分类
  - 工具
tags:
  - Gradle
---

Gradle几个重要的配置

手动下载Gradle压缩包解压到指定目录

程序安装目录
GRADLE_HOME=D:\soft\gradle-5.2.1

加入到环境全局
PATH 后加 ";%GRADLE_HOME%\bin"

Jar下载后的本地存储目录
GRADLE_USER_HOME=E:\repository\gradle

配置国内镜像

当前项目生效
在 build.gradle 文件内修改 / 添加 repositories 配置

```
repositories {
    maven {
        url "http://maven.aliyun.com/nexus/content/groups/public"
    }
}
```

 配置全局生效

 找到 (用户家目录)/.gradle/init.gradle 文件，如果找不到 init.gradle 文件，自己新建一个
  修改 / 添加 init.gradle 文件内的 repositories 配置

 allprojects {
    repositories {
        maven {
            url "http://maven.aliyun.com/nexus/content/groups/public"
        }
    }
}



异常情况：
```

Error:Unable to find method 'org.gradle.api.tasks.testing.Test.getTestClassesDir()Ljava/io/File;'.
Possible causes for this unexpected error include:<ul><li>Gradle's dependency cache may be corrupt (this sometimes occurs after a network connection timeout.)
<a href="syncProject">Re-download dependencies and sync project (requires network)</a></li><li>The state of a Gradle build process (daemon) may be corrupt. Stopping all Gradle daemons may solve this problem.
<a href="stopGradleDaemons">Stop Gradle build processes (requires restart)</a></li><li>Your project may be using a third-party plugin which is not compatible with the other plugins in the project or the version of Gradle requested by the project.</li></ul>In the case of corrupt Gradle processes, you can also try closing the IDE and then killing all Java processes.

```

解决办法：
把5.2.1的版本换成4.9的版本


今天学习使用在IDEA下利用Gradle构建java项目，碰到一个小问题，构建好的项目没有src文件，自己创建后不能新建java文件，经过排查发现是自己新建的文件不是Source Dir 所以在 Project Settion 中的Module里面将该文件夹设置为Source文件夹后,可以新建java文件。而构建项目不带src文件则自己设置一个gradle的Task
task "create-dirs" << {
    sourceSets*.java.srcDirs*.each { it.mkdirs() }
    sourceSets*.resources.srcDirs*.each { it.mkdirs() }
}

在项目目录下执行 gradle create-dirs 自行创建目录




idea 使用gradle 出现 Unindexed remote maven repositories found

用的镜像是阿里云的 maven{ url 'http://maven.aliyun.com/nexus/content/groups/public/'}

据说是idea的bug 我用的版本是2016.3 重新下一个2017.2的版本就没问题了


发现使用mavenLocal() 时Gradle默认会按以下顺序去查找本地的maven仓库：USER_HOME/.m2/settings.xml >> M2_HOME/conf/settings.xml >> USER_HOME/.m2/repository。注意，环境变量要加入M2_HOME， 我们配环境时很多时候都是使用MAVEN_HOME或者直接在path中输入bin路径了，导致mavenLocal无法生效。

另外，如果本地没有相关jar包，gradle会在下载到USER_HOME/.gradle文件夹下，若想让gradle下载到指定文件夹，配置GRADLE_USER_HOME环境变量




# 执行./gradlew命令如何不去下载gradle
https://blog.csdn.net/yzpbright/article/details/53492458
