package tot.service;

import java.util.List;
import java.util.Map;

import tot.domain.CourseDTO;

public interface CourseService {

	public abstract Map<Integer, List<Object>> getDailyCourseByTripId(int tripId);

	public abstract List<String> extractIdsFromDcourse(String dcourse);
	
	public abstract CourseDTO getCourseById(String courseId);

	public abstract List<CourseDTO> getCourseDetailsByTripId(int tripId);


}
