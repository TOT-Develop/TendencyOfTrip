package tot.service;

import org.springframework.stereotype.Service;

import tot.dao.HotelDao;
import tot.domain.Accommodation;

@Service("HotelService")
public class HotelServiceImpl implements HotelService {

	private final HotelDao hotelDao;

	public HotelServiceImpl(HotelDao hotelDao) {
		this.hotelDao = hotelDao;
	}

	@Override
	public void insertHotel(Accommodation accommodation) {
		hotelDao.insertHotel(accommodation);
	}

}
