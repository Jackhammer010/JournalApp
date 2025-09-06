package com.practice.JournalApp.repository;

import com.practice.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    public User findByUsername(String username);
    public void deleteByUsername(String username);
}
