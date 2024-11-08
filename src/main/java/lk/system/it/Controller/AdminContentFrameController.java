package lk.system.it.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.system.it.Controller.LoginFrameController;
import lk.system.it.Dto.UserDTO;
import lk.system.it.Service.ServiceFactory;
import lk.system.it.Service.ServiceTypes;
import lk.system.it.Service.custom.UserService;
import lk.system.it.Util.*;
import lk.system.it.Util.Task.TimeTask;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

public class AdminContentFrameController {
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private ImageView imgDashboard;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private ImageView imgStuent;

    @FXML
    private JFXButton btnQrcode;

    @FXML
    private ImageView imgQr;

    @FXML
    private JFXButton btnattend;

    @FXML
    private ImageView imgAttend;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private ImageView imgLogout;

    @FXML
    private Label lblGreeting;

    @FXML
    private Label lblName;

    @FXML
    private Label lblTime;
    UserDTO u1 = LoginFrameController.u1;

    public UserService userService;


    public void initialize() throws IOException {
        setUserName();
        setWelcome();
        setTime();
        Parent load = FXMLLoader.load(getClass().getResource("/View/system/AdminDashboardcontent.fxml"));
        pane.getChildren().clear();
        pane.getChildren().add(load);
    }

    private void setUserName() {
        lblName.setText(u1.getDis_name());
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

    public void setTime() {

        TimeTask a = new TimeTask();
        a.valueProperty().addListener((e,b,c)->{
            lblTime.setText(c);
        });
        Thread t1 = new Thread(a);
        t1.setDaemon(true);
        t1.start();

    }

    public void showDahboardOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.AHOME,pane);
    }

    public void showStudentFrameOnAction(ActionEvent actionEvent) throws IOException {

        Navigation.navigate(Routes.STUDENT,pane);
    }

    public void showDetailFrameOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.DETAIL,pane);
    }

    public void showmarkattendanceOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ATTEND,pane);
    }

    public void logoutOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        this.userService = ServiceFactory.getInstance().getService(ServiceTypes.USER);
        UserDTO u2 = new UserDTO(u1.getUsername(),u1.getDis_name(),u1.getPassword(),"No", u1.getHint());
        boolean updateuser = userService.update(u2);
        if (updateuser){
            Navigation.navigate(Routes.SIGNIN, pane);
        }
    }

    public void btnDashboradOnMouseEnteredAction(MouseEvent mouseEvent) {
        Timeline t = new Timeline();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgDashboard);
        transition.setToX(-70);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
        btnDashboard.setContentDisplay(ContentDisplay.CENTER);
    }

    public void btndashboardOnMouseExitAction(MouseEvent mouseEvent) {
        btnDashboard.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgDashboard);
        transition.setToX(0);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
    }

    public void btnStudentOnMouseEnteredAction(MouseEvent mouseEvent) {
        Timeline t = new Timeline();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgStuent);
        transition.setToX(-60);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
        btnStudent.setContentDisplay(ContentDisplay.CENTER);
    }

    public void btnStudentOnMouseExitAction(MouseEvent mouseEvent) {
        btnStudent.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgStuent);
        transition.setToX(0);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
    }

    public void btnQrcodeOnMouseEnteredAction(MouseEvent mouseEvent) {
        Timeline t = new Timeline();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgQr);
        transition.setToX(-60);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
        btnQrcode.setContentDisplay(ContentDisplay.CENTER);
    }

    public void btnQrcodeOnMouseExitAction(MouseEvent mouseEvent) {
        btnQrcode.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgQr);
        transition.setToX(0);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
    }

    public void btnattendOnMouseEnteredAction(MouseEvent mouseEvent) {
        Timeline t = new Timeline();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgAttend);
        transition.setToX(-60);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
        btnattend.setContentDisplay(ContentDisplay.CENTER);
    }

    public void btnattendOnMouseExitAction(MouseEvent mouseEvent) {
        btnattend.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgAttend);
        transition.setToX(0);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
    }

    public void btnLogoutOnMouseEnteredAction(MouseEvent mouseEvent) {
        Timeline t = new Timeline();
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgLogout);
        transition.setToX(-60);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
        btnLogout.setContentDisplay(ContentDisplay.CENTER);
    }

    public void btnLogoutOnMouseExitAction(MouseEvent mouseEvent) {
        btnLogout.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(imgLogout);
        transition.setToX(0);
        transition.setDuration(Duration.seconds(0.5));
        transition.play();
    }
}
