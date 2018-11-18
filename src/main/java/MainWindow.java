import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class MainWindow extends Application {

    private final static Logger LOG = Logger.getLogger(MainWindow.class);

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("Idle Automation");
        primaryStage.setScene(new Scene(root, 450, 420));
        LOG.debug("Application initiated.");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}