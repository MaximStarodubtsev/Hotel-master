package Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class BookingController {

    @Autowired
    private HotelService hotelService;

    @ModelAttribute
    public void message(Model model) {
        model.addAttribute("orders", hotelService.getReservedOrders());
        model.addAttribute("message", hotelService.getMessage());
        model.addAttribute("validDataMessage", hotelService.getValidDataMessage());
        model.addAttribute("rooms", hotelService.getAllRooms());
        model.addAttribute("guests", hotelService.getAllGuests());
    }

    @GetMapping("/booking")
    @RequestMapping( value="/booking", method = RequestMethod.GET)
    public String getPage(Model model) {
        if(hotelService.getUser().getName() == null){
            return "login";
        }
        return "booking";
    }

    @PostMapping(value = "/booking")
    public String order(HttpServletRequest request) {
        if(hotelService.validData(request.getParameter("name"), request.getParameter("surname"),
                request.getParameter("phonenumber"),
                request.getParameter("settlementdate"),
                request.getParameter("leavedate"))){
            hotelService.setValidDataMessage(null);
        } else {
            hotelService.setValidDataMessage("The data is invalid");
            return "booking";
        }

        String[] room = request.getParameter("room").split(" ");
        if(!room.equals(null)) {
            if (hotelService.checkingFreeRoom(Long.valueOf(room[0]),
                    request.getParameter("settlementdate"),
                    request.getParameter("leavedate"))) {
                hotelService.makeOrder(request.getParameter("name"),
                        request.getParameter("surname"),
                        request.getParameter("patronymic"),
                        request.getParameter("birthdate"),
                        request.getParameter("phonenumber"),
                        request.getParameter("settlementdate"),
                        request.getParameter("leavedate"),
                        Integer.valueOf(room[0]), Integer.valueOf(room[4]));
                hotelService.setMessage(
                        String.format(("The room number %s was reserved for term: %s - %s"),
                                room[1], request.getParameter("settlementdate"),
                                request.getParameter("leavedate")));
            } else {
                hotelService.setMessage("The room is busy for chosen term");
            }
        }
        else {
            hotelService.setMessage("The room is busy for chosen term");
        }
        return "redirect:/booking";
    }
}
