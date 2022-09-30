package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    private int userId;
    private int friendId;

    @JsonIgnore
    Set<Integer> friend_ID = new HashSet<>();

    public Friend(int user_id, int friend_id) {
        this.userId = user_id;
        this.friendId = friend_id;
    }
}
