package memento;

public class Caretaker<T> {
    private Memento<T> memento;

    public Memento<T> getMemento() {
        return memento;
    }

    public void setMemento(Memento<T> memento) {
        this.memento = memento;
    }
}
