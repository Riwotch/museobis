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

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ModifItemController implements Initializable {

    Items selectedItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextAreaDescription.setWrapText(true);
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

    @FXML
    private AnchorPane AnchorPaneModifyItem;

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
    private TextArea TextAreaDescription;

    @FXML
    private TextField TextFieldPrice;

    @FXML
    private ImageView ImageViewNewImage;

    @FXML
    private ChoiceBox<KeyValuePair> ChoiceBoxTag;

    @FXML
    private ChoiceBox<KeyValuePair> ChoiceBoxEra;


    @FXML
    void handleButtonSave(ActionEvent event) {
        updateItem(selectedItem.getId());
    }

    private File file;
    @FXML
    void handleButtonAdd(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("IMG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage)AnchorPaneModifyItem.getScene().getWindow();
        file  = fileChooser.showOpenDialog(stage);
        Image image = new Image(file.toURI().toString());
        ImageViewNewImage.setImage(image);
    }


    public void initItemsData(Items item) {
        this.selectedItem = item;
        String imgPath = "src/upload/"+ selectedItem.getId() +".jpg";
        TextFieldItemName.setText(selectedItem.getName());

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //DatePicker.setValue();
        TextFieldPrice.setText(String.valueOf(selectedItem.getPrice()));
        TextAreaDescription.setText(selectedItem.getDescription());
        DecodeEncodeBase64.decoder(selectedItem.getImage(), imgPath);
        File file = new File(imgPath);
        Image image = new Image(file.toURI().toString());
        ImageViewNewImage.setImage(image);
        TextFieldPrice.setText(String.valueOf(selectedItem.getPrice()));
        //ChoiceBoxEra.
        //ChoiceBoxTag.
        //ChoiceBoxSubState.
        //ChoiceBoxState.
    }

    public void updateItem(int id){

        try {
            Connection conn = getConnection();
            PreparedStatement reqst = conn.prepareStatement("UPDATE `item` SET `name`= ?, `description` = ?, `image` = ?, `modified_at` = ?, " +
                            "`is_active` = ?, `id_bibliography` = ?, `id_substate` = ?, `id_state` = ?, `id_tag` = ?, `price` = ?  WHERE id = ?");
            reqst.setString(1, TextFieldItemName.getText());
            reqst.setString(2, TextAreaDescription.getText());
            reqst.setString(3, DecodeEncodeBase64.encoder(file.getPath()));
            reqst.setDate(4, Date.valueOf(java.time.LocalDate.now()));
            reqst.setInt(5, 1);
            reqst.setInt(6, 55);
            reqst.setInt(7, ChoiceBoxSubState.getValue().getKey());
            reqst.setInt(8, ChoiceBoxState.getValue().getKey());
            reqst.setInt(9, ChoiceBoxTag.getValue().getKey());
            reqst.setFloat(10, Float.parseFloat(TextFieldPrice.getText()));
            reqst.setInt(11, id);
            reqst.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/museo", "root", "");
            return conn;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}
