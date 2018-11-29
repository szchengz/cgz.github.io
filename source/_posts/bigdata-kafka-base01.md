---
title: Kafka的架构涉及到的名词
date: 2016-03-08 08:57:51
categories:  #分类
  - 大数据
  - Kafka
tags:
  - Kafka
---

1. Topic：用于划分Message的逻辑概念，一个Topic可以分布在多个Broker上。


2. Partition：是Kafka中横向扩展和一切并行化的基础，每个Topic都至少被切分为1个Partition。


3. Offset：消息在Partition中的编号，编号顺序不跨Partition。


4. Consumer：用于从Broker中取出/消费Message。


5. Producer：用于往Broker中发送/生产Message。


6. Replication：Kafka支持以Partition为单位对Message进行冗余备份，每个Partition都可以配置至少1个Replication(当仅1个Replication时即仅该Partition本身)。


7. Leader：每个Replication集合中的Partition都会选出一个唯一的Leader，所有的读写请求都由Leader处理。其他Replicas从Leader处把数据更新同步到本地，过程类似大家熟悉的MySQL中的Binlog同步。


8. Broker：Kafka中使用Broker来接受Producer和Consumer的请求，并把Message持久化到本地磁盘。每个Cluster当中会选举出一个Broker来担任Controller，负责处理Partition的Leader选举，协调Partition迁移等工作。


9. ISR(In-Sync Replica)：是Replicas的一个子集，表示目前Alive且与Leader能够“Catch-up”的Replicas集合。由于读写都是首先落到Leader上，所以一般来说通过同步机制从Leader上拉取数据的Replica都会和Leader有一些延迟(包括了延迟时间和延迟条数两个维度)，任意一个超过阈值都会把该Replica踢出ISR。每个Partition都有它自己独立的ISR。

以上几乎是我们在使用Kafka的过程中可能遇到的所有名词，同时也无一不是最核心的概念或组件，感觉到从设计本身来说，Kafka还是足够简洁的。
