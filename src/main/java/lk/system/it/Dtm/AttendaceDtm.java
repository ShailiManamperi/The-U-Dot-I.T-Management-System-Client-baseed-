package lk.system.it.Dtm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class AttendaceDtm {
    private LocalDate date;
    private String status;
}
