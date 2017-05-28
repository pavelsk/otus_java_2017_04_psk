package atm;

import base.BaseTest;
import memento.Caretaker;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

public class ATMTest extends BaseTest {

    @Test
    public void testSaveState() throws Exception {
        Cell cell = new Cell(1, 1);
        Cell cell1 = new Cell(5, 10);
        Cell cell2 = new Cell(10, 10);

        ATM atm = new ATM(Collections.singletonList(cell));

        verifyPrivate(atm).invoke("saveState", Arrays.asList(cell1, cell2));

        Map map = (Map) this.getFieldValue(atm, "caretakersMap");

        assertEquals(3, map.size());
        map.forEach((key, val) -> {
            assertEquals(((Cell) key).getCount(), ((Caretaker) val).getMemento().getSavedState());
        });
    }

    @Test
    public void testNotify() throws Exception {
        Cell cell1 = new Cell(5, 10);
        Cell cell2 = new Cell(10, 10);

        ATM atm = new ATM(Arrays.asList(cell1, cell2));

        this.setFieldValue(cell1, "count", 1);
        this.setFieldValue(cell2, "count", 1);

        atm.notify(new RestoreEvent());

        assertEquals(10, cell1.getCount());
        assertEquals(10, cell2.getCount());
    }
}
