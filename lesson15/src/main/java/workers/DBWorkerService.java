package workers;

import app.DBService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DBWorkerService {
    private final DBService dbService;

    private final ExecutorService executor;

    public DBWorkerService(DBService dbService) {
        this.dbService = dbService;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void start() {
        executor.execute(new InsertWorker(dbService));
        executor.execute(new SelectWorker(dbService));
    }

    public void stop() {
        executor.shutdown();
    }
}
