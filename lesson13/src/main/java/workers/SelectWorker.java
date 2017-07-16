package workers;

import db.DBService;
import helpers.DataProvider;

public class SelectWorker extends DBWorker {
    public SelectWorker(DBService dbService) {
        super(dbService);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (dbService.isOpen()) {
                String name = DataProvider.getRandomUserName();
                if (name != null) {
                    dbService.readByName(name);
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
