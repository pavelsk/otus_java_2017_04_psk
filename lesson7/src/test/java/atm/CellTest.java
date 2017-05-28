package atm;

import base.BaseTest;
import memento.Memento;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CellTest extends BaseTest {
    @Test
    public void testSaveState() throws Exception {
        Cell cell = new Cell(5, 10);

        Memento memento = cell.saveState();

        assertEquals(10, memento.getSavedState());
    }

    @Test
    public void testRestoreState() {
        Cell cell = new Cell(5, 10);
        Memento<Integer> memento = new Memento<>(15);

        cell.restoreState(memento);

        assertEquals(15, cell.getCount());
    }
}
