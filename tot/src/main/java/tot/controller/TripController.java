package tot.controller;

import static tot.common.Constants.PAGE_TRIP;
import static tot.common.Constants.PAGE_TRIPLIST;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tot.domain.MemberVO;
import tot.domain.TripDTO;
import tot.service.TripService;
import tot.util.MemberUtil;

@Controller
@RequestMapping("/trip")
public class TripController {

   private TripService tripService;

   public TripController(TripService tripService) {
      this.tripService = tripService;
   }

   // 여행 목록 화면 호출
   @GetMapping("/list")
   public String getTripList(@RequestParam(value = "page", defaultValue = "1") int currentPage,
         @RequestParam(value = "pageSize", defaultValue = "4") int pageSize, Model model) {
      MemberVO member = MemberUtil.isAuthenticatedMember();

      // 전체 여행 수 계산
      int totalTrips = tripService.getTotalTripsByMemId(member.getMemId());
      System.out.println(totalTrips);
      int totalPages = (int) Math.ceil((double) totalTrips / pageSize); // 전체 페이지 수 계산
      System.out.println(totalPages);
      // 여행 목록 가져오기
      List<TripDTO> trips = tripService.getTripsByMemIdWithPaging(member.getMemId(), currentPage, pageSize);
      System.out.println(trips);

      // 모델에 추가
      model.addAttribute("trips", trips);
      model.addAttribute("currentPage", currentPage);
      model.addAttribute("pageSize", pageSize);
      model.addAttribute("totalPages", totalPages); // totalPages 추가

      return PAGE_TRIPLIST;
   }

   @GetMapping
   public String getTripMap(@RequestParam(value = "tripId") String tripId, Model model) {
      MemberVO member = MemberUtil.isAuthenticatedMember();

      model.addAttribute("tripId", tripId);
      model.addAttribute("member", member);
      return PAGE_TRIP;
   }

}
