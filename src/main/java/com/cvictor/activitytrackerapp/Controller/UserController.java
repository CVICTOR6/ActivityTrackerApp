package com.cvictor.activitytrackerapp.Controller;

import com.cvictor.activitytrackerapp.DTO.TaskDTO;
import com.cvictor.activitytrackerapp.DTO.UserDTO;
import com.cvictor.activitytrackerapp.Service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //    Method handler that directs default call to the index page
    @GetMapping(value = {"/", "/signup"})
    public String index(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "index";
    }

    // Method handler that adds users details to database and redirects users to login
    @PostMapping("/addUser")
    public String addUser( @ModelAttribute("user") UserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return "index";
        }
        userService.saveUserData(userDTO);
        return "redirect:/login";
    }

    //    Sends login view page
    @GetMapping("/login")
    public String showLogin(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "login";
    }

    //    Method handler that validates users password and username
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session) {
        UserDTO user = userService.validateUser(email, password);
        if (user == null) {
            return "redirect:/login";
        }
        session.setAttribute("currentUser", user);
        return "redirect:/home";
    }

    //    method handler that add PostRequest data to the model and redirects to home
    @GetMapping("/home")
    public String showHome(HttpSession httpSession, TaskDTO taskDto, Model model) {
        UserDTO userDTO =  (UserDTO) httpSession.getAttribute("currentUser");
        TaskDTO taskDTO = new TaskDTO();
        model.addAttribute("task", taskDTO);
        model.addAttribute("user", userDTO);
        return "home";
    }

}
