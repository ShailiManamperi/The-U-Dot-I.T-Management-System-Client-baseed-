package lk.system.it.Service.util;

import lk.system.it.Dto.CourseDto;
import lk.system.it.Dto.StudentDto;
import lk.system.it.Dto.Student_CourseDto;
import lk.system.it.Entity.Course;
import lk.system.it.Entity.Student;
import lk.system.it.Entity.Student_Course;

public class Converter {
    public StudentDto fromStudent(Student student){
        return new StudentDto(student.getStudent_id(),student.getStudent_name(),student.getContact_number(),student.getAddress(),student.getSchool(),student.getPhoto());
    }

    public Student tostudent(StudentDto studentDto){
        return new Student(studentDto.getStudent_id(),studentDto.getStudent_name(),studentDto.getContact_number(),studentDto.getAddress(),studentDto.getSchool(),studentDto.getPhoto());
    }
    public CourseDto fromCourse(Course course){
        return new CourseDto(course.getCourse_id(), course.getCourse_name(), course.getFees(), course.getDuration());
    }

    public Course toCourse(CourseDto courseDto){
        return new Course(courseDto.getCourse_id(), courseDto.getCourse_name(), courseDto.getFees(), courseDto.getDuration());
    }

    public Student_CourseDto fromStud_Cour(Student_Course studentCourse ){
        return new Student_CourseDto(studentCourse.getStudent_id(),studentCourse.getCourse_id(),studentCourse.getStatus(), studentCourse.getQr());
    }

    public Student_Course toStud_Cour(Student_CourseDto studentCourseDto){
        return new Student_Course(studentCourseDto.getStudent_id(),studentCourseDto.getCourse_id(),studentCourseDto.getStatus(),studentCourseDto.getQr());
    }
}

