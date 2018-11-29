---
  title: windows下使用nexus3搭建maven私库
  date: 2018-08-16 17:20:00
  categories: 工具
  tags: [nexus3,maven私库]
  description: 使用nexus3搭建maven私库
---

# 安装nexus

http://www.sonatype.com/download-oss-sonatype

下载Windows版本

解压

进入bin目录运行
> nexus.exe /run

没什么意外的情况下即可访问http://localhost:8081

# 配置nexus

默认系统用户密码:admin/admin123

登录成功后 左边Administration下

Blob Stores 存放目录（可在此定义自己的存放目录）
Repostitories 为远程资源管理（可在这添加资源，比如添加阿里的库）

# 配置Maven

修改Maven的settings文件

- 增加server
```
	<server>
	  <id>my-nexus-releases</id>
	  <username>admin</username>
	  <password>admin123</password>
	</server>
	<server>
	  <id>my-nexus-snapshot</id>
	  <username>admin</username>
	  <password>admin123</password>
	</server>
```

- 增加mirrors
注意这个URL跟nexus里的Repostitories的URL一致
```
<mirror>
	  <!--This sends everything else to /public -->
	  <id>nexus</id>
	  <mirrorOf>*</mirrorOf>
	  <url>http://localhost:8081/repository/mygroup/</url>
	</mirror>
```

增加profile
```
	<profile>
	  <id>nexus</id>
		<!--Enable snapshots for the built in central repo to direct -->
		<!--all requests to nexus via the mirror -->
	  <repositories>
		<repository>
			<id>central</id>
			<url>http://central</url>
			<releases><enabled>true</enabled></releases>
			<snapshots><enabled>true</enabled></snapshots>
		</repository>
	  </repositories>
	  <pluginRepositories>
	    <pluginRepository>
			<id>central</id>
			<url>http://central</url>
			<releases><enabled>true</enabled></releases>
			<snapshots><enabled>true</enabled></snapshots>
		</pluginRepository>
	  </pluginRepositories>
	</profile>
```

设置activeProfiles
```
  <activeProfiles>
	   <activeProfile>nexus</activeProfile>
  </activeProfiles>
```
