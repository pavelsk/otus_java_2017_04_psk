package atm;

import observer.Event;

import java.util.ArrayList;
import java.util.List;

public class Department implements BalanceHolder {
    private List<ATM> atmList = new ArrayList<>();

    public void addATM(ATM atm) {
        atmList.add(atm);
    }

    @Override
    public int getBalance() {
        return atmList.stream().mapToInt(ATM::getBalance).sum();
    }

    public void restore() {
        Event event = new RestoreEvent();
        atmList.forEach(observer -> observer.notify(event));
    }
}
