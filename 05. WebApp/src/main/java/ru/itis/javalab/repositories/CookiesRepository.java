package ru.itis.javalab.repositories;

import ru.itis.javalab.models.UserCookie;

import java.util.List;

public interface CookiesRepository extends CrudRepository<UserCookie> {
    UserCookie findUserCookieByUserId(Long id);
    UserCookie findUserCookieByCookie(String cookie);
}
