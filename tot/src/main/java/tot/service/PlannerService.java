package tot.service;

import java.util.List;

import tot.domain.CourseDTO;
import tot.domain.Hotel;
import tot.domain.Restaurant;
import tot.domain.Tour;
import tot.domain.Trip;

public interface PlannerService {

	List<Hotel> selectHotel(String areacode) throws Exception;

	List<Restaurant> selectRestaurant(String areacode) throws Exception;

	List<Tour> selectTour(String areacode) throws Exception;

	void insertTrip(Trip trip) throws Exception;

	void insertCourse(CourseDTO courseDTO) throws Exception;

	Integer selectLatestTripId() throws Exception;

	void updateMemberMbt(String memId, String newMbti) throws Exception;
}
