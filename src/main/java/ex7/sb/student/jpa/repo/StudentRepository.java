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
	// NOTE ilker an example "Query method". Below link has many more examples of it
	//   https://docs.spring.io/spring-data/jpa/docs/1.6.0.RELEASE/reference/html/jpa.repositories.html#jpa.query-methods
	public Optional<List<Student>> findByName(String name);
	
	// NOTE ilker an example "native query using @Query"
	@Query("select s from STUDENT s where lower(s.name) = lower(:studentName)")
	public Student findByNameIgnoreCaseQuery(@Param("studentName") String studentName);
	
}
