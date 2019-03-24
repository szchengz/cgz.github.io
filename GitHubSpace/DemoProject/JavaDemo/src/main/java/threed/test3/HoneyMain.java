package threed.test3;

/**
 * 蜜蜂 + 熊
 * a)100只蜜蜂
 * b)2只熊
 * c)每只每次蜜蜂生产的蜂蜜量为 1
 * d)熊在蜂蜜满20的时候才会吃蜂蜜.
 */
public class HoneyMain {
    public static void main(String[] args) {
        Pot pot = new Pot();

        for (int i = 0; i < 20; i++) {
            Bee bee = new Bee("Bee"+i, pot);
            bee.setName("Bee"+i);
            bee.start();
        }

        for (int i = 0; i < 3; i++) {
            Bear bee = new Bear("熊"+i, pot);
            bee.setName("熊"+i);
            bee.start();

        }


    }
}
