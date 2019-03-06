package main;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class JavaFxDispatchService extends AbstractExecutorService {
    private boolean running;

    public JavaFxDispatchService() {
        running = true;
    }

    public void shutdown() {
        running = false;
    }

    public List<Runnable> shutdownNow() {
        running = false;
        return new ArrayList<>(0);
    }

    public boolean isShutdown() {
        return !running;
    }

    public boolean isTerminated() {
        return !running;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return true;
    }

    public void execute(Runnable r) {
        Platform.runLater(r);
    }
}