package app.messages;

import app.FrontendService;
import app.Socket;
import messageSystem.Address;

public class MsgGetStatAnswer extends MsgToFrontend {
    private final String response;
    private final Socket socket;

    public MsgGetStatAnswer(Address from, Address to, String response, Socket socket) {
        super(from, to);
        this.response = response;
        this.socket = socket;
    }

    @Override
    public void exec(FrontendService frontendService) {
        socket.sendMessage(response);
    }
}
