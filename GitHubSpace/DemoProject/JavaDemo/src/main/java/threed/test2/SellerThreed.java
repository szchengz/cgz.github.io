package threed.test2;

/**
 * Created by cgz on 2019-03-24 14:46
 * 描述：
 */
public class SellerThreed extends Thread {

    private TicketPool pool;
    private String name;

    public SellerThreed(String name, TicketPool pool) {
        this.name = name;
        this.pool = pool;
    }

    @Override
    public void run() {
        for (;;) {
            int num = pool.getTickNo();
            if(num == 0) {
//                System.out.println(name +  " OVER" );
                break;
            } else
                System.out.println(name + ":" +num);
        }
    }
}
