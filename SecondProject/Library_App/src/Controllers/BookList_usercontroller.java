package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.print.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookList_usercontroller implements Initializable  {

    @FXML
    private TableView<BookList> Booktable;

    @FXML
    private TableColumn<BookList, String> colauthor;

    @FXML
    private TableColumn<BookList, String> colid;

    @FXML
    private TableColumn<BookList, String> colpage;

    @FXML
    private TableColumn<BookList, String> colpub;

    @FXML
    private TableColumn<BookList, String> coldes;

    @FXML
    private TableColumn<BookList, String> colname;

    @FXML
    private TextField txtSeachBook;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<BookList> bookdata;

    private void setCellTable(){
        colid.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colname.setCellValueFactory(new PropertyValueFactory<>("Name"));
        coldes.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colauthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        colpage.setCellValueFactory(new PropertyValueFactory<>("Page"));
        colpub.setCellValueFactory(new PropertyValueFactory<>("Public"));
    }
    private void LoadData(){
        try {
            pst = con.prepareStatement("SELECT * FROM `booklist`");
            rs = pst.executeQuery();
            while (rs.next()){
                bookdata.add(new BookList(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Booktable.setItems(bookdata);
    }
    private void cleartextbookinfo(){
        Booktable.getItems().clear();
    }
    private void Refresh(){
        cleartextbookinfo();
        setCellTable();
        LoadData();
    }
    public void refresh(ActionEvent event){
        Refresh();
    }
    private void Searchbook(){
        txtSeachBook.setOnKeyReleased(e-> {
            if (txtSeachBook.getText().equals("")) Refresh();
            else {
                cleartextbookinfo();
                String sql = "SELECT * FROM `booklist` WHERE `ID`=?"
                        + "UNION SELECT * FROM `booklist` WHERE `Name`=?";
                String Sid = txtSeachBook.getText();
                String Sname = txtSeachBook.getText();
                try {
                    pst = con.prepareStatement(sql);
                    pst.setString(1, Sid);
                    pst.setString(2, Sname);
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        bookdata.add(new BookList(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6)));
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

        });
    }

    public void switchtohome(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DbConnect.getConnect();
            bookdata = FXCollections.observableArrayList();
            setCellTable();
            LoadData();
            Searchbook();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
