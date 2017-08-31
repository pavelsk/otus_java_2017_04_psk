package frontend;

import app.FrontendService;
import app.MessageSystemContext;
import app.Socket;
import app.messages.MsgGetStat;
import messageSystem.Address;
import messageSystem.Addressee;
import messageSystem.Message;

import javax.annotation.PostConstruct;

public class FrontendServiceImpl implements FrontendService, Addressee {
    private final Address address = new Address();
    private final MessageSystemContext context;

    public FrontendServiceImpl(MessageSystemContext context) {
        this.context = context;
    }

    @PostConstruct
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void handleStatsRequest(Socket socket) {
        Message message = new MsgGetStat(this.context.getMessageSystem(), getAddress(), context.getCacheAddress(), socket);
        this.context.getMessageSystem().sendMessage(message);
    }
}
