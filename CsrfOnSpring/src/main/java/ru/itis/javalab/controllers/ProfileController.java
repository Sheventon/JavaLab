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
import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView getProfile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = usersService.getByUsername((String) session.getAttribute("user"));
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String deleteUser(@RequestParam String action,
                             @RequestParam String userId,
                             HttpServletRequest request) {
        if (action != null && action.equals("delete")) {
            usersService.deleteById(Long.parseLong(userId));
            request.getSession().invalidate();
        }
        return "redirect:signIn";
    }
}
