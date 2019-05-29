package com.bdp.main;

import java.io.*;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by cgz on 2019-04-09 14:18
 * 描述：
 */
public class SpringHadoopHDFSApp {

    private ApplicationContext ctx;
    private FileSystem fileSystem;
    //创建目录
    @Test
    public void testMkdir()throws Exception
    {
        fileSystem.mkdirs(new Path("/springhdfs"));
    }

    //读取HDFS文件内容
    @Test
    public void testText()throws Exception
    {
        FSDataInputStream in = fileSystem.open(new Path("/springhdfs/hello.txt"));
        IOUtils.copyBytes(in, System.out,2048);
        in.close();
    }
    public void create()throws Exception{
        FSDataOutputStream out=fileSystem.create(new Path("/springhdfs/a.txt"));
        out.write("hello Hadoop".getBytes());
        out.flush();
        out.close();
    }
    @Test
    public void rename()throws Exception{
        Path oldPath= new Path("/springhdfs/hello.txt");
        Path newPath=new Path("/springhdfs/hello1.txt");
        fileSystem.rename(oldPath,newPath);
    }
    /*
     *从本地 上传文件到hdfs
     */
    @Test
    public void copyFromLocalFiles()throws Exception{
        Path localParh= new Path("D:\\合并小文件.txt");
        Path newPath=new Path("/springhdfs");
        fileSystem.copyFromLocalFile(localParh,newPath);
    }
    /*
     *带进度条的上传
     */
    @Test
    public void copyFromLocalFileswithProgress()throws Exception{
        InputStream in =new BufferedInputStream(new FileInputStream(new File("D:\\电子书\\Cloudera Hadoop大数据平台实战指南@www.java1234.com.pdf")));
        FSDataOutputStream outputStream=fileSystem.create(new Path("/springhdfs/Cloudera Hadoop大数据平台实战指南.pdf")
                , new Progressable() {
                    public void progress() {
                        System.out.print("*");
                    }
                });
        IOUtils.copyBytes(in,outputStream,4096);
    }

    @Before
    public void setUp(){
        ctx=new ClassPathXmlApplicationContext("beans.xml");
        fileSystem=(FileSystem) ctx.getBean("fileSystem");
    }

    @After
    public void tearDown()throws Exception{
        ctx=null;
        fileSystem.close();
    }
}
