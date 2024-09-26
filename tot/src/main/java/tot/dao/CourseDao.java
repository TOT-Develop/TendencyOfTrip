package tot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import tot.domain.CourseDTO;
import tot.domain.CourseResDTO;
import tot.domain.LodgingDTO;
import tot.domain.RestaurantDTO;
import tot.domain.TourDTO;

public interface CourseDao {
	public abstract TourDTO getTour(String toId);
	public abstract RestaurantDTO getRestaurant(int restId);
	public abstract LodgingDTO getLodging(int lodId);
	public abstract List<CourseDTO> getCourse(int tripId);  
	public abstract CourseDTO getCourseById(String courseId);
	public abstract List<CourseDTO> getCourseByTripId(int tripId);
	public abstract CourseResDTO getCourseDetailsById(@Param("dcourseType") String dcourseType, @Param("dcourseId") int dcourseId);
}
