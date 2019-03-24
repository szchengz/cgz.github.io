package threed.test2;

/**
 * Created by cgz on 2019-03-24 14:54
 * 描述：
 */
public class TestSellTicketMain {
    public static void main(String[] args) {
        TicketPool pool = new TicketPool();
        for (int i = 0; i < 2; i++) {
            new SellerThreed("T"+i, pool).start();
        }
    }
}
