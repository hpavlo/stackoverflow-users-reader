package org.example.service;

import org.example.model.StackoverflowResponse;
import org.example.model.Tag;
import org.example.model.User;

public interface RequestService {
    StackoverflowResponse<User> getUsers(int page);

    StackoverflowResponse<Tag> getTagsByUser(long id, int page);
}
