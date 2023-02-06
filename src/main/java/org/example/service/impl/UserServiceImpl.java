package org.example.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;
import org.example.model.StackoverflowResponse;
import org.example.model.Tag;
import org.example.model.User;
import org.example.service.RequestService;
import org.example.service.UserService;

public class UserServiceImpl implements UserService {
    private static final String[] ALLOWED_COUNTRIES = new String[] {"Romania", "Moldova"};
    private static final List<String> ALLOWED_TAGS = List.of("java", ".net", "docker", "C#");
    private static final int MIN_ANSWER_COUNT = 1;
    private static final int MIN_PAGE_NUMBER = 1;
    private static final int MAX_PAGE_NUMBER = 25;
    private static final int MAX_USER_NUMBER = 2500;
    private static final int TOP_TAGS_NUMBER = 6;
    private static final int SLEEP_TIME = 50;
    private final RequestService requestService;
    private Map<User, List<Tag>> filteredUsersWithTags;

    public UserServiceImpl(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public Map<User, List<Tag>> findFilteredUsers() {
        filteredUsersWithTags = new HashMap<>();
        try (ProgressBar progressBar = createProgressBar()) {
            sendUserRequests(progressBar);
        } catch (InterruptedException e) {
            throw new RuntimeException("Can't find users by criteria", e);
        }
        return filteredUsersWithTags;
    }

    private void sendUserRequests(ProgressBar progressBar) throws InterruptedException {
        for (int page = MIN_PAGE_NUMBER; page <= MAX_PAGE_NUMBER; page++) {
            StackoverflowResponse<User> usersResponse = requestService.getUsers(page);
            List<User> users = usersResponse.getItems();
            for (User user : users) {
                if (checkUser(user)) {
                    checkUserTags(user);
                }
                progressBar.step();
            }
            Thread.sleep(SLEEP_TIME);
        }
    }

    private boolean checkUser(User user) {
        return user.getLocation() != null
                && user.getAnswerCount() != null
                && Arrays.stream(ALLOWED_COUNTRIES)
                    .anyMatch(user.getLocation()::contains)
                && user.getAnswerCount() > MIN_ANSWER_COUNT;
    }

    private void checkUserTags(User user) throws InterruptedException {
        int tagsPage = MIN_PAGE_NUMBER;
        List<Tag> topTags = new ArrayList<>(TOP_TAGS_NUMBER);
        StackoverflowResponse<Tag> tagsResponse;
        do {
            tagsResponse = requestService.getTagsByUser(user.getUserId(), tagsPage);
            List<Tag> tags = tagsResponse.getItems();
            if (tagsPage == MIN_PAGE_NUMBER) {
                topTags.addAll(tags.stream().limit(TOP_TAGS_NUMBER).toList());
            }
            if (tags.stream()
                    .map(Tag::getName)
                    .anyMatch(ALLOWED_TAGS::contains)) {
                filteredUsersWithTags.put(user, topTags);
                break;
            }
            tagsPage++;
            Thread.sleep(SLEEP_TIME);
        } while (tagsResponse.getHasMore() && tagsPage <= MAX_PAGE_NUMBER);
    }

    private ProgressBar createProgressBar() {
        return new ProgressBarBuilder()
                .setInitialMax(MAX_USER_NUMBER)
                .setTaskName("Searching users")
                .setStyle(ProgressBarStyle.ASCII)
                .build();
    }
}
