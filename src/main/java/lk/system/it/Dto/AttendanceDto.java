package lk.system.it.Dto;

import lk.system.it.Dao.DaoTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AttendanceDto {
    private String student_id;
    private LocalDate date;
    private String status;
}
