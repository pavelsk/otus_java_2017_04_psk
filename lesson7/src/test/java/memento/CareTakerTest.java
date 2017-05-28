package memento;

import base.BaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CareTakerTest extends BaseTest {

    @Test
    public void testSetMemento() {
        Memento<Integer> memento = new Memento<>(1);

        Caretaker<Integer> caretaker = new Caretaker<>();

        caretaker.setMemento(memento);

        assertEquals(memento, getFieldValue(caretaker, "memento"));
    }

    @Test
    public void testGetMemento() {
        Memento<Integer> memento = new Memento<>(1);

        Caretaker<Integer> caretaker = new Caretaker<>();

        setFieldValue(caretaker, "memento", memento);

        assertEquals(memento, caretaker.getMemento());
    }
}
