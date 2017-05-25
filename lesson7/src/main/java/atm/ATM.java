package atm;

import observer.Event;
import memento.Caretaker;

import java.util.*;

/**
 * Created by tully.
 */
public class ATM implements BalanceHolder, observer.Observer {
    private final Cell first;

    private final Map<Cell, Caretaker<Integer>> caretakersMap = new HashMap<>();

    public ATM(List<Cell> cells) {
        Collections.sort(cells);
        first = cells.get(0);
        linkCells(cells);
        saveState(cells);
    }

    public boolean withdraw(int requested) {
        return first.withdraw(requested);
    }

    public int getBalance() {
        Iterator<Cell> iterator = first.iterator();
        int balance = 0;
        while (iterator.hasNext()) {
            balance += iterator.next().getBalance();
        }
        return balance;
    }

    private void linkCells(List<Cell> cells) {
        Iterator<Cell> iterator = cells.iterator();
        Cell cellA = iterator.next();

        while (iterator.hasNext()) {
            Cell cellB = iterator.next();
            cellA.setNext(cellB);
            cellA = cellB;
        }
    }

    private void saveState(List<Cell> cells) {
        cells.forEach(cell -> {
            Caretaker<Integer> caretaker = new Caretaker<>();
            caretaker.setMemento(cell.saveState());

            this.caretakersMap.put(cell, caretaker);
        });
    }

    @Override
    public void notify(Event event) {
        if (event instanceof RestoreEvent) {
            this.caretakersMap.forEach((cell, caretaker) -> {
                cell.restoreState(caretaker.getMemento());
            });
        }
    }
}
