package lk.system.it.Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;
    private static AnchorPane subpane;

    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {
            case STUDENT:
                window.setTitle("Student Management");
                initUI("StudentFrame.fxml");
                break;
            case ADMIN:
                window.setTitle("Admin Dashboard");
                init("AdminContentFrame.fxml");
                window.centerOnScreen();
                break;
            case SIGNIN:
                window.setTitle("Login form");
                init("LoginFrame.fxml");
                break;
            case ATTEND:
                window.setTitle("Attendace form");
                initUI("AttendanceFrame.fxml");
                break;
            case DETAIL:
                window.setTitle("Student Detail Management");
                initUI("DetailsFrame.fxml");
                break;
        }
    }

    public static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/View/system/" + location)));
    }

    public static void init(String location) throws IOException {
        Stage window = (Stage) pane.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(Navigation.class.getResource("/View/system/" + location))));
    }

}
