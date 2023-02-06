package org.example.service;

import org.example.model.StackoverflowResponse;
import org.example.model.Tag;
import org.example.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StackoverflowService {
    @GET("/2.3/users"
            + "?pagesize=100"
            + "&order=desc"
            + "&min=223"
            + "&sort=reputation"
            + "&filter=!LnNkvqQBDbWWONPMmhaT-7"
            + "&site=stackoverflow")
    Call<StackoverflowResponse<User>> getUsers(@Query("page") Integer page);

    @GET("/2.3/users/{id}/tags"
            + "?pagesize=100"
            + "&order=desc"
            + "&sort=popular"
            + "&filter=!-.DiW2eRF3oO"
            + "&site=stackoverflow")
    Call<StackoverflowResponse<Tag>> getTagsByUser(@Path("id") Long id,
                                                   @Query("page") Integer page);
}
