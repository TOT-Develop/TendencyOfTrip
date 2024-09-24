package tot.service;

import java.util.List;

import tot.domain.Restaurant;

public interface restaurantService {
		
		public abstract List<Restaurant> selectRestaurant() throws Exception;
		
		public abstract int insertRestaurant(Restaurant restaurant) throws Exception;
}
