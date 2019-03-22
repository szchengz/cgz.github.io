package com.bdp.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cgz on 2019-03-14 14:02
 * 描述：
 */
public class Test4j {

    private static final Logger LOG = LoggerFactory.getLogger(Test4j.class);

    public static void main(String[] args) {
        LOG.debug("ss");
        LOG.info("00000");
        LOG.warn("wwwwwww");
        LOG.error("eeeeeeeeeeee");

        String s = System.getenv("BDP_LOG_HOME");
        System.out.println(s);

    }
}
