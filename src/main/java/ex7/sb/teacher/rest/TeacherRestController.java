/**
 * 
 */
package ex7.sb.teacher.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ex7.sb.teacher.mongodb.model.Teacher;
import ex7.sb.teacher.mongodb.repo.TeacherRepository;

/**
 * @author ilker
 *
 */
@RestController
@RequestMapping("rest/v1/teachers")
public class TeacherRestController {
	private TeacherRepository teacherRepository;
	// NOTE ilker turns out, we don't even need to put "@Autowired" here for "Constructor injection". Spring does it even without it
	public TeacherRestController(TeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
	}

	@GetMapping("/echoMessage")
	/**
	 * curl -i http://localhost:8888/rest/v1/teachers/echoMessage?msg=Hi
	 */
	public String echoMessage(@RequestParam(value="msg", defaultValue="Hello ilker") String message) {
		return "echoMessage echoed: " + message;
	}

	// curl -i http://localhost:8888/rest/v1/teachers
	// curl -i http://localhost:8888/rest/v1/teachers?page=0&rowsPerPage=5
	@GetMapping("")
	public Page<Teacher> findAll(@RequestParam(defaultValue="0") int page, @RequestParam(value="rowsPerPage", defaultValue="5") int size) {
		Page<Teacher> teachersPage = teacherRepository.findAll(new PageRequest(page, size));
		return teachersPage;
	}

	// curl -i http://localhost:8888/rest/v1/teachers/all
	@GetMapping("/all")
	public  List<Teacher> findAll() {
		List<Teacher> teachers = teacherRepository.findAll();
		return teachers;
	}

	// curl -i http://localhost:8888/rest/v1/teachers/teacherId/1
	@GetMapping("/{id}")
	public  Optional<Teacher> findById(@PathVariable String id) {
		Optional<Teacher> teacher = teacherRepository.findById(id);
		return teacher;
	}

	// curl -i http://localhost:8888/rest/v1/teachers/teacherId/1
	@GetMapping("/teacherId/{teacherId}")
	public  Optional<List<Teacher>> findByTeacherId(@PathVariable String teacherId) {
		Optional<List<Teacher>> teachers = teacherRepository.findByTeacherId(teacherId);
		return teachers;
	}

	@PostMapping("")
	/**
	 * NOTE "save" is basically upsert. If teacher is not there in DB, it INSERTs. If teacher is there in DB it UPDATEs
	 * @param teacher
	 * @return
	 */
	public  Optional<Teacher> save(@RequestBody final Teacher teacher) {
		Teacher savedTeacher = teacherRepository.save(teacher);
		return teacherRepository.findById(savedTeacher.getId());
	}

	@PutMapping("")
	public  Teacher insert(@RequestBody final Teacher teacher) {
		Teacher insertedTeacher = teacherRepository.insert(teacher);
		return insertedTeacher;
	}
	
	// curl -X DELETE -i http://localhost:8888/rest/v1/teachers/5ad826f03eb4f729f4394098
	@DeleteMapping("/{id}")
	public  void delete(@PathVariable("id") String id) {
		teacherRepository.deleteById(id);
	}
	
	// curl -X DELETE -i http://localhost:8888/rest/v1/teachers/teacherId/1
	@DeleteMapping("/teacherId/{teacherId}")
	public  void deleteByTeacherId(@PathVariable String teacherId) {
		teacherRepository.deleteByTeacherId(teacherId);
	}
	
}