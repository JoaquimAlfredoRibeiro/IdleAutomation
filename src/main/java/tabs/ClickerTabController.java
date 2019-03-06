package tabs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Robot;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.concurrent.TimeUnit;

public class ClickerTabController {

    private final static Logger LOG = Logger.getLogger(ClickerTabController.class);

    private static KeyCode startHotKey;
    private Thread robotThread;

    @FXML
    private TextField hoursTextField;
    @FXML
    private TextField minutesTextField;
    @FXML
    private TextField secondsTextField;
    @FXML
    private TextField millisecondsTextField;
    @FXML
    private TextField repeatTextField;
    @FXML
    private JFXRadioButton repeatUntilStoppedRadioButton;
    @FXML
    private JFXRadioButton currentLocationRadioButton;
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
    private JFXComboBox mouseButtonComboBox;

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

    public void keyPressed(KeyCode keyCode) {

        //start-stop clicker
        if (keyCode == startHotKey) {

            if (robotThread != null && robotThread.isAlive()) {
                Robot.stopLooping();
            } else {
                initiateRobot();
            }
        }

        //always terminate clicker on escape
        if (keyCode == KeyCode.ESCAPE) {
            Robot.stopLooping();
        }

    }

    @FXML
    private boolean onSetHotkeyKeyPressed(KeyEvent event) {

        LOG.debug("Start Hotkey set to: " + event.getCode());

        setHotkeyButton.getParent().requestFocus();

        return setStartHotkey(event.getCode());
    }

    //set startHotKey and update Start and Stop Buttons texts
    private boolean setStartHotkey(KeyCode keyCode) {

        startHotKey = keyCode;
        startButton.setText("Start (" + startHotKey.getName() + ")");
        stopButton.setText("Stop (" + startHotKey.getName() + ")");

        return true;
    }

    //calculate Click Interval in milliseconds
    @FXML
    private int getClickIntervalMillis() {

        long hours = 0;
        long seconds = 0;
        long minutes = 0;
        long milliseconds = 0;

        if (!StringUtils.isEmpty(hoursTextField.getText()))
            hours = TimeUnit.SECONDS.toMillis(TimeUnit.HOURS.toSeconds(Long.valueOf(hoursTextField.getText())));

        if (!StringUtils.isEmpty(minutesTextField.getText()))
            minutes = TimeUnit.SECONDS.toMillis(TimeUnit.MINUTES.toSeconds(Long.valueOf(minutesTextField.getText())));

        if (!StringUtils.isEmpty(secondsTextField.getText()))
            seconds = TimeUnit.SECONDS.toMillis(Long.valueOf(secondsTextField.getText()));

        if (!StringUtils.isEmpty(millisecondsTextField.getText()))
            milliseconds = Integer.valueOf(millisecondsTextField.getText());

        LOG.debug("Total delay: " + (hours + minutes + seconds + milliseconds));

        int total = Math.toIntExact(hours + minutes + seconds + milliseconds);

        //minimum event delay
        return total <= 10 ? 10 : total;
    }

    private void initiateRobot() {

        LOG.debug("Clicker Robot Initialized");
        try {
            Robot robot = new Robot(getClickIntervalMillis(), getMouseButtonValue(), getClickRepeatValue(),
                                    currentLocationRadioButton.isSelected(), getXTextFieldValue(),
                                    getYTextFieldValue());

            robotThread = new Thread(robot);
            robotThread.start();

        } catch (AWTException ex) {
            System.out.println("Error: Unable to start robot thread!");
        }
    }

    private int getXTextFieldValue() {
        if (xTextField.getText().isEmpty()) {
            return 0;
        }

        return Integer.parseInt(xTextField.getText());
    }

    private int getYTextFieldValue() {
        if (yTextField.getText().isEmpty()) {
            return 0;
        }

        return Integer.parseInt(yTextField.getText());
    }

    private int getMouseButtonValue() {
        String mouseButton = mouseButtonComboBox.getValue().toString();

        switch (mouseButton) {
            case "Right":
                return InputEvent.BUTTON3_MASK;
            case "Middle":
                return InputEvent.BUTTON2_MASK;
            default:
                return InputEvent.BUTTON1_MASK;
        }
    }

    //returns clickRepeatValue according to radio button
    private int getClickRepeatValue() {
        if (repeatUntilStoppedRadioButton.isSelected() || repeatTextField.getText().isEmpty()) {
            return 0;
        }

        return Integer.parseInt(repeatTextField.getText());
    }

    //Defines custom Click Position
    @FXML
    private void onPickLocationMouseRelease(MouseEvent event) {

        Long xScreen = Math.round(event.getScreenX());
        Long yScreen = Math.round(event.getScreenY());

        xTextField.setText(xScreen.toString());
        yTextField.setText(yScreen.toString());
    }

    //Help Window
    @FXML
    private void onHelpButtonAction() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("Free Software developed by Joaquim Ribeiro.\n"));
        dialogVbox.getChildren().add(new Text("To 'Pick Location', click the button, then release on desired location."));
        dialogVbox.getChildren().add(new Text("If 'Repeat' value is left blank and it is selected, it will click indefinitely."));
        Scene dialogScene = new Scene(dialogVbox, 400, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    //defines the character limit and numeric regex for the various textfields
    private void limitTextfields() {

        hoursTextField.setOnKeyTyped(a -> {
            int maxCharacters = 1;
            if (hoursTextField.getText().length() > maxCharacters || !StringUtils.isNumeric(a.getCharacter()))
                a.consume();
        });

        minutesTextField.setOnKeyTyped(a -> {
            int maxCharacters = 1;
            if (minutesTextField.getText().length() > maxCharacters || !StringUtils.isNumeric(a.getCharacter()))
                a.consume();
        });

        secondsTextField.setOnKeyTyped(a -> {
            int maxCharacters = 1;
            if (secondsTextField.getText().length() > maxCharacters || !StringUtils.isNumeric(a.getCharacter()))
                a.consume();
        });

        millisecondsTextField.setOnKeyTyped(a -> {
            int maxCharacters = 2;
            if (millisecondsTextField.getText().length() > maxCharacters || !StringUtils.isNumeric(a.getCharacter()))
                a.consume();
        });

        xTextField.setOnKeyTyped(a -> {
            int maxCharacters = 3;
            if (xTextField.getText().length() > maxCharacters || !StringUtils.isNumeric(a.getCharacter()))
                a.consume();
        });

        yTextField.setOnKeyTyped(a -> {
            int maxCharacters = 3;
            if (yTextField.getText().length() > maxCharacters || !StringUtils.isNumeric(a.getCharacter()))
                a.consume();
        });
    }

    @FXML
    private void onPlay() {
        initiateRobot();
    }

    @FXML
    public void onStop() {
        Robot.stopLooping();
    }
}
