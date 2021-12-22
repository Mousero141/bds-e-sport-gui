package org.but.feec.esport;
import javafx.scene.image.Image;
import org.but.feec.esport.exceptions.ExceptionHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

        private FXMLLoader loader;
        private VBox mainStage;

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) {
            try {
                loader = new FXMLLoader(getClass().getResource("/main/resources/org.but.feec.esport/Login.fxml"));
                mainStage = loader.load();

                primaryStage.setTitle("BDS E-Sport");
                Scene scene = new Scene(mainStage);
                setUserAgentStylesheet(STYLESHEET_MODENA);
                String myStyle = getClass().getResource("css/myStyle.css").toExternalForm();
                scene.getStylesheets().add(myStyle);

                primaryStage.getIcons().add(new Image(App.class.getResourceAsStream("logos/logo.png")));

                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (Exception ex) {
                ExceptionHandler.handleException(ex);
            }
        }

    }

