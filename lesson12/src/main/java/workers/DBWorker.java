package workers;

import db.DBService;

public abstract class DBWorker {
    protected final DBService dbService;

    public DBWorker(DBService dbService) {
        this.dbService = dbService;
    }
}
