package net.yafw.forum.service;

import net.yafw.forum.model.Post;

public class PostPermissionHelper {

    public static boolean isUserAllowedToViewPost(String userName, Post post){
        return post.getAuthor().getUserName().equals(userName);
    }

}
