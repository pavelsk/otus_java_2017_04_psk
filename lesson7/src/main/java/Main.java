import atm.ATM;
import atm.Cell;
import atm.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tully.
 */
public class Main {
    public static void main(String[] args) {
        Department department = new Department();

        List<Cell> firstATMCells = new ArrayList<>();

        firstATMCells.add(new Cell(1, 10));
        firstATMCells.add(new Cell(5, 10));
        firstATMCells.add(new Cell(10, 10));
        firstATMCells.add(new Cell(50, 10));
        firstATMCells.add(new Cell(100, 10));

        List<Cell> secondATMCells = new ArrayList<>();

        secondATMCells.add(new Cell(1, 20));
        secondATMCells.add(new Cell(5, 15));
        secondATMCells.add(new Cell(10, 10));
        secondATMCells.add(new Cell(50, 15));
        secondATMCells.add(new Cell(100, 5));

        ATM atm1 = new ATM(firstATMCells);
        ATM atm2 = new ATM(secondATMCells);

        department.addATM(atm1);
        department.addATM(atm2);

        System.out.println("Initial department balance: " + department.getBalance());

        System.out.println("atm1 balance: " + atm1.getBalance());
        System.out.println("atm1 withdraw: 1578");
        atm1.withdraw(1578);
        System.out.println("amt1 balance: " + atm1.getBalance());

        System.out.println("Department balance: " + department.getBalance());

        System.out.println("atm2 balance: " + atm2.getBalance());
        System.out.println("atm2 withdraw: 738");
        atm2.withdraw(738);
        System.out.println("amt2 balance: " + atm2.getBalance());

        System.out.println("Department balance: " + department.getBalance());

        System.out.println("Department restore");
        department.restore();

        System.out.println("Department balance: " + department.getBalance());
    }
}
