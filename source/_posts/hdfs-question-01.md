---
title: bigdata-cm-01
tags:
  - HDFS问题集
date: 2018-12-09 11:12:22
categories:
description: 收集HDFS碰到的各种问题
---

## 问题1：hadoop 出现 failed to create file because current leaseholder is trying to recreate file.

以多线程的方式从Kafka读取消息后保存到HDFS时报以下错误。


```

org.apache.hadoop.ipc.RemoteException(org.apache.hadoop.hdfs.protocol.AlreadyBeingCreatedException): failed to create file /bdp/data/external/t_park_in/data_key=20181228/hour_key=11/data0.txt for DFSClient_NONMAPREDUCE_815471739_12 for client 10.10.203.1 because current leaseholder is trying to recreate file.
	at org.apache.hadoop.hdfs.server.namenode.FSNamesystem.recoverLeaseInternal(FSNamesystem.java:3294)
	at org.apache.hadoop.hdfs.server.namenode.FSNamesystem.appendFileInternal(FSNamesystem.java:3124)
	at org.apache.hadoop.hdfs.server.namenode.FSNamesystem.appendFileInt(FSNamesystem.java:3409)
	at org.apache.hadoop.hdfs.server.namenode.FSNamesystem.appendFile(FSNamesystem.java:3373)
	at org.apache.hadoop.hdfs.server.namenode.NameNodeRpcServer.append(NameNodeRpcServer.java:629)
	at org.apache.hadoop.hdfs.server.namenode.AuthorizationProviderProxyClientProtocol.append(AuthorizationProviderProxyClientProtocol.java:131)
	at org.apache.hadoop.hdfs.protocolPB.ClientNamenodeProtocolServerSideTranslatorPB.append(ClientNamenodeProtocolServerSideTranslatorPB.java:434)
	at org.apache.hadoop.hdfs.protocol.proto.ClientNamenodeProtocolProtos$ClientNamenodeProtocol$2.callBlockingMethod(ClientNamenodeProtocolProtos.java)
	at org.apache.hadoop.ipc.ProtobufRpcEngine$Server$ProtoBufRpcInvoker.call(ProtobufRpcEngine.java:617)
	at org.apache.hadoop.ipc.RPC$Server.call(RPC.java:1073)
	at org.apache.hadoop.ipc.Server$Handler$1.run(Server.java:2281)
	at org.apache.hadoop.ipc.Server$Handler$1.run(Server.java:2277)
	at java.security.AccessController.doPrivileged(Native Method)
	at javax.security.auth.Subject.doAs(Subject.java:422)
	at org.apache.hadoop.security.UserGroupInformation.doAs(UserGroupInformation.java:1924)
	at org.apache.hadoop.ipc.Server$Handler.run(Server.java:2275)

	at org.apache.hadoop.ipc.Client.call(Client.java:1504)
	at org.apache.hadoop.ipc.Client.call(Client.java:1441)
	at org.apache.hadoop.ipc.ProtobufRpcEngine$Invoker.invoke(ProtobufRpcEngine.java:230)
	at com.sun.proxy.$Proxy11.append(Unknown Source)
	at org.apache.hadoop.hdfs.protocolPB.ClientNamenodeProtocolTranslatorPB.append(ClientNamenodeProtocolTranslatorPB.java:331)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.apache.hadoop.io.retry.RetryInvocationHandler.invokeMethod(RetryInvocationHandler.java:258)
	at org.apache.hadoop.io.retry.RetryInvocationHandler.invoke(RetryInvocationHandler.java:104)
	at com.sun.proxy.$Proxy12.append(Unknown Source)
	at org.apache.hadoop.hdfs.DFSClient.callAppend(DFSClient.java:1909)
	at org.apache.hadoop.hdfs.DFSClient.append(DFSClient.java:1951)
	at org.apache.hadoop.hdfs.DFSClient.append(DFSClient.java:1944)
	at org.apache.hadoop.hdfs.DistributedFileSystem$5.doCall(DistributedFileSystem.java:361)
	at org.apache.hadoop.hdfs.DistributedFileSystem$5.doCall(DistributedFileSystem.java:357)
	at org.apache.hadoop.fs.FileSystemLinkResolver.resolve(FileSystemLinkResolver.java:81)
	at org.apache.hadoop.hdfs.DistributedFileSystem.append(DistributedFileSystem.java:357)
	at org.apache.hadoop.fs.FileSystem.append(FileSystem.java:1181)
	at cn.jieshun.bdp.analyse.util.HdfsFileUtil.appendFile(HdfsFileUtil.java:83)
	at cn.jieshun.bdp.etl.kafka.ConsumerWorker.saveMessage(ConsumerWorker.java:155)
	at cn.jieshun.bdp.etl.kafka.ConsumerWorker.run(ConsumerWorker.java:63)
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
```

原因：是并发写导致的错误

解决：以下

```
synchronized (fs) {
                    try {
                        HdfsFileUtil.appendFile(fs, path1, sb.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                }
```
