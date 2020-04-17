package com.example.backend.repos;

import com.example.backend.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {
}
