package Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class GuestsController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/home")
    public String home(Model model){
        return "redirect:/booking";
    }

    @ModelAttribute
    public void message(Model model) {
        model.addAttribute("guests", hotelService.getAllGuests());
    }

    @GetMapping("/guests")
    public String guests(Model model){
        if(hotelService.getUser().getName() == null){
            return "redirect:/login";
        }
        if(hotelService.getUser().getName().equals("superUser")) {
            return "guests";
        } else {
            return "redirect:/login";
        }
    }

}
