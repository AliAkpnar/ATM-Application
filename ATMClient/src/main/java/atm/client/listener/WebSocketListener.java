package atm.client.listener;

import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@EnableWebSocket
public class WebSocketListener implements StompSessionHandler {
    private static final String ATM_WS_URL = "ws://localhost:9091/atm";
    private StompSession session;
    private WebSocketStompClient stompClient;

    public void prepare() throws ExecutionException, InterruptedException, TimeoutException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient.connectAsync(ATM_WS_URL, this).get();
        stompClient.setAutoStartup(true);
        this.stompClient = stompClient;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;
        session.subscribe("/", this);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.err.println("Error in message: " + new String(payload, StandardCharsets.UTF_8));
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        System.err.println("Connection error. Retrying connection");
        try {
            this.session = stompClient.connectAsync(ATM_WS_URL, this).get(30,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return String.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        System.out.println(payload);
    }

    public StompSession getSession() {
        return session;
    }
}
