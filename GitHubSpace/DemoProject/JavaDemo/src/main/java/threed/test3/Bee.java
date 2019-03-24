package threed.test3;

/**
 * 蜜蜂：生产者，产生蜂蜜到蜜罐
 */
public class Bee extends Thread{

    private String name;
    private Pot pot ;
    private int index = 1;

    public Bee(String name, Pot pot) {
        this.name = name;
        this.pot = pot;
    }

    @Override
    public void run() {
        for (;;) {
            pot.add();
            System.out.println(name + "  " + index);
            index ++;
        }
    }
}
