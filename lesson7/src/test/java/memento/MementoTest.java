package memento;

import base.BaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MementoTest extends BaseTest {
    @Test
    public void testGetSavedState() {
        Memento<String> memento = new Memento<>("test");

        assertEquals("test", memento.getSavedState());
    }
}
