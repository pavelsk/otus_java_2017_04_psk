package objects;

import java.util.Random;

/**
 * Created by pavel on 14.04.17.
 */
public class IntegerFactory implements ObjectFactory {

    private static Random random = new Random();

    public Object createObject() {
        return random.nextInt();
    }
}
