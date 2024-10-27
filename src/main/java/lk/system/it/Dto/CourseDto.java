package lk.system.it.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CourseDto {

    private String course_id;
    private String course_name;
    private Double fees;
    private String duration;

}
