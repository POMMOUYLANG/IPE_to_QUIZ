package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Scene Scene1= new Scene(root);
        stage.setTitle("Welcome to Library App");
        stage.setScene(Scene1);
        stage.show();
    } 
    public static void main(String[] args) throws Exception {
        launch(args);
        try {
            //code that may throw an exception
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    private static Stage stg;
    public void changescene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);

    }
}
