---
  title: ignite学习笔记01
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

# ignitevisorcmd操作


## 启动：
进入ignite安装目录的bin目录。
执行ignitevisorcmd.sh脚本，进入命令行界面。

执行：start -h=<host> -u=<user> -pw=<password> -n=x，启动x个节点。

## 查看：

执行：node -id8=@n0 -a，查看所启动的所有节点。

## 停止：

执行：kill -id8=<id8> -k，停止该节点。

sqlline.bat --color=true --verbose=true -u jdbc:ignite:thin://127.0.0.1/

!sql create table PERSON(id LONG primary key, name varchar, age int);
!sql insert into PERSON (id, name, age) values(1, '陈先生', 25);
!sql insert into PERSON (id, name, age) values(2, '张先生', 21);
!sql insert into PERSON (id, name, age) values(3, '刘德华', 41);
!sql select * from PERSON;
!sql delete from PERSON;



# 实例

```sql
!sql drop table if EXISTS dim_items;
!sql CREATE TABLE IF NOT EXISTS `dim_items` (
  `item_key` int,
  `item_type` tinyint,
  `item_code` char,
  `item_name` varchar,
  `parent_node` int,
  `start_time` datetime,
  `end_time` datetime,
  PRIMARY KEY (`item_key`,`item_type`,`start_time`)
);

!sql select * from dim_items;

!sql drop table if EXISTS `dim_date`;
!sql CREATE TABLE `dim_date` (
  `date_id` varchar,
  `date_key` date,
  `day` int,
  `month` int,
  `month_name` varchar,
  `year` int,
  `yearmonth` int,
  `quarter` int,
  `week` int,
  `week_name` varchar,
  PRIMARY KEY (`date_id`)
);
!sql select * from `dim_date`;



!sql drop table if EXISTS `dim_event_type`;
!sql CREATE TABLE IF NOT EXISTS `dim_event_type` (
  `event_key` int,
  `event_name` varchar,
  `start_time` datetime,
  `end_time` datetime,
  PRIMARY KEY (`event_key`,`start_time`)
);
!sql select * from `dim_event_type`;

!sql drop table if EXISTS `dw1_20_jsifs_fact_flow_day`;
!sql CREATE TABLE IF NOT EXISTS `dw1_20_jsifs_fact_flow_day` (
  `id` bigint,
  `item_type` tinyint,
  `item_key` int,
  `item_name` varchar,
  `charge_key` int,
  `event_key` int,
  `io_key` tinyint,
  `date_id` varchar,
  `flow_num` int,
  `create_time` datetime,
  `update_time` datetime,
  PRIMARY KEY (`id`)
);
!sql select * from `dw1_20_jsifs_fact_flow_day`;

!sql delete from dw1_20_jsifs_fact_flow_day;






CREATE TABLE IF NOT EXISTS Person (
  age int, id int, city_id int, name varchar, company varchar,
  PRIMARY KEY (name, id))
  WITH "template=partitioned,backups=1,affinitykey=city_id, key_type=PersonKey, value_type=MyPerson";


```
