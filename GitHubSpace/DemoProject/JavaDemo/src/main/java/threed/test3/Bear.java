package threed.test3;

import java.util.concurrent.Callable;

/**
 * 熊：消费者，从蜜罐拿密
 */
public class Bear extends Thread {
    private String name;
    private Pot pot ;
    private int index = 1;

    public Bear(String name, Pot pot) {
        this.name = name;
        this.pot = pot;
    }

    @Override
    public void run(){

        for (;;) {
            pot.sub();
            System.out.println(name + "  " + index);
            index ++;
        }

    }
}
