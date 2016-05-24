package com.giaquino.sample.ui.users.view;

import com.giaquino.sample.model.entity.User;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino.
 */
public interface UsersView {

    void setUsers(List<User> users);

    void showError(String message);
}
