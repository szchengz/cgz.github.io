package threed;

/**
 * Created by cgz on 2019-03-13 22:16
 * 描述：熊
 */
public class Bear extends Thread {

    public Bear(String name, HoneyPot honeyPot) {
        this.name = name;
        this.honeyPot = honeyPot;
    }

    private String name;
    private int index = 0;
    private HoneyPot honeyPot;

    public void run() {
        while(true){
            int n = honeyPot.remove( );
            System.out.println(name + " get : " + n);
            yield();
        }
    }

}
