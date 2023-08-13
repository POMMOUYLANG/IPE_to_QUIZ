package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminListController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private ActionEvent event;


    @FXML
    private TableView<AdminTableList> AdminTable;


    @FXML
    private TableColumn<AdminTableList, String> colName;

    @FXML
    private TableColumn<AdminTableList, String> colEmail;

    @FXML
    private TableColumn<AdminTableList, String> colID;

    @FXML
    private TableColumn<AdminTableList, String> colDepartment;

    @FXML
    private TableColumn<AdminTableList, String> colDuties;

    @FXML
    private javafx.scene.control.TextField name;

    @FXML
    private javafx.scene.control.TextField email;

    @FXML
    private javafx.scene.control.TextField id;

    @FXML
    private javafx.scene.control.TextField dep;

    @FXML
    private javafx.scene.control.TextField dt;







    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<AdminTableList> AdminListData;





    private void setAdminTable(){
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("Department"));
        colDuties.setCellValueFactory(new PropertyValueFactory<>("Duties"));

    }
    private void LoadAdminListData(){
        try {
            pst = con.prepareStatement("SELECT * FROM `adminlist`");
            rs = pst.executeQuery();
            while (rs.next()){
               AdminListData.add(new AdminTableList(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)));


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        AdminTable.setItems(AdminListData);
    }
    private void cleartextstudentinfo(){
        AdminTable.getItems().clear();
    }
    private void Refresh(){
        cleartextstudentinfo();
        setAdminTable();
        LoadAdminListData();
    }



    @FXML
    void adminInsert(ActionEvent event) throws SQLException {

        String sql = "INSERT INTO `adminlist`(`Name`, `Email`, `ID`, `Department`, `Duties`) VALUES (?,?,?,?,?)";
        String Name = name.getText();
        String Email = email.getText();
        String ID= id.getText();
        String Department= dep.getText();
        String Duties = dt.getText();

        try {
            pst=con.prepareStatement(sql);
            pst.setString(1, Name);
            pst.setString(2, Email);
            pst.setString(3, ID);
            pst.setString(4, Department);
            pst.setString(5, Duties);


            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Data inserted successfully");
                Refresh();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            pst.close();
        }
    }


    @FXML
    void adminRemove(ActionEvent event) throws SQLException {
        String sql = "DELETE FROM `adminlist` WHERE `ID`=?";
        pst = con.prepareStatement(sql);
        String DelID = id.getText();

        try {
            pst.setString(1, DelID);
            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Deleted successfully");
                Refresh();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void adminUpdate(ActionEvent event) {
        String sql = "UPDATE `adminlist` SET `Name`=?,`Email`=?,`Department`=?,`Duties`=? WHERE `ID`=?";
        try {
            pst = con.prepareStatement(sql);
            String Upname = name.getText();
            String Upemail = email.getText();
            String Upid = id.getText();
            String Updep = dep.getText();
            String Updt = dt.getText();

            pst.setString(1, Upname);
            pst.setString(2, Upemail);
            pst.setString(3, Updep);
            pst.setString(4, Updt);
            pst.setString(5, Upid);


            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Data updated successfully");
                Refresh();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void refresh(ActionEvent event) {
        Refresh();
    }


    public void switchtoadmin(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Adminpage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void setcellvaluetableclick(){
        AdminTable.setOnMouseClicked(mouseEvent -> {
            AdminTableList ATL = AdminTable.getItems().get(AdminTable.getSelectionModel().getSelectedIndex());
            name.setText(ATL.getName());
            email.setText(ATL.getEmail());
            id.setText(ATL.getID());
           dep.setText(ATL.getDepartment());
           dt.setText(ATL.getDuties());


        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DbConnect.getConnect();
            AdminListData= FXCollections.observableArrayList();
            setAdminTable();
            LoadAdminListData();
            setcellvaluetableclick();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
