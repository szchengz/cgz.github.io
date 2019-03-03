---
title: Go语言-爬虫 使用goquery
tags:
  - go
date: 2018-12-01 21:44:03
categories: go
description: Go语言基础
---

# Go使用goquery获取url小实例
先安装

> go get github.com/PuerkitoBio/goquery

例子如下
```go

package main

import (
	"fmt"
	"github.com/PuerkitoBio/goquery"
)

func main() {
	g, e := goquery.NewDocument("http://gold.3g.cnfol.com/")
	if e != nil {
		fmt.Println(e)
	}
	c := g.Find("ul")
	s := c.Eq(6).Find("a")
	s.Each(func(i int, content *goquery.Selection) {
		a, _ := content.Attr("href")
		fmt.Println(a)
	})

}


```
