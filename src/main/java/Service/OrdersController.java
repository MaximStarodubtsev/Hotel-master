package Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class OrdersController {

    @Autowired
    private final HotelService hotelService;

    @ModelAttribute
    public void message(Model model) {
        model.addAttribute("orders", hotelService.getOrdersForCurrentUser());
        model.addAttribute("username", hotelService.getUser().getName());
    }

    @GetMapping("/orders")
    public String orders(Model model){
        if(hotelService.getUser().getName() == null){
            return "redirect:/login";
        }
        return "orders";
    }

}
