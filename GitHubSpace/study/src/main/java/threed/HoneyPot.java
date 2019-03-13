package threed;

import java.util.LinkedList;

/**
 * Created by cgz on 2019-03-13 22:26
 * 描述：
 */
public class HoneyPot {

    private LinkedList<Integer> pool = new LinkedList<Integer>();

    private int number = 0;


    public synchronized int add(){
//        synchronized(this){
            number ++;
            pool.add(number);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//        }

        return number;
    }

    public synchronized int remove(){
        while(pool.isEmpty()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pool.removeFirst();
    }
}
