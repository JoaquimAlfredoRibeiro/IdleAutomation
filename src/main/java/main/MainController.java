package main;

import com.jfoenix.controls.JFXTabPane;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import tabs.ClickerTabController;
import tabs.ScripterTabController;

import java.util.logging.LogManager;

public class MainController extends Application implements NativeKeyListener {

    private final static Logger LOG = Logger.getLogger(MainController.class);

    // Inject tab content.
    @FXML
    private Tab clickerTabTitle;
    // Inject controller
    @FXML
    private ClickerTabController clickerTabController;
    // Inject tab content.
    @FXML
    private Tab scripterTabTitle;
    // Inject controller
    @FXML
    private ScripterTabController scripterTabController;
    @FXML
    private JFXTabPane tabPane;

    private static final String CLICKER_TAB = "clickerTab";
    private static final String SCRIPTER_TAB = "scripterTab";
    private static String selectedTabId;

    public void start(Stage primaryStage) throws Exception {
        LOG.debug("Application initiated.");

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Idle Automation");
        primaryStage.setScene(new Scene(root, 450, 420));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void initialize() {

        selectedTabId = CLICKER_TAB;

        //tab change listener
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        selectedTabId = t1.getId();
                        LOG.debug("Tab Changed: " + selectedTabId);
                    }
                }
        );

        // Set logging defaults of JNativeHook
        // Clear previous logging configurations
        LogManager.getLogManager().reset();

        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARN);

        //Set the event dispatcher for thread safety
        GlobalScreen.setEventDispatcher(new JavaFxDispatchService());

        //register native hook for application
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(this);
    }

    //redirects native key presses to appropriate controllers
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

        //Convert from NativeKeyEvent to Javafx KeyCode
        KeyEvent keyEvent = new JavaFXKeyAdapter().getJavaFXKeyEvent(e, KeyEvent.KEY_PRESSED);
        KeyCode keyCode = keyEvent.getCode();

        LOG.debug("Pressed key: " + keyCode.getName());

        if (CLICKER_TAB.equals(selectedTabId)) {
            clickerTabController.keyPressed(keyCode);
        } else if (SCRIPTER_TAB.equals(selectedTabId)) {
            scripterTabController.keyPressed(keyCode);
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
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

}