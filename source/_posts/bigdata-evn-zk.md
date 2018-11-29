---
  title: zookeeper集群配置
  date: 2018-08-16 17:20:00
  categories: 大数据
  tags: [zookeeper,集群]
  description: zookeeper集群配置
---



# 1.解压zookeeper，配置环境变量
```
export ZOOKEEPER_HOME=/usr/soft/zookeeper-3.4.9
export PATH=$PATH:${ZOOKEEPER_HOME}/bin:
```

# 2.配置zoo.cfg

> cp conf/zoo_sample.cfg conf/zoo.cfg
> vim conf/zoo.cfg

编辑内容如下：

```
tickTime=2000

initLimit=10

dataDir=/usr/soft/zookeeper-3.4.9/data
dataLogDir=/usr/soft/zookeeper-3.4.9/logs
server.0=master:2888:3888
server.1=node1:2888:3888
server.2=node2:2888:3888
```


# 3.配置myid

进入zoo.cfg配置的dataDir目录
新建文件 myid 值设置为0 （其他机器的设置为1,2,3....)

# 4.启动

在所有机器上启动zookeeper
> /usr/soft/zookeeper-3.4.9/bin/zkServer.sh start

查询状态
> /usr/soft/zookeeper-3.4.9/bin/zkServer.sh status

停止
> /usr/soft/zookeeper-3.4.9/bin/zkServer.sh stop

jps
多了一个进程   QuorumPeerMain
