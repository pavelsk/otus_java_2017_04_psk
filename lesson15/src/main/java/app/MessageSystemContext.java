package app;

import messageSystem.Address;
import messageSystem.MessageSystem;

public class MessageSystemContext {
    private final MessageSystem messageSystem;

    private Address frontAddress;
    private Address cacheAddress;

    public MessageSystemContext(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public void setFrontAddress(Address frontAddress) {
        this.frontAddress = frontAddress;
    }

    public Address getCacheAddress() {
        return cacheAddress;
    }

    public void setCacheAddress(Address cacheAddress) {
        this.cacheAddress = cacheAddress;
    }
}
