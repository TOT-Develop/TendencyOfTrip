package tot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tot.dao.CourseDao;
import tot.domain.CourseDTO;
import tot.domain.CourseResDTO;
import tot.domain.LodgingDTO;
import tot.domain.RestaurantDTO;
import tot.domain.TourDTO;

@Service
public class CourseServiceImpl implements CourseService {

	private CourseDao courseDao;

	public CourseServiceImpl(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	@Override
	public Map<Integer, List<Object>> getDailyCourseByTripId(int tripId) {
		List<CourseDTO> courses = courseDao.getCourse(tripId);
		Map<Integer, List<Object>> dailyCourses = new LinkedHashMap<>();

		for (CourseDTO course : courses) {
			String dcourse = course.getdCourse();
			int courId = course.getCourId(); // courId 기준으로

			List<String> idsList = extractIdsFromDcourse(dcourse);
			// courId를 키로 사용하여 새로운 리스트 생성
			dailyCourses.putIfAbsent(courId, new ArrayList<>());
			List<Object> dailyList = dailyCourses.get(courId);

			for (String id : idsList) {
				String[] parts = id.split(":");
				String type = parts[0];
				int idValue = Integer.parseInt(parts[1]);

				if ("TOID".equals(type)) {
					TourDTO tour = courseDao.getTour(String.valueOf(idValue));
					if (tour != null) {
						dailyList.add(tour);
					}
				} else if ("RESTID".equals(type)) {
					RestaurantDTO restaurant = courseDao.getRestaurant(idValue);
					if (restaurant != null) {
						dailyList.add(restaurant);
					}
				} else if ("LODID".equals(type)) {
					LodgingDTO lodging = courseDao.getLodging(idValue);
					if (lodging != null) {
						dailyList.add(lodging);
					}
				}
			}
		}
		return dailyCourses;
	}

	@Override
	public List<String> extractIdsFromDcourse(String dcourse) {
		Pattern pattern = Pattern.compile("(TOID|RESTID|LODID):(\\d+)");
		Matcher matcher = pattern.matcher(dcourse);

		List<String> idsList = new ArrayList<>();
		while (matcher.find()) {
			String key = matcher.group(1);
			String value = matcher.group(2);
			idsList.add(key + ":" + value);
		}
		return idsList;
	}

	@Override
	public CourseDTO getCourseById(String courseId) {
		return courseDao.getCourseById(courseId);
	}

	@Override
	public List<CourseDTO> getCourseDetailsByTripId(int tripId) {
		List<CourseDTO> courseList = courseDao.getCourseByTripId(tripId);
		for (CourseDTO course : courseList) {
			List<CourseResDTO> courseDetails = new ArrayList<>();
			String[] courseTypes = course.getdCourse().split(",");

			// 로그 추가: courseTypes 출력
			System.out.println("Course Types: " + Arrays.toString(courseTypes));

			for (String courseType : courseTypes) {
				String[] typeAndId = courseType.split(":");
				// 로그 추가: typeAndId 출력
				System.out.println("Type and ID: " + Arrays.toString(typeAndId));

				// 로그 추가: 메서드 호출 전 확인
				System.out.println("Calling getCourseDetailsById with type: " + typeAndId[0] + ", id: "
						+ Integer.parseInt(typeAndId[1]));

				CourseResDTO courseDetail = courseDao.getCourseDetailsById(typeAndId[0],
						Integer.parseInt(typeAndId[1]));
				courseDetails.add(courseDetail);
			}
			course.setCourseDetail(courseDetails);
		}
		System.out.println(courseList);
		return courseList;
	}

	@Override
	public List<LodgingDTO> selectHotel(String areacode) throws Exception {
		return courseDao.selectHotel(areacode);
	}

	@Override
	public List<RestaurantDTO> selectRestaurant(String areacode) throws Exception {
		return courseDao.selectRestaurant(areacode);
	}

	@Override
	public List<TourDTO> selectTour(String areacode) throws Exception {
		return courseDao.selectTour(areacode);
	}

	@Transactional
	@Override
	public void updateDcourses(List<Integer> courIds, List<String> dcourses, Long tripId) {
		try {
			for (int i = 0; i < courIds.size(); i++) {
				Integer courId = courIds.get(i);
				String dcourse = dcourses.get(i);

				courseDao.updateDcourse(courId, dcourse, tripId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
