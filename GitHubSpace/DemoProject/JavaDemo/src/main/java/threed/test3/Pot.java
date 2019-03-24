package threed.test3;

import threed.test2.SellerThreed;

/**
 * 蜜罐：资源
 */
public class Pot {
    private final  static  int max = 5;
    private int number;

    //往蜜罐加密，没有达到最大值就加入成功
    public synchronized void add(){
        while (number == max) {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("       "+ name + " wait....");
                this.wait();
                System.out.println("       "+ name + " wait....OK...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        number ++;
        this.notify();
    }

    public synchronized void sub(){
        while (number < max) {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("       "+ name + " wait....");
                this.wait();
                System.out.println("       "+ name + " wait....OK...");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        number = number - max;
        this.notify();
    }


}
