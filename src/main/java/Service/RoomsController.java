package Service;

import lombok.RequiredArgsConstructor;
import org.hibernate.jpamodelgen.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class RoomsController {
    @Autowired
    private HotelService hotelService;

    @ModelAttribute
    public void message(Model model, HttpServletRequest request) {
        model.addAttribute("validRoomMessage", hotelService.getValidDataMessage());
    }

    @GetMapping("/rooms")
    public String rooms(Model model){
        if(!hotelService.getUser().getName().equals("superUser")) {
            return "redirect:/login";
        }
        return "rooms";
    }

    @PostMapping("/rooms")
    public String rooms(Model model, HttpServletRequest request){
        if(hotelService.getUser().getName() == null){
            return "redirect:/login";
        }
        if(!hotelService.getUser().getName().equals("superUser")) {
            return "redirect:/login";
        }
        if(hotelService.validRoomData(request.getParameter("number"),
                request.getParameter("places"),
                request.getParameter("level"),
                request.getParameter("rent"))){
            hotelService.setValidRoomMessage(null);
        } else {
            hotelService.setValidRoomMessage("The data is invalid");
            return "rooms";
        }

        hotelService.addRoom(Integer.valueOf(request.getParameter("number")),
                request.getParameter("places"),
                request.getParameter("level"),
                Integer.valueOf(request.getParameter("rent")));
        hotelService.setValidRoomMessage(
                String.format("The room number %s was added", request.getParameter("number")));
        return "rooms";
    }
}
