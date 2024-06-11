package atm.client;

import atm.client.listener.CommandListener;
import atm.client.listener.WebSocketListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component
public class Application {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("atm.client");
        applicationContext.refresh();
        applicationContext.start();
        applicationContext.getBean(WebSocketListener.class).prepare();
        applicationContext.getBean(CommandListener.class)
                .listen(applicationContext.getBean(WebSocketListener.class));

    }
}
