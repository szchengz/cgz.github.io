package threed.test2;

import java.util.LinkedList;

/**
 * Created by cgz on 2019-03-24 14:44
 * 描述：
 */
public class TicketPool {

    private int number = 100;

    public synchronized int getTickNo(){
        int num = number;
        if(num == 0){
            return 0;
        }
        number --;
        return num;
    }

}
