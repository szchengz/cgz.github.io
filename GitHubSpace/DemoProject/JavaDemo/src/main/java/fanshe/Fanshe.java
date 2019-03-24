package fanshe;

import java.lang.reflect.Method;

/**
 * Created by cgz on 2019-03-23 11:41
 * 描述：
 */
public class Fanshe {
	
    public static void main(String[] args) {
        //第一种方式获取Class对象
        Student stu1 = new Student();//这一new 产生一个Student对象，一个Class对象。
        Class stuClass = stu1.getClass();//获取Class对象
        System.out.println(stuClass.getName());

        //第二种方式获取Class对象
        Class stuClass2 = Student.class;
        System.out.println(stuClass == stuClass2);//判断第一种方式获取的Class对象和第二种方式获取的是否是同一个

        //第三种方式获取Class对象
        try {
            Class stuClass3 = Class.forName("fanshe.Student");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
            System.out.println(stuClass3 == stuClass2);//判断三种方式是否获取的是同一个Class对象


//            Method[]  methods = stuClass3.getDeclaredMethods();
//            for (Method m : methods) {
//                System.out.println(m.getName());
//
//            }
            Object obj = stuClass.getConstructor().newInstance();

            Method m = stuClass.getMethod("sayHello");
            m.invoke(obj );

            Method m1 = stuClass.getMethod("sayHello", String.class);
            m1.invoke(obj , "cgz");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
