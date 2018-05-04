/**
 * 
 */
package ex7.sb.student.jpa.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ex7.sb.student.jpa.model.Student;

/**
 * Couple of example native queries
 *   @Query("select s from STUDENT s where lower(s.name) = lower(:studentName)")
 *   public Student findByNameIgnoreCaseQuery(@Param("studentName") String studentName);
 * 
 *   @Query("select s from STUDENT s where s.name = ?1")
 *   public Student findByNameQueryPositionalParam(String studentName);
 * 
 * @author ilker
 *
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {
	public Optional<List<Student>> findByName(String name);
	
	@Query("select s from STUDENT s where lower(s.name) = lower(:studentName)")
	public Student findByNameIgnoreCaseQuery(@Param("studentName") String studentName);
	
}
