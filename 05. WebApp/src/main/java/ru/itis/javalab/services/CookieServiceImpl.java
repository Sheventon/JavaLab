package ru.itis.javalab.services;

import ru.itis.javalab.models.UserCookie;
import ru.itis.javalab.repositories.CookieRepository;
import ru.itis.javalab.repositories.UsersRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class CookieServiceImpl implements CookieService {

    private UsersRepository usersRepository;
    private CookieRepository cookieRepository;

    public CookieServiceImpl(UsersRepository usersRepository, CookieRepository cookieRepository) {
        this.usersRepository = usersRepository;
        this.cookieRepository = cookieRepository;
    }

    @Override
    public void generateCookieByUserId(HttpServletRequest req, HttpServletResponse resp, String username) {
        Long userId = usersRepository.findByUsername(username).getId();
        String auth;
        UserCookie userCookie = cookieRepository.findUserCookieByUserId(userId);
        if(userCookie != null) {
            auth = userCookie.getUserCookie();
        } else {
            do {
                auth = UUID.randomUUID().toString();
            } while (cookieRepository.findUserCookieByUserId(userId) != null);
            UserCookie newUserCookie = UserCookie.builder()
                    .userId(userId)
                    .userCookie(auth)
                    .build();

            cookieRepository.save(newUserCookie);
        }
        Cookie cookie = new Cookie("Auth", auth);
        cookie.setMaxAge(60 * 60);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    @Override
    public UserCookie getCookieByAuth(String cookie) {
        return cookieRepository.findUserCookieByCookie(cookie);
    }
}
