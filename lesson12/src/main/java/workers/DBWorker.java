package workers;

import db.DBService;

public class DBWorker {
    protected final DBService dbService;

    public DBWorker(DBService dbService) {
        this.dbService = dbService;
    }
}
