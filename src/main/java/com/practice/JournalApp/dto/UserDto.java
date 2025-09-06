package com.practice.JournalApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Component
@AllArgsConstructor
public class UserDto {
    @Id
    private ObjectId id;
    @NonNull
    private String username;
    @NonNull
    private List<String> roles;

}
