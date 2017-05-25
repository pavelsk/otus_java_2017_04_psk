package observer;

@FunctionalInterface
public interface Observer {
    void notify(Event event);
}
