package main;

import com.jfoenix.controls.JFXTabPane;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import jdk.nashorn.internal.objects.Global;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import tabs.ClickerTabController;
import tabs.ScripterTabController;

import java.util.logging.LogManager;

public class MainController implements NativeKeyListener {

    private final static Logger LOG = Logger.getLogger(MainController.class);

    // Inject tab content.
    @FXML
    private Tab clickerTab;
    // Inject controller
    @FXML
    private ClickerTabController clickerTabController;

    // Inject tab content.
    @FXML
    private Tab scripterTab;
    // Inject controller
    @FXML
    private ScripterTabController scripterTabController;

    @FXML
    private JFXTabPane tabPane;

    private static final String CLICKER_TAB = "clickerTab";
    private static final String SCRIPTER_TAB = "scripterTab";
    private static String selectedTabId;

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

        //register native hook for application
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new MainController());

        // Set logging defaults of JNativeHook
        // Clear previous logging configurations
        LogManager.getLogManager().reset();

        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARN);
    }

    //redirects native key presses to appopriate controllers
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {

        if (CLICKER_TAB.equals(selectedTabId)) {
            ClickerTabController.keyPressed(NativeKeyEvent.getKeyText(e.getKeyCode()));
        } else if (SCRIPTER_TAB.equals(selectedTabId)) {
            ScripterTabController.keyPressed(e.getKeyCode());
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }
}