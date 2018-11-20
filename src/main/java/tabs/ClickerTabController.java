package tabs;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.TextField;

import java.util.concurrent.TimeUnit;

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

    private KeyCode startHotKey;
    private Long xScreen;
    private Long yScreen;


    @FXML
    public void initialize() {
        LOG.debug("Clicker Tab Initialized.");

        //default startHotKey
        setStartHotkey(KeyCode.PLUS);

        //default Click Interval
        millisecondsTextField.setText("100");

        //define character limit for textfields
        limitTextfields();

    }

    @FXML
    private boolean onSetHotkeyKeyPressed(KeyEvent event) {

        LOG.debug("onSetHotKeyPressed: " + event.getCode());

        setHotkeyButton.getParent().requestFocus();

        return setStartHotkey(event.getCode());
    }

    //set startHotKey and update Start and Stop Buttons texts
    private boolean setStartHotkey(KeyCode keyCode) {

        this.startHotKey = keyCode;

        startButton.setText("Start (" + startHotKey.getName() + ")");
        stopButton.setText("Stop (" + startHotKey.getName() + ")");

        return true;
    }

    //calculate Click Interval in milliseconds
    @FXML
    private long getClickIntervalMillis() {

        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        if (!StringUtils.isEmpty(hoursTextField.getText()))
            hours = TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(Long.valueOf(hoursTextField.getText())));

        if (!StringUtils.isEmpty(minutesTextField.getText()))
            minutes = TimeUnit.SECONDS.toMillis(TimeUnit.MINUTES.toSeconds(Long.valueOf(minutesTextField.getText())));

        if (!StringUtils.isEmpty(secondsTextField.getText()))
            seconds = TimeUnit.SECONDS.toMillis(Long.valueOf(secondsTextField.getText()));

        //no need to check if it is empty, it has a default value
        long milliseconds = Integer.valueOf(millisecondsTextField.getText());

        LOG.debug("Hours in milliseconds: " + hours);
        LOG.debug("Minutes in milliseconds: " + minutes);
        LOG.debug("Seconds in milliseconds: " + seconds);
        LOG.debug("Milliseconds: " + milliseconds);
        LOG.debug("Total: " + (hours + minutes + seconds + milliseconds));

        return hours + minutes + seconds + milliseconds;
    }

    @FXML
    private void testmethod() {
        //PLACEHOLDER
    }

    //Defines custom Click Position
    @FXML
    private void onPickLocationMouseRelease(MouseEvent event) {

        xScreen = new Long(Math.round(event.getScreenX()));
        yScreen = new Long(Math.round(event.getScreenY()));

        xTextField.setText(xScreen.toString());
        yTextField.setText(yScreen.toString());
    }

    //defines the character limit for the various textfields
    private void limitTextfields() {

        hoursTextField.setOnKeyTyped(a -> {
            int maxCharacters = 1;
            if (hoursTextField.getText().length() > maxCharacters) a.consume();
        });

        minutesTextField.setOnKeyTyped(a -> {
            int maxCharacters = 1;
            if (minutesTextField.getText().length() > maxCharacters) a.consume();
        });

        secondsTextField.setOnKeyTyped(a -> {
            int maxCharacters = 1;
            if (secondsTextField.getText().length() > maxCharacters) a.consume();
        });

        millisecondsTextField.setOnKeyTyped(a -> {
            int maxCharacters = 2;
            if (millisecondsTextField.getText().length() > maxCharacters) a.consume();
        });

        xTextField.setOnKeyTyped(a -> {
            int maxCharacters = 3;
            if (xTextField.getText().length() > maxCharacters) a.consume();
        });

        yTextField.setOnKeyTyped(a -> {
            int maxCharacters = 3;
            if (yTextField.getText().length() > maxCharacters) a.consume();
        });
    }

}
