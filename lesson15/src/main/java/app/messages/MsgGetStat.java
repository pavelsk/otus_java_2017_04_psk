package app.messages;

import app.CacheEngine;
import app.Socket;
import messageSystem.Address;
import messageSystem.MessageSystem;
import org.json.simple.JSONObject;

public class MsgGetStat extends MsgToCache {
    private final MessageSystem messageSystem;
    private final Socket socket;

    public MsgGetStat(MessageSystem messageSystem, Address from, Address to, Socket socket) {
        super(from, to);
        this.messageSystem = messageSystem;
        this.socket = socket;
    }


    @Override
    public void exec(CacheEngine cacheEngine) {
        JSONObject object = new JSONObject();
        object.put("hitCount", cacheEngine.getHitCount());
        object.put("missCount", cacheEngine.getMissCount());

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messageSystem.sendMessage(new MsgGetStatAnswer(getTo(), getFrom(), object.toJSONString(), socket));
    }
}
