package net.yafw.forum.service;

import net.yafw.forum.dataStore.PostJPARepository;
import net.yafw.forum.model.BaseModel;
import net.yafw.forum.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PostService {

    @Autowired
    PostJPARepository postRepository;

    public BaseModel<Post> getPostById(String userName, UUID postId)  {
        boolean isSuccess = false;
        List<String> errorMessageList = new ArrayList<>();
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = null;
        if(optionalPost.isEmpty()) {
            errorMessageList.add("Post Not Found: "+postId);
        }
        else if(!PostPermissionHelper.isUserAllowedToViewPost(userName,optionalPost.get())){
            errorMessageList.add("User: "+userName+" is not allowed to view post");
        }
        else{
            post = optionalPost.get();
            errorMessageList.add("Successful");
            isSuccess = true;
        }
        return new BaseModel<Post>(isSuccess,errorMessageList,
                post, Calendar.getInstance().getTime().toString()
        );
    }

    public BaseModel<Post> updatePostById(String userName, UUID postId, Post postToUpdate) {
        boolean isSuccess = false;
        List<String> errorMessageList = new ArrayList<>();
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = null;
        if(optionalPost.isEmpty()) {
            errorMessageList.add("Post Not Found: "+postId);
        }
        else if(!PostPermissionHelper.isUserAllowedToViewPost(userName,optionalPost.get())){
            errorMessageList.add("User: "+userName+" is not allowed to update post");
        }
        else{
            Post existingPost = optionalPost.get();
            post = new Post(existingPost.getId(),postToUpdate.getTitle(),postToUpdate.getDescription(),
                    postToUpdate.getTags(),existingPost.getAuthor(),existingPost.getUpVotes(),existingPost.getDownVotes());
            post = postRepository.save(post);
        }
        return new BaseModel<Post>(isSuccess,errorMessageList,
                post, Calendar.getInstance().getTime().toString()
        );
    }

    public BaseModel<String> deletePostById(String userName, UUID postId) {
        boolean isSuccess = false;
        List<String> errorMessageList = new ArrayList<>();
        Optional<Post> optionalPost = postRepository.findById(postId);
        String message = "";
        if(optionalPost.isEmpty()) {
            errorMessageList.add("Post Not Found: "+postId);
        }
        else if(!PostPermissionHelper.isUserAllowedToViewPost(userName,optionalPost.get())){
            errorMessageList.add("User: "+userName+" is not allowed to delete post");
        }
        else{
            postRepository.deleteById(postId);
            message = "Post deleted successfully";
        }
        return new BaseModel<String>(isSuccess,errorMessageList,
                message, Calendar.getInstance().getTime().toString()
        );
    }
}
