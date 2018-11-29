---
  title: ignite学习笔记02
  date: 2018-10-11 10:20:00
  categories: ignite
  tags: [ignite, 缓存]
  description: ignite学习笔记
---

# 测试实例

## 启动终端

linux
> ignite.sh
> sqlline.sh --color=true --verbose=true -u jdbc:ignite:thin://127.0.0.1/
windows
> ignite.bat
> sqlline.bat --color=true --verbose=true -u jdbc:ignite:thin://127.0.0.1/

## 创建表结构
执行以下脚本创建表
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

!sql drop table if EXISTS `fact_flow`;
!sql CREATE TABLE IF NOT EXISTS `fact_flow` (
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

!sql CREATE INDEX idx_item_key ON fact_flow (item_key);
!sql CREATE INDEX idx_date_id ON fact_flow (date_id);
!sql CREATE INDEX idx_ikey_ekey_dateid ON fact_flow (item_key,event_key,date_id);


!sql select * from `fact_flow`;



```

## 初始化维度表数据

执行以下脚本插入数据

```sql

!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (100, 1, '229710', '中海物业', 0, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (101, 1, '22974', '华南区中心', 100, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (102, 1, '229710', '华北区中心', 100, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (103, 1, '229710', '华东区中心', 100, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (1011, 1, '229710', '深圳分公司', 101, '1900-01-01 00:00:00', '2018-10-08 09:54:58');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (1011, 1, '229710', '深圳分公司2', 101, '2018-09-06 11:22:56', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (1012, 1, '227710', '广州分公司', 101, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (1013, 1, '22979980', '惠州分公司', 101, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (1021, 1, '229710', '北京分公司', 102, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (1031, 1, '229710', '上海分公司', 103, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10111, 0, 'p2018005', '万科城', 1011, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10112, 0, 'p2049005', '阳光城', 1011, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10113, 0, 'p29005', '中海云城', 1011, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10121, 0, 'p29005', '珠江新城', 1012, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10122, 0, 'p29005', '珠江2', 1012, '1900-01-01 00:00:00', '2018-10-08 09:54:58');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10122, 0, 'p29005', '珠江233', 1012, '2018-10-08 09:54:58', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10131, 0, 'p290405', '一方天地', 1013, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10132, 0, 'p290305', '一方天地', 1013, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10133, 0, 'p2900995', '一方天地', 1013, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10211, 0, 'p201809005', '北京楼盘1', 1021, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10212, 0, 'p201809005', '北京楼盘2', 1021, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10311, 0, 'p201809005', '上海楼盘1', 1031, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10312, 0, 'p201809005', '上海楼盘2', 1031, '1900-01-01 00:00:00', '2999-12-31 00:00:00');
!sql INSERT INTO dim_items(item_key,item_type,item_code,item_name,parent_node,start_time,end_time) VALUES (10313, 0, 'p201809005', '上海楼盘3', 1031, '1900-01-01 00:00:00', '2999-12-31 00:00:00');


```


## java测试代码

```java
package jdbc1;

import util.DateUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by cgz on 2018-10-16 17:14
 * 描述：
 */
public class IgniteExample {


    static final int days = 100;    //生成多少天的数据
    static final int days_num = 10000;  //每天产生多少数据

    public static void main(String[] args) throws Exception {

        saveDim();
        saveFact();

        selectAll() ;
        selectOneTable();
        selectOneTableGroupby();
        selectThreeTableGroupby();


    }

    public static int getRandom(int min, int max) {
        Random rand = new Random();
        int randNumber = rand.nextInt(max - min + 1) + min;
        return randNumber;
    }

    public static int[] array = new int[]{ 10111,10112,10113,
            10121, 10122, 10122, 10131,  10132,  10133,  10211,    10212,  10311,10312,      10313
    };

    public static void saveDim() throws Exception {
        Long startTime = System.currentTimeMillis();
        String sql = "INSERT INTO dim_date(date_id,date_key,day,month,month_name,year,yearmonth,quarter,week,week_name) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Connection conn = getConn();
        PreparedStatement pst = conn.prepareStatement(sql);
        java.util.Date sdate = new java.util.Date();
        for (int ind_day =0; ind_day < days; ind_day ++) {
            java.util.Date date = DateUtil.next(sdate, 0 - ind_day);

            pst.setString(1, DateUtil.format( date, "yyyy-MM-dd"));
            pst.setDate(2, new Date(date.getTime()));
            pst.setInt(3, DateUtil.getDay(date));
            pst.setInt(4, DateUtil.getMonth(date));
            pst.setString(5, DateUtil.getMonthStr(date));
            pst.setInt(6, DateUtil.getYear(date));
            pst.setInt(7, DateUtil.getYearMonth(date));
            pst.setInt(8, DateUtil.getQuarter(date));
            pst.setInt(9, DateUtil.getWeek(date));
            pst.setString(10, DateUtil.getWeekStr(date));
            pst.addBatch();
        }
        pst.executeBatch();

//        conn.commit();//2,进行手动提交（commit）
        printlnTime(startTime, days);

    }

    public static void saveFact() throws Exception {
        Long startTime = System.currentTimeMillis();

        String sql = "insert into fact_flow(id,item_key,item_name,event_key,date_id,flow_num,create_time) values (?,?,?,?,?,?,?)";
        String stime = "2018-09-30";
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        java.util.Date t = ft.parse(stime);
        long long_stime = t.getTime();

        PreparedStatement pst = getConn().prepareStatement(sql);
        Connection conn = getConn();
        conn.setAutoCommit(false);
        int id = 1;

        for (int ind_day =0; ind_day < days; ind_day ++) {
            long_stime = long_stime - (24*60*60*100);
            for (int ind_num =0; ind_num < days_num; ind_num ++) {
                pst.setInt(1, id);
                pst.setInt(2, array[getRandom(0,13)]);
                pst.setString(3, "项目"+ ind_num);
                pst.setInt(4, getRandom(0,4));
                pst.setString(5, ft.format(long_stime));
//                pst.setDate(5, new Date(long_stime));
                pst.setInt(6, getRandom(3,45));
                pst.setDate(7, new Date(long_stime));
                id ++;
                pst.addBatch();
            }
            pst.executeBatch();
            System.out.println(ind_day + " 插入成功！");
            // 若成功执行完所有的插入操作，则正常结束
            conn.commit();//2,进行手动提交（commit）
//            System.out.println("提交成功!");
//            conn.setAutoCommit(true);//3,提交完成后回复现场将Auto commit,还原为true,

        }

        printlnTime(startTime, days* days_num);


    }

    public static void selectOneTableGroupby()  throws SQLException {
        Long startTime = System.currentTimeMillis();
        int rowCount = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("select d.item_key, d.event_key, d.date_id, sum(d.flow_num) ");
        sb.append(" from fact_flow d ");
        sb.append(" group by d.item_key, d.event_key, d.date_id");
//        sb.append(" and d.item_key=10122 and d.date_key='2018-10-13' and d.event_key=3 and d.flow_num>40 ");

        PreparedStatement pst = getConn().prepareStatement(sb.toString());
        ResultSet rSet = pst.executeQuery();
        while (rSet.next()) {
            rowCount ++;
            System.out.print(rSet.getInt(1) + "\t");
            System.out.print(rSet.getInt(2) + "\t");
            System.out.print(rSet.getString(3) + "\t");
            System.out.print(rSet.getInt(4) + "\t");

            System.out.print("\n");
        }

        printlnTime(startTime, rowCount);

    }

    public static void selectThreeTableGroupby()  throws SQLException {
        Long startTime = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        sb.append("select d.item_key, i.item_name, dd.week_name, d.event_key, sum(d.flow_num) ");
        sb.append(" from fact_flow d, dim_items i, dim_date dd");
        sb.append(" where d.date_id=dd.date_id and d.item_key=i.item_key ");
        sb.append(" and d.item_key=10122 and d.date_id='2018-09-20' and d.flow_num>40 ");
        sb.append(" group by d.item_key, i.item_name, dd.week_name, d.event_key");

        PreparedStatement pst = getConn().prepareStatement(sb.toString());
        ResultSet rSet = pst.executeQuery();
        // 遍历结果

        int rowCount = 0;

        while (rSet.next()) {
            rowCount ++;
            System.out.print(rSet.getInt(1) + "\t");
            System.out.print(rSet.getString(2) + "\t");
            System.out.print(rSet.getString(3) + "\t");
            System.out.print(rSet.getString(4) + "\t");
            System.out.print(rSet.getInt(5) + "\t");

            System.out.print("\n");
        }

        printlnTime(startTime, rowCount);

    }

    public static void selectOneTable() throws SQLException {
        Long startTime = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        sb.append("select d.id, d.item_key,d.item_name,d.event_key,d.date_id,d.flow_num");
        sb.append(" from fact_flow d");
        sb.append(" where ");
        sb.append(" d.item_key=10122 and d.date_id='2018-09-20' and d.event_key=3 and d.flow_num>40 ");

        PreparedStatement pst = getConn().prepareStatement(sb.toString());
        ResultSet rSet = pst.executeQuery();
        // 遍历结果

        int rowCount = 0;

        while (rSet.next()) {
            rowCount ++;
            System.out.print(rSet.getInt(1) + "\t");
            System.out.print(rSet.getInt(2) + "\t");
            System.out.print(rSet.getString(3) + "\t");
            System.out.print(rSet.getInt(4) + "\t");
            System.out.print(rSet.getString(5) + "\t");
            System.out.print(rSet.getInt(6) + "\t");
//            System.out.print(rSet.getString(7) + "\t");
//            System.out.print(rSet.getString(8) + "\t");
            System.out.print("\n");
        }


        printlnTime(startTime, rowCount);

    }

    public static void selectAll() throws SQLException {

        Long startTime = System.currentTimeMillis();

        String sqlString = "select id,item_key,item_name,event_key,date_id,flow_num from fact_flow";

        PreparedStatement pst = getConn().prepareStatement(sqlString);
        ResultSet rSet = pst.executeQuery();

        int rowCount = 0;
        while (rSet.next()) {
            rowCount ++;
//            System.out.print(rSet.getInt(1) + "\t");
//            System.out.print(rSet.getInt(2) + "\t");
//            System.out.print(rSet.getString(3) + "\t");
//            System.out.print(rSet.getInt(4) + "\t");
//            System.out.print(rSet.getDate(5) + "\t");
//            System.out.print(rSet.getInt(6) + "\t");
//            System.out.print("\n");
        }

        printlnTime(startTime, rowCount);
    }

    //打印执行情况
    public static void printlnTime(Long startTime, int testrows) {
        Long endTime=System.currentTimeMillis(); //获取结束时间
        float interval = endTime-startTime == 0 ? 1 : endTime-startTime;
        float tpms = (float) testrows /interval;
        System.out.println("=======数据量==="+ testrows+"=========");
        System.out.println("运行时间： "+ interval+"ms    -->>  " +  interval/1000 + " s" );
        System.out.println("每毫秒:"+tpms+"条。");
        System.out.println("每秒:"+tpms*1000+"条。");
        System.out.println("===================================");
    }

    public static void save(int test_rows) throws Exception {
        Long startTime = System.currentTimeMillis();

        String sql = "insert into fact_flow(id,item_key,item_name,event_key,date_key,flow_num,create_time) values (?,?,?,?,?,?,?)";

        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        String stime = "2018-10-15";
        java.util.Date t = ft.parse(stime);
        long long_stime = t.getTime();

        for (int i = 0;i < test_rows; i ++) {
            long_stime = long_stime - (24*60*60*100);
            PreparedStatement pst = getConn().prepareStatement(sql);
            pst.setInt(1, i);
            pst.setInt(2, array[getRandom(0,13)]);
            pst.setString(3, "项目"+i);
            pst.setInt(4, getRandom(0,4));
            pst.setDate(5, new Date(long_stime));
            pst.setInt(6, getRandom(3,45));
            pst.setDate(7, new Date(long_stime));
            pst.executeUpdate();
        }

        System.out.println("添加成功");

        Long endTime=System.currentTimeMillis(); //获取结束时间
        float interval = endTime-startTime == 0 ? 1 : endTime-startTime;
        float tpms = (float) test_rows /interval;
        System.out.println("=======数量==="+ test_rows+"=========");
        System.out.println("程序运行时间： "+ interval+"ms");
        System.out.println("每毫秒写入:"+tpms+"条。");
        System.out.println("每秒写入:"+tpms*1000+"条。");
        System.out.println("===================================");

    }

    static Connection conn = null;
    public static Connection getConn() {
        // Register JDBC driver
        try {
            if(conn == null) {
                Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
//                String urlstr = "jdbc:ignite:thin://192.168.52.128/";
                String urlstr = "jdbc:ignite:thin://10.101.90.113/";
                conn = DriverManager.getConnection(urlstr);
                System.out.println("连接成功");

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

```
