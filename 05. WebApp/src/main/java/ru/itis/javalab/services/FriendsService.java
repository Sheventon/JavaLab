package ru.itis.javalab.services;

import ru.itis.javalab.models.Friend;

import java.util.List;

public interface FriendsService {
    List<Friend> getByUserId(Long userId);
    Long addNewFriend(String firstName, String lastName, Integer age, String description, Long userId);
}
