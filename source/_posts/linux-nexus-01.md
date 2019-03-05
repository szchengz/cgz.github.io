---
  title: 配置Nexus私服
  date: 2019-01-16 17:20:00
  categories: linux
  tags: [linux]
  description: 配置Nexus私服
---

## 设置开机启动

-设置启动的用户为root
vim /opt/soft/nexus/nexus-3.14.0-04/bin/nexus.rc
```
run_as_user="root"
```


sudo ln -s /opt/soft/nexus/nexus-3.14.0-04/bin/nexus /etc/init.d/nexus
sudo chmod 777 nexus
sudo chkconfig --add nexus
sudo chkconfig --levels 345 nexus on
sudo service nexus enable


- 验证

service nexus start
service nexus stop


参考文章：
https://wenku.baidu.com/view/3add87a4846a561252d380eb6294dd88d1d23d16.html
