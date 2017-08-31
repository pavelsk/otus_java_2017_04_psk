package app.messages;

import app.CacheEngine;
import messageSystem.Address;
import messageSystem.Addressee;
import messageSystem.Message;

public abstract class MsgToCache extends Message {
    public MsgToCache(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof CacheEngine) {
            exec((CacheEngine) addressee);
        }
    }

    public abstract void exec(CacheEngine cacheEngine);
}
