package com.dalton.post;

import com.dalton.tag.Tag;
import com.dalton.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dalton on 22/12/17.
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserAndStatusOrderByCreationDateDesc(User user, boolean status);
    List<Post> findByTagsAndStatusOrderByCreationDateDesc(Tag tag, boolean status);
    List<Post> findByStatusOrderByCreationDateDesc(boolean status);

}
