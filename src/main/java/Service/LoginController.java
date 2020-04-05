package Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class LoginController {

    @Autowired
    HotelService hotelService;

    @ModelAttribute
    public void message(Model model) {
        model.addAttribute("loginMessage", hotelService.getLoginMessage());
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        if(!hotelService.isThreadStarted()) {
            Thread thread = new Thread(hotelService);
            thread.start();
            hotelService.setThreadStarted(true);
        }

        hotelService.getUser().setName(request.getParameter("username"));
        hotelService.getUser().setPassword(request.getParameter("password"));
        if(hotelService.validLoginData()){
            hotelService.setLoginMessage("");
            return "redirect:/booking";
        } else {
            hotelService.setLoginMessage("The data is invalid");
            return "login";
        }
    }
}
