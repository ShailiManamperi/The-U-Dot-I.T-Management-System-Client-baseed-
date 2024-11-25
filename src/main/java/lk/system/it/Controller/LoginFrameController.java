package lk.system.it.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
//import lk.ijse.shaili.system.Dto.UserDTO;
//import lk.ijse.shaili.system.Service.ServiceFactory;
//import lk.ijse.shaili.system.Service.ServiceTypes;
//import lk.ijse.shaili.system.Service.custom.UserService;
//import lk.ijse.shaili.system.Util.Navigation;
//import lk.ijse.shaili.system.Util.Routes;
import lk.system.it.Dto.UserDTO;
import lk.system.it.Service.ServiceFactory;
import lk.system.it.Service.ServiceTypes;
import lk.system.it.Service.custom.UserService;
import lk.system.it.Util.Navigation;
import lk.system.it.Util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

public class LoginFrameController {

//
//
//    public void LoginUserOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
////        Navigation.navigate(Routes.ADMIN,pane);

//    }
//
//    public void getPasswordHintOnMoueClicked(MouseEvent mouseEvent) {
//        String userName = txtInUsername.getText();
//        try {
//            UserDTO search = userService.search(userName);
//            lblHint.setText(search.getHint());
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void SHowPasswordOnMouseClicked(MouseEvent mouseEvent) {

//    }
//
//    public void HidePasswordOnMOuseClicked(MouseEvent mouseEvent) {

//    }
//
//    public void RegisterUserOnAction(ActionEvent actionEvent) {

//    }
//
//    public void ShowRegisterFormOnAction(ActionEvent actionEvent) {
//        TranslateTransition slide = new TranslateTransition();
//        slide.setDuration(Duration.seconds(1.5));
//        slide.setNode(slider);
//
//        slide.setToX(-480);
//        slide.play();
//        lblwelcome.setVisible(true);
//        lblLogin.setVisible(true);
//        btnlogin.setVisible(true);
//        lblGreeting.setVisible(false);
//        lblCreate.setVisible(false);
//        btncreate.setVisible(false);
//    }
//
//    public void ShowLoginFormOnAction(ActionEvent actionEvent) {
//        TranslateTransition slide = new TranslateTransition();
//        slide.setDuration(Duration.seconds(1.5));
//        slide.setNode(slider);
//
//        slide.setToX(0);
//        slide.play();
//        lblwelcome.setVisible(false);
//        lblLogin.setVisible(false);
//        btnlogin.setVisible(false);
//        lblGreeting.setVisible(true);
//        lblCreate.setVisible(true);
//        btncreate.setVisible(true);
//    }

    @FXML
    private AnchorPane pane;

    @FXML
    private AnchorPane Signupframe;

    @FXML
    private JFXTextField txtUpUsername;

    @FXML
    private JFXTextField txtDisplayname;

    @FXML
    private JFXPasswordField txtUpPassword;

    @FXML
    private JFXTextField txtPsHint;

    @FXML
    private JFXButton btncreate;

    @FXML
    private Text txtLogin;

    @FXML
    private AnchorPane SigninFrame;

    @FXML
    private JFXButton btnlogin;

    @FXML
    private Text txtCreate;

    @FXML
    private JFXTextField txtInUsername;

    @FXML
    private JFXTextField txtShowPassword;

    @FXML
    private JFXPasswordField txtInPassword;

    @FXML
    private ImageView imgHideEye;

    @FXML
    private ImageView imgShowPassword;

    @FXML
    private Label lblGreeting;
    private UserService userService;

    public static UserDTO u1;

    public void initialize(){
        this.userService = ServiceFactory.getInstance().getService(ServiceTypes.USER);
        setWelcome();
        imgHideEye.setVisible(false);
        txtShowPassword.setVisible(false);
        SigninFrame.setVisible(true);
        Signupframe.setVisible(false);
//
    }
    public void setWelcome() {
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            if (currentTime.getHour() > 6 && currentTime.getHour() < 12) {
                lblGreeting.setText("Good Morning ");
            } else if (currentTime.getHour() >= 12 && currentTime.getHour() < 16) {
                lblGreeting.setText("Good AfterNoon");
            } else if (currentTime.getHour() >= 16 && currentTime.getHour() < 19) {
                lblGreeting.setText("Good Evening");
            } else {
                lblGreeting.setText("Good Night");
            }
        }), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    @FXML
    void HidePasswordOnMOuseClicked(MouseEvent event) {
        String password = txtShowPassword.getText();
        txtInPassword.setVisible(true);
        txtShowPassword.setVisible(false);
        txtInPassword.setText(password);
        imgShowPassword.setVisible(true);
        imgHideEye.setVisible(false);

    }

    @FXML
    void LoginUserOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String uname = txtInUsername.getText();
        String password = txtInPassword.getText();
        UserDTO search = userService.search(uname);

        String ps = search.getPassword();
        String verification = search.getVerification();
        if (password.equals(ps)){
            if(verification.equals("No")) {
                u1 = new UserDTO(search.getUsername(), search.getDis_name(), search.getPassword(),"Yes", search.getHint());
                boolean save = userService.update(u1);
                if(save){
                    Navigation.navigate(Routes.ADMIN, pane);
                }
            }else{
                new Alert(Alert.AlertType.WARNING, " This user already login!").show();
            }
        }else {
            txtInPassword.setUnFocusColor(Paint.valueOf("#FF0000"));
            new Alert(Alert.AlertType.ERROR,"Enter Password is wrong! please re-enter it").show();
        }


    }

    @FXML
    void RegisterUserOnAction(ActionEvent event) {
        String ps = txtUpPassword.getText();
        String uname = txtUpUsername.getText();
        String hint = txtPsHint.getText();
        String dis_name = txtDisplayname.getText();
        String verification = "No";
        if (ps.length() > 8){
            UserDTO u1 = new UserDTO(uname,dis_name,ps,verification,hint);
            try {
                boolean save = userService.save(u1);

                if (save) {
                    new Alert(Alert.AlertType.CONFIRMATION, "New user Added!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something happened!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING,"Duplicate entry!").show();
                throw new RuntimeException(e);
            }
        }else{
            txtUpPassword.setFocusColor(Paint.valueOf("Red"));
            txtUpPassword.requestFocus();

        }
    }

    @FXML
    void SHowPasswordOnMouseClicked(MouseEvent event) {
        String password = txtInPassword.getText();
        txtShowPassword.setVisible(true);
        txtInPassword.setVisible(false);
        txtShowPassword.setText(password);
        imgHideEye.setVisible(true);
        imgShowPassword.setVisible(false);
    }

    @FXML
    void showCreatePaneOnAction(MouseEvent event) {
        SigninFrame.setVisible(false);
        Signupframe.setVisible(true);
    }

    @FXML
    void showLoginPaneOnAction(MouseEvent event) {
        SigninFrame.setVisible(true);
        Signupframe.setVisible(false);
    }
}

