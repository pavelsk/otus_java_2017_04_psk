package atm;

import base.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ATM.class})
public class DepartmentTest extends BaseTest {

    @Test
    public void testAddATM() {
        ATM atm1 = new ATM(Collections.singletonList(new Cell(1, 2)));
        ATM atm2 = new ATM(Collections.singletonList(new Cell(1, 2)));

        Department department = new Department();

        department.addATM(atm1);
        department.addATM(atm2);

        List<ATM> list = (List<ATM>) this.getFieldValue(department, "atmList");

        assertEquals(2, list.size());
        assertEquals(list.get(0), atm1);
        assertEquals(list.get(1), atm2);
    }

    @Test
    public void testGetBalance() {
        ATM atmSpy = PowerMockito.spy(new ATM(Collections.singletonList(new Cell(1, 2))));
        ATM atmSpy1 = PowerMockito.spy(new ATM(Collections.singletonList(new Cell(1, 2))));

        Mockito.when(atmSpy.getBalance()).thenReturn(10);
        Mockito.when(atmSpy1.getBalance()).thenReturn(20);

        Department department = new Department();
        department.addATM(atmSpy);
        department.addATM(atmSpy1);

        assertEquals(30, department.getBalance());

        Mockito.verify(atmSpy).getBalance();
        Mockito.verify(atmSpy1).getBalance();
    }

    @Test
    public void testRestore() throws Exception {
        ATM atmSpy = PowerMockito.spy(new ATM(Collections.singletonList(new Cell(1, 2))));
        ATM atmSpy1 = PowerMockito.spy(new ATM(Collections.singletonList(new Cell(1, 2))));

        Department department = new Department();
        department.addATM(atmSpy);
        department.addATM(atmSpy1);

        department.restore();

        Mockito.verify(atmSpy).notify(Mockito.any(RestoreEvent.class));
        Mockito.verify(atmSpy1).notify(Mockito.any(RestoreEvent.class));
    }
}
