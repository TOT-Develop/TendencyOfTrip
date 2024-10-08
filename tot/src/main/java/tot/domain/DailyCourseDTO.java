package tot.domain;

import java.util.List;

public class DailyCourseDTO {
	private int day;
	private List<TourDTO> tours;
	private List<RestaurantDTO> restaurants;
	private List<LodgingDTO> lodgings;

	public int getDay() {
		return day;
	}

	public List<TourDTO> getTours() {
		return tours;
	}

	public List<RestaurantDTO> getRestaurants() {
		return restaurants;
	}

	public List<LodgingDTO> getLodgings() {
		return lodgings;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setTours(List<TourDTO> tours) {
		this.tours = tours;
	}

	public void setRestaurants(List<RestaurantDTO> restaurants) {
		this.restaurants = restaurants;
	}

	public void setLodgings(List<LodgingDTO> lodgings) {
		this.lodgings = lodgings;
	}

}
