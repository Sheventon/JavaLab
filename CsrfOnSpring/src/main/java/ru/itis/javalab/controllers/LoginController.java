package ru.itis.javalab.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final UsersService usersService;

    @Autowired
    public LoginController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.GET)
    public ModelAndView getLoginPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject(request.getAttribute("csrf_token"));
        return modelAndView;
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public String signIn(@RequestParam String username,
                         @RequestParam String password,
                         HttpServletRequest request) {
        User user = usersService.getByUsername(username);
        if (!user.getIsDeleted()) {
            String userPassword = user.getPassword();
            if (usersService.matchesPasswords(password, userPassword)) {
                request.getSession().setAttribute("user", username);
                return "redirect:profile";
            } else {
                return "redirect:signIn";
            }
        } else {
            return "redirect:signIn";
        }
    }
}
