package tot.domain;

import tot.domain.Restaurant;
import tot.dao.RestaurantDao;
import tot.util.FileUtil;

import java.util.List;

import com.google.gson.Gson;
//import com.mysql.cj.Session;

public class test {

    public static void main(String[] args) {
        RestaurantDao restaurantDao = new RestaurantDao();
        
        try {
        	String filePath = "D:/embeded/restaurant/restaurant15.json";
        	
            String jsonString = FileUtil.readFileAsString(filePath);
//            System.out.println(jsonString);

            Gson gson = new Gson();
            
            Restaurant[] restaurantArray = gson.fromJson(jsonString, Restaurant[].class);
            int restaurantArrayLeng = restaurantArray.length;
            System.out.println(restaurantArray.length);
            
            if (restaurantArrayLeng > 0) {
            	for (Restaurant restaurant : restaurantArray) {
            		restaurantDao.insertRestaurant(restaurant);
            	}
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("저장 완료");
    }
}
