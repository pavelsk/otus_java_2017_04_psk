package workers;

import app.DBService;

public abstract class DBWorker implements Runnable {
    protected final DBService dbService;

    public DBWorker(DBService dbService) {
        this.dbService = dbService;
    }
}
