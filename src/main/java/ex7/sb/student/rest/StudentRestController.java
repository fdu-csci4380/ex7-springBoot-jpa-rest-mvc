/**
 * 
 */
package ex7.sb.student.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ex7.sb.student.jpa.model.Student;
import ex7.sb.student.jpa.repo.StudentRepository;

/**
 * @author ilker
 *
 */
@RestController
@RequestMapping("rest/v1/students")
public class StudentRestController {
	@Autowired StudentRepository studentRepository;

	// curl -i -X GET http://localhost:8888/rest/v1/students/echoMessage?msg=Hi
//	@RequestMapping(value="/echoMessage", method=RequestMethod.GET)
	@RequestMapping("/echoMessage") // NOTE ilker, equivalent to above line since default is GET
//	@GetMapping("/echoMessage")		// NOTE ilker, equivalent to above line as well
	/**
	 * http://localhost:8888/rest/v1/students/echoMessage?msg=Hi
	 */
	public String echoMessage(@RequestParam(value="msg", defaultValue="Hello ilker") String message) {
		return "echoMessage echoed: " + message;
	}

	// curl -i http://localhost:8888/rest/v1/students
	// curl -i http://localhost:8888/rest/v1/students?page=2
	// curl -i http://localhost:8888/rest/v1/students?rowsPerPage=3
	// curl -i "http://localhost:8888/rest/v1/students?page=2&rowsPerPage=3"
	@GetMapping("")
	public Page<Student> findAll(@RequestParam(defaultValue="0") int page, @RequestParam(value="rowsPerPage", defaultValue="5") int size) {
		Page<Student> studentsPage = studentRepository.findAll(new PageRequest(page, size));
		return studentsPage;
	}

	// curl -i http://localhost:8888/rest/v1/students/all
	@GetMapping("/all")
	public  List<Student> findAll() {
		List<Student> students = studentRepository.findAll();
		return students;
	}

//	curl -X POST -H "Content-Type: application/json" -i  -d '{"name":"ilker_0", "lastname":"kiris_0", "grade":"freshman ", "age":200, "isFullTime":false, "updatedOn":"2018-04-29"}' http://localhost:8888/rest/v1/students
//	curl -X POST -H "Content-Type: application/json"     -d '{"name":"ilker_1", "lastname":"kiris_1", "grade":"FreshMan",  "age":201, "isFullTime":false, "updatedOn":"2018-04-29"}' http://localhost:8888/rest/v1/students
//	curl -X POST -H "Content-Type: application/json" --data '{"name":"ilker_2", "lastname":"kiris_2", "grade":" freshman", "age":202, "isFullTime":true,  "updatedOn":"2018-04-29"}' http://localhost:8888/rest/v1/students	
	@PostMapping("")
	public  Optional<Student> save(@RequestBody final Student student) {
		Student savedStudent = studentRepository.save(student);
		return studentRepository.findById(savedStudent.getStudentId());
	}
	
	// curl -X DELETE -i http://localhost:8888/rest/v1/students/2
	@DeleteMapping("/{petId}")
	public  void delete(@PathVariable("petId") Integer id) {
		studentRepository.deleteById(id);
	}
	
	// curl -i http://localhost:8888/rest/v1/students/findByNameIgnoreCaseQuery/ilker
	// curl -i http://localhost:8888/rest/v1/students/findByNameIgnoreCaseQuery/ILKer
	@GetMapping("/findByNameIgnoreCaseQuery/{studentName}")
	public  Student findByNameIgnoreCaseQuery(@PathVariable String studentName) {
		Student student = studentRepository.findByNameIgnoreCaseQuery(studentName);
		return student;
	}
	
}
