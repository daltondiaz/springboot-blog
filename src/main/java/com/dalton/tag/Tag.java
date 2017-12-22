package com.dalton.tag;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dalton on 22/12/17.
 */
@Entity
@Table(name = "blog_tag")
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String name;
    private boolean status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
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
