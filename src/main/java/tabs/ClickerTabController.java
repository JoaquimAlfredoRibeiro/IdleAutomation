package tabs;

import javafx.fxml.FXML;
import org.apache.log4j.Logger;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.TextField;

public class ClickerTabController {


    private final static Logger LOG = Logger.getLogger(ClickerTabController.class);

    @FXML
    private TextField hoursTextField;

    @FXML
    private TextField minutesTextField;

    @FXML
    private TextField secondsTextField;

    @FXML
    private TextField millisecondsTextField;

    @FXML
    private JFXRadioButton repeatRadioButton;

    @FXML
    private TextField repeatTextField;

    @FXML
    private JFXRadioButton repeatUntilStoppedRadioButton;

    @FXML
    private JFXRadioButton currentLocationRadioButton;

    @FXML
    private JFXRadioButton pickLocationRadioButton;

    @FXML
    private TextField xTextField;

    @FXML
    private TextField yTextField;

    @FXML
    private JFXButton startButton;

    @FXML
    private JFXButton stopButton;

    @FXML
    private JFXButton setHotkeyButton;

    @FXML
    private JFXButton helpButton;

}
