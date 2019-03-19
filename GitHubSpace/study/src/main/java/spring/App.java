package spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cgz on 2019-03-19 23:00
 * 描述：
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("Start");
        logger.info("=============开始启动任务==============");
//        System.out.println("=============Start to Execute task==============");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml", "quartz-context.xml");

    }
}
