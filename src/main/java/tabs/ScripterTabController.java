package tabs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import org.apache.log4j.Logger;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class ScripterTabController {

    private final static Logger LOG = Logger.getLogger(ScripterTabController.class);

    public static void keyPressed(KeyCode keyCode) {
        LOG.debug("ScripterTab Key Pressed: " + keyCode.getName());
    }
}
