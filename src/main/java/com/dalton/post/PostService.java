package com.dalton.post;

import com.dalton.tag.Tag;
import com.dalton.tag.TagRepository;
import com.dalton.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dalton on 22/12/17.
 */

@Component
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    private final Logger log = LoggerFactory.getLogger(PostService.class);

    public Post update(Post post) throws Exception{
        try {
            Post newPost = postRepository.findOne(post.getId());
            newPost.setTitle(post.getTitle());
            newPost.setDescription(post.getDescription());
            newPost.setUpdateDate(new Date());
            return  postRepository.save(newPost);
        }catch (Exception e){
            log.error(e.getLocalizedMessage(), e);
            throw e;
        }
    }

    public boolean delete(Long id){
        try {
            Post post = postRepository.findOne(id);
            post.setStatus(false);
            postRepository.save(post);
            return true;
        }catch (Exception e){
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public Post save(Post post) throws Exception{

        try{
            post.setUser(post.getUser());
            post.setCreationDate(new Date());
            post.setStatus(true);

            List<Tag> tags = new ArrayList<Tag>();

            for(Tag tag : post.getTags()){
                if(tag.getId() == 0){
                    // add new tags
                    tag.setCreationDate(new Date());
                    tag.setStatus(true);
                    tags.add(tagRepository.save(tag));
                }else{
                    tags.add(tag);
                }
            }
            post.setTags(tags);
            return postRepository.save(post);
        }catch (NullPointerException n){
            throw n;
        }
    }
}
