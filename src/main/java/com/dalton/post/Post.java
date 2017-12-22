package com.dalton.post;

import com.dalton.tag.Tag;
import com.dalton.user.User;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by dalton on 22/12/17.
 */

@Entity
@Table(name = "blog_post")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String title;
    private boolean status;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Tag> tags;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "update_date")
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getCreationDateFormat(){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(this.creationDate);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
