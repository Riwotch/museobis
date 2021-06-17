package museo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tools.DecodeEncodeBase64;
import tools.DecodeEncodeBase64.*;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {

    private File file;

    public void addItem() {
        String res = null;
        PreparedStatement reqst = null;
        try {
            Connection conn = getConnection();
            reqst = conn.prepareStatement(
                    "INSERT INTO item (`name`, `description`, `image`, `acquired_at`, `modified_at`, " +
                            "`is_active`, `id_era`, `id_bibliography`, `id_substate`, `id_state`, `id_tag`) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            reqst.setString(1, TextFieldItemName.getText());
            reqst.setString(2, TextAreaDescription.getText());
            reqst.setString(3, DecodeEncodeBase64.encoder(file.getPath()));
            reqst.setDate(4, Date.valueOf(String.valueOf(DatePicker.getValue())));
            reqst.setDate(5, Date.valueOf(java.time.LocalDate.now()));
            reqst.setInt(6, 1);
            reqst.setInt(7, ChoiceBoxEra.getValue().getKey());
            reqst.setInt(8, 2);
            reqst.setInt(9, ChoiceBoxSubState.getValue().getKey());
            reqst.setInt(10, ChoiceBoxState.getValue().getKey());
            reqst.setInt(11, ChoiceBoxTag.getValue().getKey());
            reqst.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            try { if (reqst != null) reqst.close(); } catch (Exception e) { e.printStackTrace();};
        }
    }


    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/museotest", "root", "");
            return conn;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


    @FXML
    void handleButtonAdd(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("IMG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage)AnchorPaneAddItem.getScene().getWindow();
        file  = fileChooser.showOpenDialog(stage);
        Image image = new Image(file.toURI().toString());
        ImageViewNewImage.setImage(image);
    }

    @FXML
    void handleButtonSave(ActionEvent event) {
        addItem();
    }

    @FXML
    private AnchorPane AnchorPaneAddItem;

    @FXML
    private TextField TextFieldItemName;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private Button ButtonSave;

    @FXML
    private Button ButtonAdd;

    @FXML
    private ChoiceBox<KeyValuePair> ChoiceBoxState;

    @FXML
    private ChoiceBox<KeyValuePair> ChoiceBoxSubState;

    @FXML
    private ChoiceBox<KeyValuePair> ChoiceBoxTag;

    @FXML
    private ChoiceBox<KeyValuePair> ChoiceBoxEra;


    @FXML
    private TextArea TextAreaDescription;

    @FXML
    private TextField TextFieldPrice;

    @FXML
    private ImageView ImageViewNewImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connection conn = getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM state");
            while (rs.next()){
                ChoiceBoxState.getItems().add(new KeyValuePair(rs.getInt("id"), rs.getString("name")));
            }

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM substate");
            while (rs.next()){
                ChoiceBoxSubState.getItems().add(new KeyValuePair(rs.getInt("id"), rs.getString("name")));
            }

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM era");
            while (rs.next()){
                ChoiceBoxEra.getItems().add(new KeyValuePair(rs.getInt("id"), rs.getString("name")));
            }

            st = conn.createStatement();
            rs = st.executeQuery("SELECT * FROM tag");
            while (rs.next()){
                ChoiceBoxTag.getItems().add(new KeyValuePair(rs.getInt("id"), rs.getString("name")));
            }

        }catch (Exception e){

        }


    }
}