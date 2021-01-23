package ru.itis.javalab.repositories;

import ru.itis.javalab.models.Friend;

import java.util.List;

public interface FriendsRepository extends CrudRepository<Friend> {
    List<Friend> findByUserId(Long UserId);
}
