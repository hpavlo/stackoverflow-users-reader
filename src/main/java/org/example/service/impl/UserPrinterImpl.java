package org.example.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.example.model.Tag;
import org.example.model.User;
import org.example.service.UserPrinter;

public class UserPrinterImpl implements UserPrinter {
    public static final String RESET_COLOR = "\033[0m";
    public static final String GREEN = "\033[0;32m";
    public static final String GREEN_BOLD = "\033[1;32m";
    public static final String CYAN = "\033[0;36m";

    @Override
    public void print(Map<User, List<Tag>> usersWithTags) {
        for (Map.Entry<User, List<Tag>> entry : usersWithTags.entrySet()) {
            User user = entry.getKey();
            String tags = entry.getValue()
                    .stream()
                    .map(Tag::getName)
                    .collect(Collectors.joining(", "));
            System.out.println(
                    "\nUser name: " + GREEN_BOLD + user.getDisplayName() + RESET_COLOR
                    + "\nLocation: " + GREEN + user.getLocation() + RESET_COLOR
                    + "\nAnswer count: " + GREEN + user.getAnswerCount() + RESET_COLOR
                    + "\nQuestion count: " + GREEN + user.getQuestionCount() + RESET_COLOR
                    + "\nTop tags: " + GREEN + tags + RESET_COLOR
                    + "\nLink to profile: " + CYAN + user.getLink() + RESET_COLOR
                    + "\nLink to avatar: " + CYAN + user.getProfileImage() + RESET_COLOR);
        }
    }
}
