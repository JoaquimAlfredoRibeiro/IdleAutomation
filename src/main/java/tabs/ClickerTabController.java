package tabs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

public class ClickerTabController {

    private final static Logger LOG = Logger.getLogger(ClickerTabController.class);

    @FXML
    private Button test;

    @FXML
    private void onTest() {
        System.out.println("Clicker End Point");
    }

}
