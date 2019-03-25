package com.log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by cgz on 2019-03-25 22:14
 * 描述：
 */
public class MyClass {

    /* 获取名字为HelloWorld的Logger，若无该Logger，则根据继承结构向父节点寻找，直到找到Root Logger为止 */
    private static final Logger LOG = LoggerFactory.getLogger("HelloWorld2");

    public void method() {
        LOG.info("Hello World!");  // 打印日志信息
        LOG.info("info...");
        LOG.debug("debug...");
        LOG.warn("warn...");
        LOG.error("error...");
    }

    public static void main(String[] args) {
        new MyClass().method();

        System.out.println("========== System.getProperties() =========");
        Properties properties = System.getProperties();
        Iterator i = properties.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            System.out.println(key + "=" + value);
        }

        System.out.println("========== System.getenv() =========");
        Map map = System.getenv();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            System.out.print(entry.getKey() + "=");
            System.out.println(entry.getValue());
        }

    }
}
