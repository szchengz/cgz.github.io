---
  title: Hive常见问题
  date: 2018-08-16 17:20:00
  categories: 大数据
  tags: [Hive]
  description: Hive常见问题
---



# 1、message:For direct MetaStore DB connections, we don't support retries at the client level.

当在Hive中创建表的时候报错：
```
hive> create table test(id string,name string) row format delimited fields terminated by ',' stored as textfile;
FAILED: Execution Error, return code 1 from org.apache.hadoop.hive.ql.exec.DDLTask. MetaException(message:For direct MetaStore DB connections, we don't support retries at the client level.)
```
这是由于字符集的问题，需要配置MySQL的字符集：

> mysql> alter database hive character set latin1;


# 2.初次启动hive找不到Spark的Jar包

hadoop@master:/usr/soft/hadoop-2.6.0/sbin$ hive --service metastore
ls: cannot access /usr/soft/spark-2.1.0-bin-hadoop2.6/lib/spark-assembly-*.jar: No such file or directory
Starting Hive Metastore Server

解决方法：修改hive文件

步骤1：
```
cd $HIVE_HOME/bin
vi hive
```

步骤2：
```
if [[ -n "$SPARK_HOME" ]]
then
  sparkAssemblyPath=`ls ${SPARK_HOME}/lib/spark-assembly-*.jar`
  CLASSPATH="${CLASSPATH}:${sparkAssemblyPath}"
fi

```

# 3.搭建环境启动Hive时报错Terminal initialization failed; falling back to unsupported

```
[ERROR] Terminal initialization failed; falling back to unsupported
java.lang.IncompatibleClassChangeError: Found class jline.Terminal, but interface was expected
	at jline.TerminalFactory.create(TerminalFactory.java:101)
	at jline.TerminalFactory.get(TerminalFactory.java:158)
	at jline.console.ConsoleReader.<init>(ConsoleReader.java:229)
	at jline.console.ConsoleReader.<init>(ConsoleReader.java:221)
	at jline.console.ConsoleReader.<init>(ConsoleReader.java:209)
	at org.apache.hadoop.hive.cli.CliDriver.setupConsoleReader(CliDriver.java:787)
	at org.apache.hadoop.hive.cli.CliDriver.executeDriver(CliDriver.java:721)
	at org.apache.hadoop.hive.cli.CliDriver.run(CliDriver.java:681)
	at org.apache.hadoop.hive.cli.CliDriver.main(CliDriver.java:621)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.apache.hadoop.util.RunJar.run(RunJar.java:221)
	at org.apache.hadoop.util.RunJar.main(RunJar.java:136)

Exception in thread "main" java.lang.IncompatibleClassChangeError: Found class jline.Terminal, but interface was expected
	at jline.console.ConsoleReader.<init>(ConsoleReader.java:230)
	at jline.console.ConsoleReader.<init>(ConsoleReader.java:221)
	at jline.console.ConsoleReader.<init>(ConsoleReader.java:209)
	at org.apache.hadoop.hive.cli.CliDriver.setupConsoleReader(CliDriver.java:787)
	at org.apache.hadoop.hive.cli.CliDriver.executeDriver(CliDriver.java:721)
	at org.apache.hadoop.hive.cli.CliDriver.run(CliDriver.java:681)
	at org.apache.hadoop.hive.cli.CliDriver.main(CliDriver.java:621)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.apache.hadoop.util.RunJar.run(RunJar.java:221)
	at org.apache.hadoop.util.RunJar.main(RunJar.java:136)
```

解决方法：将Hive新版本的jline的包拷贝到Hadoop下面（原来的没有删除）
cp /usr/soft/hive1.2.1/lib/jline-2.12.jar ./usr/soft/hadoop-2.6.0/share/hadoop/yarn/lib

