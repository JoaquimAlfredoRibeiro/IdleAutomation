package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class MainWindow extends Application {

    private final static Logger LOG = Logger.getLogger(MainWindow.class);

    public void start(Stage primaryStage) throws Exception {
        LOG.debug("Application initiated.");

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Idle Automation");
        primaryStage.setScene(new Scene(root, 450, 420));
        primaryStage.show();
    }

    public void stop() throws Exception {

        LOG.debug("Unregistering Native Hook");

        //unregister Native Hook
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e1) {
            e1.printStackTrace();
        }

        LOG.debug("Closing Application");
    }

    public static void main(String[] args) {
        launch(args);
    }
}