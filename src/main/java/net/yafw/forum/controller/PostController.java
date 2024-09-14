package net.yafw.forum.controller;

import net.yafw.forum.model.BaseModel;
import net.yafw.forum.model.Post;
import net.yafw.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/forum/v1/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping(path = "/{id}")

    public BaseModel<Post> getPost(@RequestHeader(name = "X-username") String userName,
                                   @PathVariable UUID id) {
        return postService.getPostById(userName,id);
    }

    @PutMapping(path = "/{id}")
    public BaseModel<Post> updatePost(@RequestHeader(name = "X-username") String userName,
            @PathVariable UUID postId, @RequestBody Post postToUpdate){
        return postService.updatePostById(userName,postId,postToUpdate);
    }

    @DeleteMapping(path = "/{id}")
    public BaseModel<String> updatePost(@RequestHeader(name = "X-username") String userName,
                           @PathVariable UUID postId){
        return postService.deletePostById(userName,postId);
    }
}
