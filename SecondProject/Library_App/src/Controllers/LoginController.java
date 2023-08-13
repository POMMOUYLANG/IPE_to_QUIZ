package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    @FXML
    TextField userid;
    @FXML
    private PasswordField passid;
    private Stage stage;
    private Scene scene;

    public void switchtohome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void login(ActionEvent event) throws IOException, SQLException {
        String id = userid.getText();
        String pass = passid.getText();
        if (id.equals("") && pass.equals("")) {
            JOptionPane.showMessageDialog(null,"Empty insert");
        } else {

            pst = con.prepareStatement("SELECT * FROM `adminlist` WHERE `Email`=? and `ID`=?");
            pst.setString(1, id);
            pst.setString(2, pass);
            rs = pst.executeQuery();

            if(rs.next()){
                Parent root = FXMLLoader.load(getClass().getResource("AdminHomePage.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else {
                JOptionPane.showMessageDialog(null, "Incorrect userid or password");
            }
        }
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DbConnect.getConnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
