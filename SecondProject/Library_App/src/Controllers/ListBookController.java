package Controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import java.io.IOException;

import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
public class ListBookController implements Initializable {

    @FXML
    private TableView<BookList> Booktable;

    @FXML
    private TableColumn<BookList, String> authorcol;

    @FXML
    private TableColumn<BookList, String> descol;

    @FXML
    private TableColumn<BookList, String> idcol;

    @FXML
    private TableColumn<BookList, String> namecol;

    @FXML
    private TableColumn<BookList, String> pagecol;

    @FXML
    private TableColumn<BookList, String> pubcol;
    @FXML
    private TextField txtSeachBook;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtDes;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPage;

    @FXML
    private TextField txtPub;
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<BookList> bookdata;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con = DbConnect.getConnect();
            bookdata = FXCollections.observableArrayList();
            setCellTable();
            LoadData();
            setcellvaluetableclick();
            Searchbook();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void adddata(ActionEvent event) throws SQLException {
        String sql = "INSERT INTO `booklist`(`ID`, `Name`, `Description`, `Author`, `Page`, `Public`) VALUES (?,?,?,?,?,?)";
        String id = txtID.getText();
        String name = txtName.getText();
        String description = txtDes.getText();
        String author = txtAuthor.getText();
        String page = txtPage.getText();
        String pub =txtPub.getText();

        try {
            pst=con.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, name);
            pst.setString(3, description);
            pst.setString(4, author);
            pst.setString(5, page);
            pst.setString(6, pub);

            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Data inserted succesfully");
                Refresh();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            pst.close();
        }
    }
    public void updatebook(ActionEvent event){
        String sql = "UPDATE `booklist` SET `Name`=?,`Description`=?,`Author`=?,`Page`=?,`Public`=? WHERE `ID`=?";
        try {
            pst = con.prepareStatement(sql);
            String UpID = txtID.getText();
            String UpName = txtName.getText();
            String UpDes = txtDes.getText();
            String UpAuthor = txtAuthor.getText();
            String UpPage = txtPage.getText();
            String UpPub = txtPub.getText();
            pst.setString(6, UpID);
            pst.setString(1, UpName);
            pst.setString(2, UpDes);
            pst.setString(3, UpAuthor);
            pst.setString(4, UpPage);
            pst.setString(5, UpPub);

            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Data updated succesfully");
                Refresh();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void deletebook(ActionEvent event) throws SQLException {
        String sql = "DELETE FROM `booklist` WHERE `ID`=?";
        pst = con.prepareStatement(sql);
        String DelID = txtID.getText();

        try {
            pst.setString(1, DelID);
            int i = pst.executeUpdate();
            if(i == 1){
                System.out.println("Data Deleted succesfully");
                Refresh();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellTable(){
        idcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        descol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        authorcol.setCellValueFactory(new PropertyValueFactory<>("Author"));
        pagecol.setCellValueFactory(new PropertyValueFactory<>("Page"));
        pubcol.setCellValueFactory(new PropertyValueFactory<>("Public"));
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
    private void setcellvaluetableclick(){
        Booktable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                BookList bl = Booktable.getItems().get(Booktable.getSelectionModel().getSelectedIndex());
                txtID.setText(bl.getID());
                txtName.setText(bl.getName());
                txtDes.setText(bl.getDescription());
                txtAuthor.setText(bl.getAuthor());
                txtPage.setText(bl.getPage());
                txtPub.setText(bl.getPublic());

            }
        });
    }

    public void switchtohome(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("AdminHomePage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
