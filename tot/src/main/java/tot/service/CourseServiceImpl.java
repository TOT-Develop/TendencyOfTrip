package tot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tot.dao.CourseDao;
import tot.domain.CourseDTO;
import tot.domain.CourseResDTO;
import tot.domain.LodgingDTO;
import tot.domain.RestaurantDTO;
import tot.domain.TourDTO;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseDao courseDao;

    @Override
    public Map<Integer, List<Object>> getDailyCourseByTripId(int tripId) {
        // 1. Trip ID에 맞는 코스 목록 가져오기 (courid 순으로 정렬됨)
        System.out.println("Fetching courses for tripId: " + tripId);
        
        List<CourseDTO> courses = courseDao.getCourse(tripId);
        System.out.println("Courses fetched: " + courses);
        // 2. DCOURSE 값을 파싱하여 각 코스를 일차별로 나누기
        Map<Integer, List<Object>> dailyCourses = new LinkedHashMap<>();
        
        int dayCounter = 1;  // 일차 번호를 1부터 시작
        
        for (CourseDTO course : courses) {
            String dcourse = course.getDcourse();
            
            // DCOURSE 값에서 LODID, RESTID, TOID 추출
            List<String> idsList = extractIdsFromDcourse(dcourse);

            // 해당 일차에 코스를 추가
            dailyCourses.putIfAbsent(dayCounter, new ArrayList<>()); // 순차적으로 일차 할당
            List<Object> dailyList = dailyCourses.get(dayCounter);

            // 각 ID를 기반으로 데이터 조회 및 추가
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

            dayCounter++;  // 다음 일차 번호로 증가
        }
        System.out.println(dailyCourses);
        return dailyCourses;
    }

    @Override
    // DCOURSE 파싱 메서드
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
            String[] courseTypes = course.getDcourse().split(",");

            // 로그 추가: courseTypes 출력
            System.out.println("Course Types: " + Arrays.toString(courseTypes));

            for (String courseType : courseTypes) {
                String[] typeAndId = courseType.split(":");
                // 로그 추가: typeAndId 출력
                System.out.println("Type and ID: " + Arrays.toString(typeAndId));

                // 로그 추가: 메서드 호출 전 확인
                System.out.println("Calling getCourseDetailsById with type: " + typeAndId[0] + ", id: " + Integer.parseInt(typeAndId[1]));

                CourseResDTO courseDetail = courseDao.getCourseDetailsById(typeAndId[0],
                        Integer.parseInt(typeAndId[1]));
                courseDetails.add(courseDetail);
            }
            course.setCourseDetail(courseDetails);
        }
        System.out.println(courseList);
        return courseList;
    }


}
