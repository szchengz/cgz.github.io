---
  title: hexo常用命令操作
  date: 2018-08-16 17:20:00
  categories: 工具
  tags: [hexo]
  description:
---


# 1.常用命令

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


- 部署到服务器
> hexo server

```

//新建一helloworld的文章在_posts目录

//新建

```
