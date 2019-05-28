package com.bdp.main;

import org.apache.hadoop.fs.FileStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.hadoop.fs.FsShell;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by cgz on 2019-04-09 15:04
 * 描述：
 */
@SpringBootApplication
public class SpringBootApp implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SpringBootApp.class);

    @Autowired
    FsShell fsShell;

    public void run(String... strings) throws Exception {
        Collection<FileStatus> lst =  fsShell.lsr("/springhdfs");
        for(FileStatus fileStatus : lst) {
            System.out.println("> " + fileStatus.getPath());
            LOG.info("------启动------------");
        }
    }

    public static void main(String[] args) {
        LOG.info("------启动------------");
        SpringApplication.run(SpringBootApp.class, args);
    }
}
