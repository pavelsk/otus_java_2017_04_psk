package memento;

public class Memento<T> {
    private final T state;

    public Memento(T state) {
        this.state = state;
    }

    public T getSavedState() {
        return this.state;
    }
}
