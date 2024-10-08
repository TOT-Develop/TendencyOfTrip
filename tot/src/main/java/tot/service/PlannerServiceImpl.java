package tot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tot.dao.PlannerDao;
import tot.domain.CourseDTO;
import tot.domain.Hotel;
import tot.domain.Restaurant;
import tot.domain.Tour;
import tot.domain.Trip;

@Service("PlannerService")
@Transactional
public class PlannerServiceImpl implements PlannerService {

	private PlannerDao plannerDao;

	public PlannerServiceImpl(PlannerDao plannerDao) {
		this.plannerDao = plannerDao;
	}

	@Override
	public List<Hotel> selectHotel(String areacode) throws Exception {
		return plannerDao.selectHotel(areacode);
	}

	@Override
	public List<Restaurant> selectRestaurant(String areacode) throws Exception {
		return plannerDao.selectRestaurant(areacode);
	}

	@Override
	public List<Tour> selectTour(String areacode) throws Exception {
		return plannerDao.selectTour(areacode);
	}

	@Override
	public void insertCourse(CourseDTO courseDTO) throws Exception {
		plannerDao.insertCourse(courseDTO);

	}

	@Override
	public void insertTrip(Trip trip) throws Exception {
		plannerDao.insertTrip(trip);
	}

	@Override
	public Integer selectLatestTripId() throws Exception {
		return plannerDao.selectLatestTripId();
	}

	@Override
	public void updateMemberMbt(String memId, String newMbti) throws Exception {
		plannerDao.updateMemberMbt(memId, newMbti);
	}

}
