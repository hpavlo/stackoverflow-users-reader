package org.example;

import java.util.List;
import java.util.Map;
import org.example.model.Tag;
import org.example.model.User;
import org.example.service.RequestService;
import org.example.service.UserPrinter;
import org.example.service.UserService;
import org.example.service.impl.RequestServiceImpl;
import org.example.service.impl.UserPrinterImpl;
import org.example.service.impl.UserServiceImpl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {
    private static final String API_LINK = "https://api.stackexchange.com/";

    public static void main(String[] args) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestService requestService = new RequestServiceImpl(retrofit);
        UserService userService = new UserServiceImpl(requestService);
        Map<User, List<Tag>> usersWithTags = userService.findFilteredUsers();

        UserPrinter userPrinter = new UserPrinterImpl();
        userPrinter.print(usersWithTags);
    }
}
