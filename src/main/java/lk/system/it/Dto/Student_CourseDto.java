package lk.system.it.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Student_CourseDto {

    private String student_id;
    private String course_id;
    private String status;
    private byte[] Qr;
    private String city;


}
