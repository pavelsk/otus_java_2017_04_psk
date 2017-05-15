package xunit.assertion;

public class AssertionError extends RuntimeException {

    public AssertionError(String message) {
        super(message);
    }

    public AssertionError() {
        super();
    }
}
