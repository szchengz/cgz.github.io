package com.spring;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by cgz on 2019-04-01 23:12
 * 描述：使用Spring Hadoop来访问HDFS
 */
public class SpringHadoopHdfsApp {
    private ApplicationContext ctx;
    private FileSystem fileSystem;

    @Test
    public void testMkdir() throws Exception {
        fileSystem.mkdirs(new Path("springhdfs"));
    }

    @Test
    public void testText() throws Exception {
        FSDataInputStream in = fileSystem.open(new Path("/springhdfs/hello.txt"));
        IOUtils.copyBytes(in, System.out, 1024);
        in.close();
    }

    @Before
    public void setUp(){
        ctx = new ClassPathXmlApplicationContext("beans.xml");
        fileSystem = (FileSystem) ctx.getBean("fileSystem");
    }

    @After
    public void setDown() throws Exception{
        ctx = null;
        fileSystem.close();
    }
}
