package threed;

/**
 * Created by cgz on 2019-03-13 22:36
 * 描述：
 */
public class App {
    public static void main(String[] args) {
        HoneyPot pool = new HoneyPot();

        for(int i =0; i < 6;i ++){
            new Bee("蜜蜂-"+i, pool).start();
        }
        for(int i =0; i < 3;i ++){
            new Bear("Bear-"+i, pool).start();
        }

    }
}
