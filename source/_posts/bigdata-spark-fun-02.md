---
title: spark RDD的action操作
date: 2018-08-07 13:14:42
categories:  #分类
  - 大数据
  - Spark
tags:
  - Spark算子
---

# 1.reduce
reduce(func)是对数据集的所有元素执行聚集(func)函数，该函数必须是可交换的。
```
val rdd1 = sc.parallelize(1 to 9, 3)
val rdd2 = rdd1.reduce(_ + _)
rdd2: Int = 45
```
# 2.collect
collect是将数据集中的所有元素以一个array的形式返回。
```
rdd1.collect()
res8: Array[Int] = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
```
# 3.count
返回数据集中元素的个数。
```
rdd1.count()
res9: Long = 9
```

<!--more-->

# 4.first
返回数据集中的第一个元素， 类似于take(1)。
rdd1.first()
res10: Int = 1
# 5.take
Take(n)返回一个包含数据集中前n个元素的数组， 当前该操作不能并行。
rdd1.take(3)
res11: Array[Int] = Array(1, 2, 3)
# 6.takeSample
takeSample(withReplacement,num, [seed])返回包含随机的num个元素的数组，和Sample不同， takeSample 是行动操作，所以返回
的是数组而不是RDD ， 其中第一个参数withReplacement是抽样时是否放回，第二个参数num会精确指定抽样数，而不是比例。
rdd1.takeSample(true, 4)
res15: Array[Int] = Array(9, 5, 5, 6)
# 7.takeOrdered
takeOrdered(n， [ordering])是返回包含随机的n个元素的数组，按照顺序输出。
rdd1.takeOrdered(4)
res16: Array[Int] = Array(1, 2, 3, 4)
# 8.saveAsTextFile
把数据集中的元素写到一个文本文件， Spark会对每个元素调用toString方法来把每个元素存成文本文件的一行。
# 9.countByKey
对于(K, V)类型的RDD. 返回一个(K, Int)的map， Int为K的个数。
# 10.foreach
foreach(func)是对数据集中的每个元素都执行func函数。
