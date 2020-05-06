package com.example.backend.repos.dialog;

import com.example.backend.domain.dialog.Dialog;
import com.example.backend.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DialogRepo extends CrudRepository<Dialog, Long> {
    public Dialog findByUserAndFriend(User user, User friend);
    public Dialog findByUserIdAndFriendId(Long userId, Long friendId);

    public List<Dialog> findByUserIdOrderByDialogIndexDesc(Long dialogIndex);

}
