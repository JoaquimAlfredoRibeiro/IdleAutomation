import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import tabs.ClickerTabController;
import tabs.ScripterTabController;

public class MainController {

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

}