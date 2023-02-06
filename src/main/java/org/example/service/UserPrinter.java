package org.example.service;

import java.util.List;
import java.util.Map;
import org.example.model.Tag;
import org.example.model.User;

public interface UserPrinter {
    void print(Map<User, List<Tag>> usersWithTags);
}
