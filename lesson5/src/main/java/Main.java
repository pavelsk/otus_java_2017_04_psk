import xunit.XUnit;
import test.ManTest;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        String[] classes = new String[]{"test.ManTest", "test.machines.CarTest"};
        XUnit.test(classes);

        Class[] clazzes = new Class[]{ManTest.class};
        XUnit.test(clazzes);

        XUnit.test("test");

        XUnit.test("test.machines");
    }
}
