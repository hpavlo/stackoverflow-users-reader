package org.example.service;

import java.util.List;
import java.util.Map;
import org.example.model.Tag;
import org.example.model.User;

public interface UserService {
    Map<User, List<Tag>> findFilteredUsers();
}
