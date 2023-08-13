package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class AdminHomePage {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchtohome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AdminHomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoaboutus(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AdminAboutUS.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtolistbook(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ListBook.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoadmin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Adminpage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtohomep(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
//    public void switchtoAdminList(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("AdminList.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

}
