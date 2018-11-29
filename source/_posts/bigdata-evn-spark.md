---
  title: Spark环境集群环境配置
  date: 2018-08-16 17:20:00
  categories: 大数据
  tags: [Spark,集群]
  description: Spark环境集群环境配置
---


# 1.解压Spark，配置环境变量

> export SPARK_HOME=/usr/soft/spark2.1
> export PATH=$PATH:${SPARK_HOME}/bin:

# 2.配置 slaves(conf目录下）
> cp slaves.template slaves
> vim slaves
```
node1
node2
```

# 3.配置 spark-defaults.conf（配置historyServer必须配置这个，如果无须配置这个可以不用配置）
cp spark-defaults.conf.template spark-defaults.conf
```
spark.executor.extraJavaOptions  -XX:+PrintGCDetails -Dkey=value -Dnumbers="one two three"
spark.eventLog.enabled           true
spark.eventLog.dir               hdfs://master:9000/historyServerforSpark
spark.yarn.historyServer.address work1:18080
spark.history.fs.logDirectory    hdfs://master:9000/historyServerforSpark
```
# 4.配置  spark-env.sh
```shell
export SCALA_HOME=/usr/soft/scala-2.11.8
export JAVA_HOME=/usr/soft/jdk1.8
export SPARK_MASTER_IP=master
export SPARK_WORKER_MEMORY=1g
export HADOOP_HOME=/usr/soft/hadoop2.6
export HADOOP_CONF_DIR=/usr/soft/hadoop2.6/etc/hadoop
```

# 5.拷贝文件
```
scp /usr/soft/spark2.1/conf/spark-env.sh root@node1:/usr/soft/spark2.1/conf/
scp /usr/soft/spark2.1/conf/spark-env.sh root@node2:/usr/soft/spark2.1/conf/

scp /usr/soft/spark2.1/conf/slaves root@node1:/usr/soft/spark2.1/conf/
scp /usr/soft/spark2.1/conf/slaves root@node2:/usr/soft/spark2.1/conf/
```

# 6.启动
```
/usr/soft/spark2.1/sbin/start-master.sh
/usr/soft/spark2.1/sbin/start-slaves.sh

/usr/soft/spark2.1/sbin/stop-all.sh
```

# 创建历史记录目录
> hadoop fs -mkdir /historyServerforSpark
```
　　hadoop fs -mkdir -p /Hadoop/Input（在HDFS创建目录）
　　hadoop fs -put hello.txt /Hadoop/Input（将hello.txt文件上传到HDFS）
　　hadoop fs -ls /Hadoop/Input （查看上传的文件）
　　hadoop fs -text /Hadoop/Input/hello.txt （查看文件内容）
```
# 启动spark-shell测试

创建测试数据
> hadoop fs -mkdir /testdata
> hadoop fs -put $SPARK_HOME/LICENSE /testdata

启动spark-shell
> /usr/soft/spark2.1/bin/spark-shell --master spark://master:7077

spark wordcount
注意：如果是读本地文件，集群中的各节点都需要有这个文件，不然会提示找不到文件

```
val file=sc.textFile("file:/usr/soft/spark-2.1.0-bin-hadoop2.6/README.md")
val file=sc.textFile("/usr/soft/spark-2.1.0-bin-hadoop2.6/README.md")
val file=sc.textFile("hdsf://master:9000/testdata/README.md")

//val rdd = file.flatMap(line => line.split(" ")).map(word => (word,1)).reduceByKey(_+_)

val words = file.flatMap(line => line.split(" "))
val pairs = words.map(word => (words,1))

//val wordcount = pairs.reduceByKey((v1,v2) => v1+v2)
val wordcount = pairs.reduceByKey(_+_)

wordcount.collect().foreach(println)

val rddTotal = rdd.collect()
rddTotal.foreach(println)

```

wordcount升级版，增加排序
```
val file=sc.textFile("hdfs://master:9000/testdata/LICENSE")
val rdd = file.flatMap(line=>line.split(" ")).map(word => (word,1)).reduceByKey(_+_).map(tp=>(tp._2,tp._1)).sortByKey(false).map(item=>(item._2,item._1)).collect
rdd.foreach(println)
```
