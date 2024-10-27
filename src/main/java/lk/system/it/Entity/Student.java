package lk.system.it.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Student {

    private String student_id;
    private String student_name;
    private String contact_number;
    private String address;
    private String school;
    private byte[] photo;

}
