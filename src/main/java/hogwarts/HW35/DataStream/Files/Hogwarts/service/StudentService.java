package hogwarts.HW35.DataStream.Files.Hogwarts.service;

import hogwarts.HW35.DataStream.Files.Hogwarts.model.Faculty;
import hogwarts.HW35.DataStream.Files.Hogwarts.model.Student;
import hogwarts.HW35.DataStream.Files.Hogwarts.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;



@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student find(Long id) {
        return studentRepository.findById(id).get();
    }


    public Student changeStudentInfo(Student student) {
        return studentRepository.save(student);
    }

    public  void removeStudent(Long id) {
        studentRepository.deleteById(id);

    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> findStudentByAge(Integer age) {
        return studentRepository.findStudentByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max){
        return studentRepository.findByAgeBetweenIgnoreCase(min, max);
    }
    public Faculty findFacultyByStudent(long id){
        return studentRepository.getReferenceById(id).getFaculty();
    }


}
