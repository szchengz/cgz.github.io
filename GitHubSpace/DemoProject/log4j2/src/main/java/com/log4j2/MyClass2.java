package com.log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cgz on 2019-03-25 23:02
 * 描述：
 */
public class MyClass2 {

    /* 获取名字为HelloWorld的Logger，若无该Logger，则根据继承结构向父节点寻找，直到找到Root Logger为止 */
    private static final Logger LOG = LoggerFactory.getLogger(MyClass2.class);

    public void method() {
        LOG.info("Hello World!");  // 打印日志信息
        LOG.info("info...");
        LOG.debug("debug...");
        LOG.warn("warn...");
        LOG.error("error...");
    }

    public static void main(String[] args) {
        new MyClass2().method();
    }
}
