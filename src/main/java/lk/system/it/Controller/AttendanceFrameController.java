package lk.system.it.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.system.it.Dtm.MarkDtm;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Service.ServiceFactory;
import lk.system.it.Service.ServiceTypes;
import lk.system.it.Service.custom.AttendanceService;
import lk.system.it.Service.custom.StudentService;
import lk.system.it.Service.custom.Student_CourseService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AttendanceFrameController {

    public JFXButton btnRefresh;
    @FXML
    private AnchorPane frame;

    @FXML
    private JFXTextField txtStudid;

    @FXML
    private JFXTextField txtstudename;

    @FXML
    private JFXTextField txtcontactno;

    @FXML
    private JFXTextField txtschool;

    @FXML
    private JFXTextField txtcourse;

    @FXML
    private Label lbltitle;

    @FXML
    private JFXButton btnupload1;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<MarkDtm> tblAttend;

    @FXML
    private TableColumn<MarkDtm, String> colId;

    @FXML
    private TableColumn<MarkDtm, String> ColName;

    @FXML
    private TableColumn<MarkDtm, String> ColStatus;

    @FXML
    private JFXButton btnMark;

    @FXML
    private JFXButton btnLoad;

    @FXML
    private JFXComboBox<String> cmbcities;


    public AttendanceService attendanceService;
    public StudentService studentService;
    public Student_CourseService studentCourseService;
    public MarkDtm markDtm;
    public void initialize() throws IOException {
        colId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadCities();
        this.attendanceService = ServiceFactory.getInstance().getService(ServiceTypes.ATTEND);
        this.studentService = ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);
        this.studentCourseService = ServiceFactory.getInstance().getService(ServiceTypes.Student_Course);
        btnMark.setDisable(true);
    }
    private void loadCities() {
        String[] type = {"Panadura","Bandaragama"};
        ObservableList<String> list = FXCollections.observableArrayList(type);
        cmbcities.setItems(list);
    }
    @FXML
    void HomePageOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void searchStudntOnAction(ActionEvent event) throws SQLException {
        String search = txtSearch.getText();
        MarkDtm dtm = attendanceService.getstudentattend(search, LocalDate.now());
        List<MarkDtm> collect = new ArrayList<>();
        if (dtm == null) {
            new Alert(Alert.AlertType.WARNING, "This student was not found!").show();
        } else {
            collect.add(new MarkDtm(dtm.getStudent_id(), dtm.getName(), dtm.getStatus()));
            tblAttend.setItems(FXCollections.observableArrayList(collect));
        }
    }

    @FXML
    void MarkPresentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtStudid.getText();
        boolean b = attendanceService.markPresent(id);
        if (b){
            new Alert(Alert.AlertType.CONFIRMATION,"Mark Present").show();
            clearAll();
            loadAllAttend();
        }else {
            new Alert(Alert.AlertType.WARNING,"cannot mark present..").show();
        }
    }

    private void clearAll() {
        txtStudid.clear();
        txtstudename.clear();
        txtcontactno.clear();
        txtschool.clear();
        txtcourse.clear();
    }

    @FXML
    void loadAllStudentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String city = cmbcities.getSelectionModel().getSelectedItem();
        System.out.println(city);
        boolean b1 = attendanceService.searchIsAdded(LocalDate.now());
        System.out.println(b1);
        if (b1){
            loadAllAttend();
        }else {
            ArrayList<Student_CourseDto> studentByCity = studentCourseService.findStudentByCity(city);
            System.out.println(studentByCity);
            attendanceService.markAllAbsent(studentByCity);
            loadAllAttend();
        }

    }

    private void loadAllAttend() throws SQLException{
        ArrayList<MarkDtm> allAttendance = attendanceService.getAllAttendance(LocalDate.now());
        List<MarkDtm> searchResult = allAttendance.stream().map(markDtm ->
                new MarkDtm(markDtm.getStudent_id(), markDtm.getName(), markDtm.getStatus())).collect(Collectors.toList());
        tblAttend.setItems(FXCollections.observableArrayList(searchResult));
    }

    @FXML
    void studentDetailOnMouseClicked(MouseEvent event) throws SQLException {
        markDtm = tblAttend.getSelectionModel().getSelectedItem();
        StudentDto studentDto = studentService.findByPk(markDtm.getStudent_id());
        Student_CourseDto studentCourse = studentCourseService.findStudent_Course(studentDto.getStudent_id());
        txtStudid.setText(studentDto.getStudent_id());
        txtstudename.setText(studentDto.getStudent_name());
        txtcourse.setText(studentCourse.getCourse_id());
        txtcontactno.setText(studentDto.getContact_number());
        txtschool.setText(studentDto.getSchool());
        btnMark.setDisable(false);
    }

    @FXML
    void RefreshOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        loadAllAttend();
    }

}
