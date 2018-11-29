---
  title: 配置Kafka集群
  date: 2018-08-16 17:20:00
  categories: 大数据
  tags: [Kafka,集群]
  description: 配置Kafka集群
---

# 0.安装ZooKeeper环境

# 1. 解压Kafka，配置环境变量
```
export KAFKA_HOME=/usr/soft/kafka_2.11-0.10.2.0
export PATH=$PATH:${KAFKA_HOME}/bin:
```

# 2. 配置server.properties

修改（所有节点一样，与zookeeper的配置一致）
```
zookeeper.connect=master:2181,node1:2181,node2:2181
```

当前机器在集群中的唯一标识，和zookeeper的myid性质一样（各节点须不一样）

```
broker.id=1
```

# 3. 下载slf4j-1.7.25.tar（非必须）

解压 slf4j-nop-1.7.25.jar 到kafka的libs 这样就可以nohub形式启动

# 4.启动

bin/kafka-server-start.sh config/server.properties &
/usr/soft/kafka_2.11-0.10.2.0/bin/kafka-server-start.sh /usr/soft/kafka_2.11-0.10.2.0/config/server.properties &

nohup形式启动，不打印日志到终端
nohup bin/kafka-server-start.sh config/server.properties &

停止
bin/kafka-server-stop.sh


# 5.测试

创建topics(可在任一节点操作)
> bin/kafka-topics.sh --create --zookeeper master:2181,node1:2181,node2:2181 --replication-factor 3 --partitions 1 --topic HelloKafka

查看topics(可在任一节点操作)
> bin/kafka-topics.sh --describe --zookeeper master:2181,node1:2181,node2:2181 --topic HelloKafka

在topic上创建一个producer（生产者）
> bin/kafka-console-producer.sh --broker-list master:9092,node1:9092,node2:9092 --topic HelloKafka

创建消费者
> bin/kafka-console-consumer.sh --zookeeper master:2181,node1:2181,node2:2181 --topic HelloKafka --from-beginning

查看Topic
> bin/kafka-topics.sh --list --zookeeper master:2181,node1:2181,node2:2181

删除Topic
> bin/kafka-topics.sh --zookeeper master:2181,node1:2181,node2:2181 --delete --topic HelloKafka
