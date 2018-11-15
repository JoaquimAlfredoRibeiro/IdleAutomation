package tabs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

public class ScripterTabController {

    private final static Logger LOG = Logger.getLogger(ScripterTabController.class);

    @FXML
    private Button endPoint;

    @FXML
    private void onEndPoint() {
        System.out.println("Scripter End Point");
    }
}
