package org.example.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    @SerializedName("user_id")
    private Long userId;
    @SerializedName("answer_count")
    private Integer answerCount;
    @SerializedName("question_count")
    private Integer questionCount;
    private String location;
    private String link;
    @SerializedName("profile_image")
    private String profileImage;
    @SerializedName("display_name")
    private String displayName;
}
