package org.example.service.impl;

import java.io.IOException;
import java.util.List;
import org.example.model.StackoverflowResponse;
import org.example.model.Tag;
import org.example.model.User;
import org.example.service.RequestService;
import org.example.service.StackoverflowService;
import retrofit2.Call;
import retrofit2.Retrofit;

public class RequestServiceImpl implements RequestService {
    private final StackoverflowService service;

    public RequestServiceImpl(Retrofit retrofit) {
        this.service = retrofit.create(StackoverflowService.class);
    }

    @Override
    public StackoverflowResponse<User> getUsers(int page) {
        Call<StackoverflowResponse<User>> users = service.getUsers(page);
        try {
            return users.execute().body();
        } catch (IOException e) {
            return emptyResponse();
        }
    }

    @Override
    public StackoverflowResponse<Tag> getTagsByUser(long id, int page) {
        Call<StackoverflowResponse<Tag>> tags = service.getTagsByUser(id, page);
        try {
            return tags.execute().body();
        } catch (IOException e) {
            return emptyResponse();
        }
    }

    private <T> StackoverflowResponse<T> emptyResponse() {
        StackoverflowResponse<T> emptyResponse = new StackoverflowResponse<>();
        emptyResponse.setItems(List.of());
        return emptyResponse;
    }
}
