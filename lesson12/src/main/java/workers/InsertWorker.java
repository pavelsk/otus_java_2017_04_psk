package workers;

import db.DBService;
import helpers.DataProvider;

public class InsertWorker extends DBWorker implements Runnable {
    private static final int count = 30;

    public InsertWorker(DBService dbService) {
        super(dbService);
    }

    @Override
    public void run() {
        while (DataProvider.getSize() < count) {
            if (dbService.isOpen()) {
                dbService.save(DataProvider.generateNewUser());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
