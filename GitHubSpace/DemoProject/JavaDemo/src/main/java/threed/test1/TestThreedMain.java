package threed.test1;

import threed.test1.MyThreed;

/**
 * Created by cgz on 2019-03-24 14:14
 * 描述：
 */
public class TestThreedMain {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i ++ ){
            new MyThreed("threed"+i).start();
        }
    }
}

class MyThreed extends Thread{
    public MyThreed(String name) {
        this.name = name;
    }

    private String name;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(name + "  " + (i+1));
            yield();
        }
    }
}
