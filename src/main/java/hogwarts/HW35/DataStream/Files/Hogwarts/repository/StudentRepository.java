package hogwarts.HW35.DataStream.Files.Hogwarts.repository;


import hogwarts.HW35.DataStream.Files.Hogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findStudentByAge (Integer age);
    Collection<Student> findByAgeBetweenIgnoreCase(int min, int max);

}
