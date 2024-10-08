package tot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tot.domain.CourseDTO;
import tot.service.CourseService;

@RestController
@RequestMapping("/course")
public class CourseRestController {

	private final CourseService courseService;

	public CourseRestController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping("/{courseId}")
	public CourseDTO getCourseById(@PathVariable("courseId") String courseId) {
		return courseService.getCourseById(courseId);
	}

}
