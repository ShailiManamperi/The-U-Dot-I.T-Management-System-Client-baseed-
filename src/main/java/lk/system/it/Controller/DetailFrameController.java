package lk.system.it.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.system.it.Dtm.AttendaceDtm;
import lk.system.it.Dtm.MarkDtm;
import lk.system.it.Dto.AttendanceDto;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Service.ServiceFactory;
import lk.system.it.Service.ServiceTypes;
import lk.system.it.Service.custom.AttendanceService;
import lk.system.it.Service.custom.StudentService;
import lk.system.it.Service.custom.Student_CourseService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DetailFrameController {

    public JFXTextField txtStatus;
    public JFXTextField txtcity;
    public TableView<AttendaceDtm> tblAttend;
    public TableColumn<AttendaceDtm, Date> colDate;
    public TableColumn<AttendaceDtm,String> colStatus;
    @FXML
    private AnchorPane frame;

    @FXML
    private JFXTextField txtStudid;

    @FXML
    private JFXTextField txtstudename;

    @FXML
    private JFXTextField txtcontactno;

    @FXML
    private JFXTextField txtaddress;

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
    private JFXRadioButton rbId;

    @FXML
    private JFXRadioButton rbName;

    @FXML
    private JFXRadioButton rbContact;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnDelete;

    public StudentService studentService;
    public Student_CourseService studentCourseService;
    public AttendanceService attendanceService;

    public Student_CourseDto studentCourse;
    public StudentDto studentDto;

    public void initialize() throws IOException {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        this.studentService = ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);
        this.studentCourseService = ServiceFactory.getInstance().getService(ServiceTypes.Student_Course);
        this.attendanceService = ServiceFactory.getInstance().getService(ServiceTypes.ATTEND);
    }

    private String selectedtype() {
        String type = null;
        if (rbId.isSelected()){
            type ="student_id";
        }if (rbName.isSelected()){
            type = "student_name";
        }if (rbContact.isSelected()){
            type = "contact_number";
        }
        return type;
    }


    @FXML
    void HomePageOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void deletestudentOnAction(ActionEvent event) {
        String id = txtStudid.getText();
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure to delete this student ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean deleteStudent = studentService.deleteStudent(id);
            if (deleteStudent) {
                tblAttend.getItems().removeAll(tblAttend.getSelectionModel().getSelectedItem());
                new Alert(Alert.AlertType.INFORMATION, "Item delete successful").show();
                clearData();
            }
        }

    }

    private void clearData() {
        txtStudid.clear();
        txtstudename.clear();
        txtaddress.clear();
        txtcontactno.clear();
        txtcity.clear();
        txtcourse.clear();
        txtStatus.clear();
    }


    @FXML
    void searchStudntOnAction(ActionEvent event) throws SQLException {
        String selectedtype = selectedtype();
        if (selectedtype == null){
            new Alert(Alert.AlertType.WARNING,"Please select the type frist").show();
        }else{
            String search = txtSearch.getText();
            System.out.println(search);
            studentDto= studentService.searchStudent(search, selectedtype);
            System.out.println(studentDto);
            studentCourse = studentCourseService.findStudent_Course(studentDto.getStudent_id());
            ArrayList<AttendanceDto> statusById = attendanceService.getStatusById(studentCourse.getStudent_id());
            if (studentDto == null){
                new Alert(Alert.AlertType.WARNING,"this type Student not founded!").show();
            }else {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                fillData(studentDto,studentCourse);
                fillTable(statusById);
            }
        }

    }

    private void fillTable(ArrayList<AttendanceDto> statusById) {
        List<AttendaceDtm> collect = statusById.stream().map(attendanceDtm ->
                new AttendaceDtm(attendanceDtm.getDate(), attendanceDtm.getStatus())).collect(Collectors.toList());
        tblAttend.setItems(FXCollections.observableArrayList(collect));
    }

    private void fillData(StudentDto studentDto,Student_CourseDto studentCourseDto) {
        txtStudid.setText(studentDto.getStudent_id());
        txtstudename.setText(studentDto.getStudent_name());
        txtaddress.setText(studentDto.getAddress());
        txtschool.setText(studentDto.getSchool());
        txtcontactno.setText(studentDto.getContact_number());
        txtcourse.setText(studentCourseDto.getCourse_id());
        txtStatus.setText(studentCourseDto.getStatus());
        txtcity.setText(studentCourseDto.getCity());
    }


    @FXML
    void updatestudentOnAction(ActionEvent event) {
        StudentDto studentDto1 = makeObject();
        StudentDto studentDto2 = studentService.updateStudent(studentDto1);
        if (studentDto2 == null){
            new Alert(Alert.AlertType.ERROR,"This Student is Not Update!", ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.CONFIRMATION,"This studnet Detail is Update Sucessfully!",ButtonType.CLOSE).show();
        }

    }
    private StudentDto makeObject() {
        StudentDto s1 = new StudentDto(
                txtStudid.getText(),
                txtstudename.getText(),
                txtcontactno.getText(),
                txtaddress.getText(),
                txtschool.getText(),
                studentDto.getPhoto()

        );
        return s1;
    }

}
