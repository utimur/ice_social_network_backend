package com.example.backend.repos.dialog;

import com.example.backend.domain.dialog.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {
    public List<Message> findMessagesByDialogId(Long dialogId);
}
