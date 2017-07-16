package workers;

import db.DBService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBWorkerService {
    private final DBService dbService;

    private final ExecutorService executor;

    public DBWorkerService(DBService dbService) {
        this.dbService = dbService;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void run() {
        executor.execute(new InsertWorker(dbService));
        executor.execute(new SelectWorker(dbService));
    }

    public void stop() {
        executor.shutdown();
    }
}
