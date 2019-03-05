---
title: 在CentOS6上安装 git1.9.1
date: 2019-03-01 22:00:15
categories:  #分类
  - 工具
tags:
  - git
---


在CentOS6上安装 git2.7.2

借鉴了以下的方法。版本不一样。

https://blog.csdn.net/qq_31082427/article/details/78016952


sudo yum install curl-devel expat-devel gettext-devel openssl-devel zlib-devel gcc perl-ExtUtils-MakeMaker

wget https://www.kernel.org/pub/software/scm/git/git-2.7.2.tar.gz


tar xzf git-1.9.1.tar.gz

进入git目录编译执行

cd git-1.9.1

sudo make prefix=/usr/local/git all

sudo make prefix=/usr/local/git install

把git加入到环境变量中
echo "export PATH=$PATH:/usr/local/git/bin" >> /etc/bashrc
source /etc/bashrc

或是
echo "export PATH=$PATH:/usr/local/git/bin" >> /etc/profile
source /etc/profile

4、安装成功，查看git版本

git --version
