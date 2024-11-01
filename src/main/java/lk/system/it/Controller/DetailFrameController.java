package lk.system.it.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.system.it.Dtm.AttendaceDtm;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Service.ServiceFactory;
import lk.system.it.Service.ServiceTypes;
import lk.system.it.Service.custom.StudentService;
import lk.system.it.Service.custom.Student_CourseService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

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

    public void initialize() throws IOException {
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        this.studentService = ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);
        this.studentCourseService = ServiceFactory.getInstance().getService(ServiceTypes.Student_Course);
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

    }

    @FXML
    void searchStudntOnAction(ActionEvent event) throws SQLException {
        String selectedtype = selectedtype();
        if (selectedtype == null){
            new Alert(Alert.AlertType.WARNING,"Please select the type frist").show();
        }else{
            String search = txtSearch.getText();
            System.out.println(search);
            StudentDto studentDto = studentService.searchStudent(search, selectedtype);
            System.out.println(studentDto);
            Student_CourseDto studentCourse = studentCourseService.findStudent_Course(studentDto.getStudent_id());


            if (studentDto == null){
                new Alert(Alert.AlertType.WARNING,"this type Student not founded!").show();
            }else {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
                fillData(studentDto,studentCourse);
            }
        }

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

    }

    @FXML
    void uploadimageOnAction(ActionEvent event) {

    }

}
