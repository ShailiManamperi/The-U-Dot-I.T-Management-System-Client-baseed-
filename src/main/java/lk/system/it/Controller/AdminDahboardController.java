package lk.system.it.Controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import lk.system.it.Service.ServiceFactory;
import lk.system.it.Service.ServiceTypes;
import lk.system.it.Service.custom.AttendanceService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class AdminDahboardController {

    @FXML
    private BarChart<String, Integer> bargraph1;

    @FXML
    private BarChart<String, Integer> bargraph2;

    private AttendanceService attendanceService;

    public void initialize() throws SQLException {
        this.attendanceService = ServiceFactory.getInstance().getService(ServiceTypes.ATTEND);
        loadData();
    }

    private void loadData() throws SQLException {
        // Retrieve attendance data by city and date from the AttendanceService
        Map<String, Map<LocalDate, Integer>> dailyAttendanceByCity = attendanceService.getDailyAttendanceByCity();
        String[] cities = dailyAttendanceByCity.keySet().toArray(new String[0]);
        // Assuming we have two cities, we’ll load data for each into the respective BarChart
        if (dailyAttendanceByCity.size() >= 2) {
            System.out.println(dailyAttendanceByCity); // Debug output to verify data

            // Extracting first two cities and their attendance date
            loadCityAttendanceData(cities[0], dailyAttendanceByCity.get(cities[0]), bargraph1);
            loadCityAttendanceData(cities[1], dailyAttendanceByCity.get(cities[1]), bargraph2);
        } else {
            System.out.println("Not enough cities to display two charts.");
        }
    }

    private void loadCityAttendanceData(String city, Map<LocalDate, Integer> attendanceData, BarChart<String, Integer> barChart) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Attendance for " + city);
        // Loop through attendance data to populate the series
        for (Map.Entry<LocalDate, Integer> entry : attendanceData.entrySet()) {
            String date = entry.getKey().toString();  // Converting LocalDate to String for x-axis labels
            Integer count = entry.getValue();
            System.out.println(date + " "+ count);
            // Adding data for each date
            series.getData().add(new XYChart.Data(date, count));
        }

        // Clear any existing data and add the new series to the BarChart
        barChart.getData().clear();
        barChart.getData().add(series);
    }
}
