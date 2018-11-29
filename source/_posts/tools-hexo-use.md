---
title: 在Github上搭建Hexo博客的简要教程
date: 2017-07-31 22:18:45
tags:
	- github
	- hexo
	- yilia
---

# 1.前言

前提环境
> 安装 NodeJs （安装过程略，注意版本不要太低，我开始的时候弄了半天没成功，后面升级了NodeJs的版本就搞定了）
>
> 安装 Git


将操作分成两部分来做。1）、Github的配置；2）Hexo搭建博客系统

# 2.Github的配置

# 3.Hexo描建博客系统


## 3.1 安装Hexo

    $ cd d:/hexo
    $ npm install hexo-cli -g
    $ hexo init blog
    $ cd blog
    $ npm install
    $ hexo g # 或者hexo generate
    $ hexo s # 或者hexo server，可以在http://localhost:4000/ 查看

到这里Hexo的安装已经完成。打开浏览器访问http://localhost:4000即可看到效果。

不过这个主题的效果实在不怎么好看。所以我们要给他换一个主题。

## 3.2 安装Hexo的主题


### 下载主题
    git clone https://github.com/litten/hexo-theme-yilia.git themes/yilia

这里用到的主题是yilia，还有另外两个很不错的主题


    git clone https://github.com/iTimeTraveler/hexo-theme-hiker.git themes/hiker

    git clone https://github.com/Haojen/hexo-theme-Anisina.git themes/anisina


修改_config.yml文件中的配置，把主题修改为刚刚下载的主题名称


    theme: yilia

使用yilia主题还需要另外安装两个模块(不然浏览所有文章时会报错）


    npm i hexo-generator-json-content --save

    npm install hexo-deployer-git --save


修改_config.yml文件增加以下内容（yilia主题的所有文章需要用到）

```
jsonContent:
  meta: false
  pages: false
  posts:
		title: true
		date: true
		path: true
		text: false
		raw: false
		content: false
		slug: false
		updated: false
		comments: false
		link: false
		permalink: false
		excerpt: false
		categories: false
		tags: true
```

修改_config.yml文件，将Github的地址绑定在这里，可通过 hexo d命令将文章发布到Github上面
```
deploy:
  type: git
  repository: git@github.com:szchengz/szchengz.github.io.git
  branch: master
```
