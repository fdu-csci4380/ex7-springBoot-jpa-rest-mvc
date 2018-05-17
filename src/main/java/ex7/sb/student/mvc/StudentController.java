/**
 * 
 */
package ex7.sb.student.mvc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ex7.sb.student.jpa.model.Student;
import ex7.sb.student.jpa.repo.StudentRepository;

/**
 * @author ilker
 *
 */
@Controller
@RequestMapping("/mvc/student")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;
	
//	@RequestMapping(value="/echoMessage", method=RequestMethod.GET)
	@RequestMapping("/echoMessage") // NOTE ilker, equivalent to above line since default is GET
//	@GetMapping("/echoMessage")		// NOTE ilker, equivalent to above line as well
	@ResponseBody					// NOTE ilker, means this method is not returning name of "view", just pass whatever it returns to Http Response
	/**
	 * http://localhost:8888/mvc/student/echoMessage?msg=Hi
	 */
	public String echoMessage(@RequestParam(value="msg", defaultValue="Hello ilker") String message) {
		return "echoMessage echoed: " + message;
	}
	
	@GetMapping("/index4angular-hello")
	public String index4angular_hello() {
		return "index4angular-hello";
	}
	
	@GetMapping("/list")
	public String showStudentList(Model model, @RequestParam(defaultValue="0") int page, @RequestParam(value="rowsPerPage", defaultValue="5") int size) {
		Page<Student> studentsPage = studentRepository.findAll(new PageRequest(page, size));
		model.addAttribute("studentsData", studentsPage);
		model.addAttribute("currentPage", page);	// for UI nav-pills to high light "active" page
		model.addAttribute("rowsPerPage", size);	// example of passing 2 uri params
		return "studentList";
	}
	
	// /mvc/student/save    POST
	@PostMapping("/save")   
	public String saveStudent(Student student) {
		studentRepository.save(student);
		return "redirect:/mvc/student/list";	// after student is added, want to go to student list view, so redirect to it's url
	}
	
	@GetMapping("/delete")
	public String deleteStudent(Integer studentId) {
		studentRepository.deleteById(studentId);
		return "redirect:/mvc/student/list";	// after student is deleted, want to go to student list view, so redirect to it's url
	}
	
	@GetMapping("/detailOptional")
	@ResponseBody			// NOTE ilker since will be using jQuery for this, let it return
	public Optional<Student> showStudentDetailOptional(Integer studentId) {
		return studentRepository.findById(studentId);
	}
	
	// NOTE ilker this is equivalent to findOne or findById
	@GetMapping("/detail")	// /mvc/student/detail
	@ResponseBody			// NOTE ilker since will be using jQuery for this, let it return the StudentEntity
	public Student showStudentDetail(@RequestParam("studentId") Integer studentId) {
		Optional<Student> student = studentRepository.findById(studentId);
		return student.isPresent() ? student.get() : null;
	}
}
