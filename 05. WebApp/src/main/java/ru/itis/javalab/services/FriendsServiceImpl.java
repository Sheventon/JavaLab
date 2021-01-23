package ru.itis.javalab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.javalab.models.Friend;
import ru.itis.javalab.repositories.FriendsRepository;

import javax.sql.DataSource;
import java.util.List;

@Component
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private FriendsRepository friendsRepository;

    /*public FriendsServiceImpl(FriendsRepository friendsRepository) {
        this.friendsRepository = friendsRepository;
    }*/

    @Override
    public List<Friend> getByUserId(Long userId) {
        return friendsRepository.findByUserId(userId);
    }

    @Override
    public Long addNewFriend(String firstName, String lastName, Integer age, String description, Long userId) {
        Friend friend = Friend.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .description(description)
                .userId(userId)
                .build();
        return friendsRepository.save(friend);
    }
}
