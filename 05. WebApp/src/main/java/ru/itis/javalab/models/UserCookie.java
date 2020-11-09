package ru.itis.javalab.models;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class UserCookie {
    private Long userId;
    private String userCookie;
}
