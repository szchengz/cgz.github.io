---
  title: ignite学习笔记
  date: 2018-10-11 10:20:00
  categories: ignite
  tags: [ignite, 缓存]
  description: ignite学习笔记
---

# 安装

直接官网下载解压，即可（我下载的版本是apache-ignite-fabric-2.3.0-bin）

# 使用sql

## 命令行
sqlline.bat --help  可以查询使用方式

启动终端
linux
> sqlline.sh --color=true --verbose=true -u jdbc:ignite:thin://127.0.0.1/
windows
> sqlline.bat --color=true --verbose=true -u jdbc:ignite:thin://127.0.0.1/


测试代码
```sql

!sql create table if not exists city (id long primary key, name varchar);
!sql insert into city (id, name) values(1, '深圳');
!sql insert into city (id, name) values(2, '广东'), (3, '东莞');
!sql select * from city;
!sql drop table city;

```
由此可看到数据已经查询出来了，不过因为数据是写在内存的，重启后数据就不存在了

!sql create table student (id LONG primary key, name varchar, age int);
!sql insert into student (id, name, age) values(1, '陈先生', 25);
!sql insert into student (id, name, age) values(2, '张先生', 21);
!sql insert into student (id, name, age) values(3, '刘德华', 41);
!sql insert into student (id, name, age) values(4, '李四生', 29);
!sql insert into student (id, name, age) values(5, '黄九生', 26);
!sql select * from student;
!sql delete from student;


## jdbc调用

pom.xml配置
```xml
<dependency>
    <groupId>org.apache.ignite</groupId>
    <artifactId>ignite-core</artifactId>
    <version>2.3.0</version>
</dependency>

<dependency>
    <groupId>org.apache.ignite</groupId>
    <artifactId>ignite-spring</artifactId>
    <version>2.3.0</version>
</dependency>

<dependency>
    <groupId>org.apache.ignite</groupId>
    <artifactId>ignite-log4j</artifactId>
    <version>2.3.0</version>
</dependency>
```


测试代码
```java
package jdbc1;

import java.sql.*;

public class HelloJdbc {
    public static void main(String[] args) throws Exception {

        Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
        String urlstr = "jdbc:ignite:thin://10.101.90.113/";
        Connection conn = DriverManager.getConnection(urlstr);
        System.out.println("连接成功");

        int test_rows = 100;

        String sqlCreate = "create table PERSON(id long primary key, age int, name varchar)";
        Statement stat = conn.createStatement();
        stat.execute(sqlCreate);

        String sql = "INSERT INTO PERSON(id, age, name) values (?,?,?)";
        for (int i = 0;i < test_rows; i ++) {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, i);
            pst.setInt(2, 2+i);
            pst.setString(3, "李四"+i);
            pst.executeUpdate();
        }


        PreparedStatement pst = conn.prepareStatement("SELECT * FROM PERSON");
        ResultSet rSet = pst.executeQuery();
        while (rSet.next()) {
            System.out.print(rSet.getInt(1) + "\t");
            System.out.print(rSet.getInt(2) + "\t");
            System.out.print(rSet.getString(3) + "\t");
            System.out.print("\n");
        }

    }
}

```

# 集群配置

(基于静态IP的发现,其他方式,暂时没有试过)

修改配置文件(config/default-config.xml,也可新建配置在启动时指定这个配置文件)

```xml
<bean class="org.apache.ignite.configuration.IgniteConfiguration">
  <property name="discoverySpi">
    <bean class="org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi">
      <property name="ipFinder">
        <bean class="org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder">
          <property name="addresses">
            <list>
              <!--
                  本机的地址
              -->
              <value>master</value>
              <!--
                  网络中的节点
              -->
              <value>node1:47500..47509</value>
              <value>node2:47500..47509</value>
            </list>
          </property>
        </bean>
      </property>
    </bean>
  </property>
</bean>
```

各节点分别启动即可
