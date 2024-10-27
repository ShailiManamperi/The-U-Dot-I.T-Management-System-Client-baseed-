package lk.system.it.Controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Service.ServiceFactory;
import lk.system.it.Service.ServiceTypes;
import lk.system.it.Service.custom.AddStudentService;
import lk.system.it.Service.custom.CourseService;
import lk.system.it.Service.custom.StudentService;
import lk.system.it.Service.custom.Student_CourseService;
import org.bouncycastle.asn1.dvcs.ServiceType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class StudentFrameController {

    public ImageView imgQr;
    public JFXButton btngenerate;
    public ImageView imgNew;
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
    private JFXComboBox<String> cmbcourse;
    @FXML
    private ImageView imgStud;

    private byte[] imageBytes; // To store image data
    private byte[] QrBytes; // To store Qr data

    public CourseService courseService;
    public StudentService studentService;
    public Student_CourseService studentCourseService;
    public AddStudentService addStudentService;

    public void initialize() throws SQLException, ClassNotFoundException {
        this.courseService = ServiceFactory.getInstance().getService(ServiceTypes.COURSE);
        this.studentService = ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);
        this.studentCourseService = ServiceFactory.getInstance().getService(ServiceTypes.Student_Course);
        this.addStudentService = ServiceFactory.getInstance().getService(ServiceTypes.ADDSTUDENT);
        loadCourseId();
    }

    private void loadCourseId() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> courseIdIdList = courseService.loadCourseId();
            for (String id : courseIdIdList) {
                observableList.add(id);
            }
            cmbcourse.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private

    @FXML
    void addstudentOnAction(ActionEvent event) {
        StudentDto studentDto = makeStudentObject();
        Student_CourseDto studentCourseDto = makeStu_CourObject();
        try {
            boolean b = addStudentService.AddStudent(studentDto, studentCourseDto);
            if (b){
                clearAll();
                new Alert(Alert.AlertType.CONFIRMATION,"Student added sucessfully", ButtonType.CLOSE).show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong.", ButtonType.CLOSE).show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearAll(){
        txtStudid.clear();
        txtstudename.clear();
        txtcontactno.clear();
        txtaddress.clear();
        txtschool.clear();
        cmbcourse.getSelectionModel().clearSelection();
        imgStud.setImage(null);
        imageBytes = null;
    }

    private StudentDto makeStudentObject(){
        String id = txtStudid.getText();
        String name = txtstudename.getText();
        String contact = txtcontactno.getText();
        String address = txtaddress.getText();
        String school = txtschool.getText();
        return new StudentDto(id,name,contact,address,school,imageBytes);
    }

    private Student_CourseDto makeStu_CourObject(){
        String stud_id = txtStudid.getText();
        String cour_id = cmbcourse.getSelectionModel().getSelectedItem().toString();
        String status = "Not Done";
        return new Student_CourseDto(stud_id,cour_id,status,QrBytes);
    }
//    private String selectedtype() {
//        String type = null;
//        if (rdbid.isSelected()){
//            type ="student_id";
//        }if (rdbname.isSelected()){
//            type = "student_name";
//        }if (rbcontact.isSelected()){
//            type = "contact_number";
//        }
//        return type;
//    }
//    @FXML
//    void searchOnAction(ActionEvent event) {
//        String selectedtype = selectedtype();
//        if (selectedtype == null){
//            new Alert(Alert.AlertType.WARNING,"Please select the type frist").show();
//        }else{
//            String search = txtSearch.getText();
//            System.out.println(search);
//
//            selectedUser = employeeService.searchEmployee(search,selectedtype);
//
//            System.out.println(selectedUser);
//            if (selectedUser == null){
//                new Alert(Alert.AlertType.WARNING,"this type employee not founded!").show();
//            }else {
//                String type = selectedUser.getJob();
//                salaryDTO = attendanceService.searchSalaryType(type);
//                System.out.println(selectedUser.getEid());
//                btnUpdate.setDisable(false);
//                btnDelete.setDisable(false);
//                fillData();
//            }
//        }
//    }

    @FXML
    void newStudentidOnAction(ActionEvent event) {
        try {
            String studId = studentService.generateNewItemId();
            txtStudid.setText(studId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Cannot Generate a Item id.", ButtonType.CLOSE).show();
        }
    }

    @FXML
    void uploadimageOnAction(ActionEvent event) {
        // Open a file chooser to select an image file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Show open dialog
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                // Read image file as byte array
                imageBytes = getBytesFromFile(file);
                // Convert byte array to JavaFX image and display
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                imgStud.setImage(image);

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Error loading image.");
                alert.show();
            }
        }
    }

    // Helper method to read a file as a byte array
    private byte[] getBytesFromFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }

    public void HomePageOnMouseClicked(MouseEvent mouseEvent) {
    }

    @FXML
    void generateQrOnAction(ActionEvent event) {
        try {
            // Step 1: Gather student data for QR code
            String studentId = txtStudid.getText();
            String courseId = cmbcourse.getSelectionModel().getSelectedItem();
            String contactNumber = txtcontactno.getText();

            if (studentId.isEmpty() || courseId == null || contactNumber.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill all fields to generate the QR code.").show();
                return;
            }

            // Combine the data into a single string
            String qrData = "Student ID: " + studentId + "\nCourse ID: " + courseId + "\nContact Number: " + contactNumber;

            // Step 2: Set QR code properties
            int width = 250;
            int height = 250;
            HashMap<EncodeHintType, Object> qrParams = new HashMap<>();
            qrParams.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            qrParams.put(EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.L);

            // Step 3: Generate the QR code matrix
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix matrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, width, height, qrParams);

            // Step 4: Convert matrix to buffered image
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);

            // Step 5: Convert buffered image to JavaFX Image and set to imgQr
            Image qrImage = SwingFXUtils.toFXImage(bufferedImage, null);
            imgQr.setImage(qrImage);

            // Optionally: Save QR code image as bytes for future use
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            javax.imageio.ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            QrBytes = byteArrayOutputStream.toByteArray();
            // Save QR image to device
            saveQrCodeImage(bufferedImage, studentId); // Save with studentId as file name

            new Alert(Alert.AlertType.INFORMATION, "QR Code generated successfully!").show();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error generating QR code: " + e.getMessage()).show();
        }
    }

    private void saveQrCodeImage(BufferedImage bufferedImage, String studentId) {
        try {
            // Specify the file path and name
            String filePath = "C:\\Users\\Dell\\Pictures\\Qr-codes\\QRCode_" + studentId + ".png"; // Updated path
            File outputFile = new File(filePath);

            // Save the buffered image to file
            ImageIO.write(bufferedImage, "png", outputFile);
            System.out.println("QR Code saved at: " + filePath);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("QR Code saved successfully at: " + filePath);
            alert.show();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving QR Code image: " + e.getMessage()).show();
        }
    }

}
