package com.chen.mongodb.repository;

import com.chen.mongodb.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description: <br>
 * @Date: Created in 2019/12/17 <br>
 * @Author: chenjianwen
 */
@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
}
