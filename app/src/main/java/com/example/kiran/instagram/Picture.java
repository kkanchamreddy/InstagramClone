package com.example.kiran.instagram;

/**
 * Created by kiran on 12/1/15.
 */
public class Picture {
    public String imageUrl;
    public String userName;
    public String caption;
    public String likeCount;
    public String userProfilePic;

    public Picture(String imageUrl, String userName, String caption, String likeCount) {

        this.imageUrl = imageUrl;
        this.userName = userName;
        this.caption = caption;
        this.likeCount = likeCount;
    }

    public void setUserProfilePicture(String profilePic) {
       this.userProfilePic = profilePic;
    }

}
