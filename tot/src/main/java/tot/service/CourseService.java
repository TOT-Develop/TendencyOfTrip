package tot.service;

import java.util.List;
import java.util.Map;

import tot.domain.CourseDTO;
import tot.domain.LodgingDTO;
import tot.domain.RestaurantDTO;
import tot.domain.TourDTO;

public interface CourseService {

	Map<Integer, List<Object>> getDailyCourseByTripId(int tripId);

	List<String> extractIdsFromDcourse(String dcourse);

	CourseDTO getCourseById(String courseId);

	List<CourseDTO> getCourseDetailsByTripId(int tripId);

	List<LodgingDTO> selectHotel(String areacode) throws Exception;

	List<RestaurantDTO> selectRestaurant(String areacode) throws Exception;

	List<TourDTO> selectTour(String areacode) throws Exception;

	void updateDcourses(List<Integer> courIds, List<String> dcourses, Long tripId);

}
