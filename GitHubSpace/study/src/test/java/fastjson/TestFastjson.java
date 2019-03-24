package fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.List;

/**
 * Created by cgz on 2019-03-23 11:23
 * 描述：
 */
public class TestFastjson {

    @Test
    public void test1(){
        // 对象嵌套数组嵌套对象
        String json1 = "{'id':1,'name':'JAVAEE-1703','stus':[{'id':101,'name':'刘铭','age':16}]}";
        // 数组
        String json2 = "['北京','天津','杭州']";
        //1、
        //静态方法
        JSONObject json= JSON.parseObject(json1, JSONObject.class);
        String id = json.getString("id");
        String name = json.getString("name");
        System.out.println(id);
        System.out.println(name);

        //2、
        List<String> list=JSON.parseArray(json2, String.class);
        System.out.println(list);
    }
}