# 4.执行插入操作时异常
```
hive>   insert into table words select explode(split(line, " ")) as word from word_data;
Query ID = hadoop_20180823200522_7a53339e-6923-4bfe-b5cf-25ac172ddcc2
Total jobs = 3
Launching Job 1 out of 3
Number of reduce tasks is set to 0 since there's no reduce operator
java.net.ConnectException: Call From master/192.168.52.128 to master:8032 failed on connection exception: java.net.ConnectException: Connection refused; F
or more details see:  http://wiki.apache.org/hadoop/ConnectionRefused	at sun.reflect.GeneratedConstructorAccessor36.newInstance(Unknown Source)
	at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.lang.reflect.Constructor.newInstance(Constructor.java:422)
	at org.apache.hadoop.net.NetUtils.wrapWithMessage(NetUtils.java:791)
	at org.apache.hadoop.net.NetUtils.wrapException(NetUtils.java:731)
	at org.apache.hadoop.ipc.Client.call(Client.java:1472)
	at org.apache.hadoop.ipc.Client.call(Client.java:1399)
	at org.apache.hadoop.ipc.ProtobufRpcEngine$Invoker.invoke(ProtobufRpcEngine.java:232)
	at com.sun.proxy.$Proxy25.getNewApplication(Unknown Source)
	at org.apache.hadoop.yarn.api.impl.pb.client.ApplicationClientProtocolPBClientImpl.getNewApplication(ApplicationClientProtocolPBClientImpl.java:21
7)	at sun.reflect.GeneratedMethodAccessor10.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.apache.hadoop.io.retry.RetryInvocationHandler.invokeMethod(RetryInvocationHandler.java:187)
	at org.apache.hadoop.io.retry.RetryInvocationHandler.invoke(RetryInvocationHandler.java:102)
	at com.sun.proxy.$Proxy26.getNewApplication(Unknown Source)
	at org.apache.hadoop.yarn.client.api.impl.YarnClientImpl.getNewApplication(YarnClientImpl.java:206)
	at org.apache.hadoop.yarn.client.api.impl.YarnClientImpl.createApplication(YarnClientImpl.java:214)
	at org.apache.hadoop.mapred.ResourceMgrDelegate.getNewJobID(ResourceMgrDelegate.java:187)
	at org.apache.hadoop.mapred.YARNRunner.getNewJobID(YARNRunner.java:231)
	at org.apache.hadoop.mapreduce.JobSubmitter.submitJobInternal(JobSubmitter.java:446)
	at org.apache.hadoop.mapreduce.Job$10.run(Job.java:1296)
	at org.apache.hadoop.mapreduce.Job$10.run(Job.java:1293)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:422)
	at org.apache.hadoop.security.UserGroupInformation.doAs(UserGroupInformation.java:1628)
	at org.apache.hadoop.mapreduce.Job.submit(Job.java:1293)
	at org.apache.hadoop.mapred.JobClient$1.run(JobClient.java:562)
	at org.apache.hadoop.mapred.JobClient$1.run(JobClient.java:557)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:422)
	at org.apache.hadoop.security.UserGroupInformation.doAs(UserGroupInformation.java:1628)
	at org.apache.hadoop.mapred.JobClient.submitJobInternal(JobClient.java:557)
	at org.apache.hadoop.mapred.JobClient.submitJob(JobClient.java:548)
	at org.apache.hadoop.hive.ql.exec.mr.ExecDriver.execute(ExecDriver.java:431)
	at org.apache.hadoop.hive.ql.exec.mr.MapRedTask.execute(MapRedTask.java:137)
	at org.apache.hadoop.hive.ql.exec.Task.executeTask(Task.java:160)
	at org.apache.hadoop.hive.ql.exec.TaskRunner.runSequential(TaskRunner.java:88)
	at org.apache.hadoop.hive.ql.Driver.launchTask(Driver.java:1653)
	at org.apache.hadoop.hive.ql.Driver.execute(Driver.java:1412)
	at org.apache.hadoop.hive.ql.Driver.runInternal(Driver.java:1195)
	at org.apache.hadoop.hive.ql.Driver.run(Driver.java:1059)
	at org.apache.hadoop.hive.ql.Driver.run(Driver.java:1049)
	at org.apache.hadoop.hive.cli.CliDriver.processLocalCmd(CliDriver.java:213)
	at org.apache.hadoop.hive.cli.CliDriver.processCmd(CliDriver.java:165)
	at org.apache.hadoop.hive.cli.CliDriver.processLine(CliDriver.java:376)
	at org.apache.hadoop.hive.cli.CliDriver.executeDriver(CliDriver.java:736)
	at org.apache.hadoop.hive.cli.CliDriver.run(CliDriver.java:681)
	at org.apache.hadoop.hive.cli.CliDriver.main(CliDriver.java:621)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.apache.hadoop.util.RunJar.run(RunJar.java:221)
	at org.apache.hadoop.util.RunJar.main(RunJar.java:136)
Caused by: java.net.ConnectException: Connection refused
	at sun.nio.ch.SocketChannelImpl.checkConnect(Native Method)
	at sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:717)
	at org.apache.hadoop.net.SocketIOWithTimeout.connect(SocketIOWithTimeout.java:206)
	at org.apache.hadoop.net.NetUtils.connect(NetUtils.java:530)
	at org.apache.hadoop.net.NetUtils.connect(NetUtils.java:494)
	at org.apache.hadoop.ipc.Client$Connection.setupConnection(Client.java:607)
	at org.apache.hadoop.ipc.Client$Connection.setupIOstreams(Client.java:705)
	at org.apache.hadoop.ipc.Client$Connection.access$2800(Client.java:368)
	at org.apache.hadoop.ipc.Client.getConnection(Client.java:1521)
	at org.apache.hadoop.ipc.Client.call(Client.java:1438)
	... 49 more

```

解决方法：启动yarn
> start-yarn.sh
