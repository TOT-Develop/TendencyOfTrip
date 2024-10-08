package tot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tot.domain.CourseDTO;
import tot.domain.CourseResDTO;
import tot.domain.LodgingDTO;
import tot.domain.RestaurantDTO;
import tot.domain.TourDTO;

public interface CourseDao {

	TourDTO getTour(String toId);

	RestaurantDTO getRestaurant(int restId);

	LodgingDTO getLodging(int lodId);

	List<CourseDTO> getCourse(int tripId);

	CourseDTO getCourseById(String courseId);

	List<CourseDTO> getCourseByTripId(int tripId);

	CourseResDTO getCourseDetailsById(@Param("dcourseType") String dcourseType, @Param("dcourseId") int dcourseId);

	List<LodgingDTO> selectHotel(String areacode) throws Exception;

	List<RestaurantDTO> selectRestaurant(String areacode) throws Exception;

	List<TourDTO> selectTour(String areacode) throws Exception;

	void updateDcourse(@Param("courId") Integer courId, @Param("dcourse") String dcourse, @Param("tripId") Long tripId);

}
