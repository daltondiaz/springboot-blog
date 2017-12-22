package com.dalton.post;

import com.dalton.tag.Tag;
import com.dalton.user.User;
import com.dalton.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dalton on 22/12/17.
 */

@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/posts")
    public List<Post> findAll(){
        User user = userRepository.findOne(1l);
        return postRepository.findByUserAndStatusOrderByCreationDateDesc(user, true);
    }

    @PostMapping("/post/tag")
    public List<Post> findAllByTag(@RequestBody Tag tag){
        return postRepository.findByTagsAndStatusOrderByCreationDateDesc(tag, true);
    }

    @GetMapping("/post")
    public Post findOne(@PathVariable Long id){
        return postRepository.findOne(id);
    }

    @PostMapping("/post")
    public Post save(@RequestBody Post post){
        return postService.save(post);
    }

    @PutMapping("/post")
    public Post update(@RequestBody Post post) throws Exception{
        // TODO edit only the own post and test post when happened some error
        return postService.update(post);
    }

    @DeleteMapping("post/{id}")
    public boolean delete(@PathVariable Long id){
        return postService.delete(id);
    }


}
