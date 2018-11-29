---
  title: Centos升级python版本
  date: 2018-08-16 17:20:00
  categories: python
  tags: [python, 环境]
  description: Centos升级python版本
---

# 实验环境

Ubuntu 14.04

查看版本
> cat /etc/os-release

```shell
sudo add-apt-repository ppa:jonathonf/python-3.6
sudo apt-get update
sudo apt-get install python3.6
sudo rm /usr/bin/python3
sudo ln -s /usr/bin/python3.6 /usr/bin/python3
```

sudo rm -rf /usr/bin/python
sudo ln -s /usr/bin/ptyhon3.6 /usr/bin/python

试过以上没有成功，后来按这个操作成功了
```
# wget https://www.python.org/ftp/python/3.6.2/Python-3.6.2.tar.xz
# tar -xvf Python-3.6.2.tar.xz
# cd Python-3.6.2
# ./configure
# make
# make install
```
https://blog.csdn.net/yanzhibo/article/details/75913347

还需要修改环境变量默认到3.6版本
https://blog.csdn.net/yucicheung/article/details/79334957
