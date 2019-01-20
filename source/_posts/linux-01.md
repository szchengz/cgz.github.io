---
  title: 在Linux中分布调用集成中的所有命令
  date: 2019-01-16 17:20:00
  categories: 大数据
  tags: [linux]
  description: 在Linux中分布调用集成中的所有命令
---

## 0.前提
- 集群所有机器配置SSH
- 集群的机器别名是连续的比如 node1,node2,node3

## 调用脚本

vim xcall.sh
```

#!/bin/bash

params=$@
i=1
for (( i=1 ; i <= 3 ; i = $i + 1 )) ; do
    echo ===========o$i $params ============
    ssh o$i "$params"
done

```

拷贝文件到指定目录 并对文件授权

> sudo cp xcall.sh /usr/local/bin/

## rsync远程拷贝文件
需要安装

sudo yum install rsync

## 集群同步文件
xsync.sh
```
#!/bin/bash

if [[ $# -lt 1 ]] ; then echo no params ; exit ; fi

p=$1
#echo p=$p
dir=`dirname $p`
#echo dir=$dir
filename=`basename $p`
#echo filename=$filename
cd $dir
fullpath=`pwd -P .`
#echo fullpath=$fullpath

user=`whoami`
for (( i = 2 ; i <= 3 ; i = $i + 1 )) ; do
   echo ======= o$i =======
   rsync -lr $p ${user}@o$i:$fullpath
done ;

```
