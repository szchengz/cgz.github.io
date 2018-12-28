---
title: hexo安装使用笔记
tags:
	- github
	- hexo
	- yilia
date: 2018-11-18 20:45:45
categories: 工具
description: hexo的安装摘要，使用笔记
---

# 0.目录

- 安装hexo
- 安装配置git
- github设置

## 1.安装hexo
(使用nodejs安装hexo)

```
$ cd d:/hexo
$ npm install hexo-cli -g
$ hexo init blog
$ cd blog
$ npm install
$ hexo g # 或者hexo generate
$ hexo s # 或者hexo server，可以在http://localhost:4000/ 查看 
```

hexo常用命令

创建一篇新文章

> hexo new [layout] <title>

布局（layout），默认为 post，可以通过修改 _config.yml 中的 default_layout 参数来指定默认布局
Hexo 有三种默认布局：post、page 和 draft，它们分别对应不同的路径，而您自定义的其他布局和 post 相同，都将储存到 source/_posts 文件夹。

- 创建一文章
> hexo new helloworld
或
> hexo new post helloworld

- 创建一草稿
> hexo new draft helloworld

- 发表草稿
将草稿文件目录_draft移动到文章目录_posts
> hexo publish helloworld

- 将MD格式生成静态文件
> hexo generate
可以简写为
> hexo g

- 启动服务器
> hexo deploy
可以简写为
> hexo d


- 部署到服务器
> hexo server


 附:hexo常用命令 [点这里链接](https://blog.csdn.net/qq_26975307/article/details/62447489)

## 2.安装配置git
下载git 我使用的版本是：Git-2.7.1.2-64-bit
 配置
 ```

git config --global user.name "youname"
git config --global user.email "youname@xxx.com"

git config --global core.quotepath false   # 显示 status 编码
git config --global gui.encoding utf-8   # 图形界面编码
git config --global i18n.commit.encoding utf-8   # 提交信息编码
git config --global i18n.logoutputencoding utf-8 # 输出 log 编码
export LESSCHARSET=utf-8

# 查看你的git配置
git config --list

 ```

生成秘钥
```
ssh-keygen -t rsa -C "youname@xxxx.com"
```

我本地成的位置在：C:\Users\ThinkPad\.ssh

# 3.github设置

### repositories设置
必须是与用户名一致的repositories

### SSH设置

settings -> ssh and Gpd keys
增加本地生成的公钥
（如果需要多台电脑编辑，需要在多台电脑上生成公钥必在这添加）
