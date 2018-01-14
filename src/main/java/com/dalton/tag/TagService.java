package com.dalton.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by dalton on 22/12/17.
 */

@Component
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    private Logger log = LoggerFactory.getLogger(TagService.class);

    public Tag delete(Long id){
        try{
            Tag tag = tagRepository.findOne(id);
            tag.setStatus(false);
            return tagRepository.save(tag);

        }catch(IllegalArgumentException e){
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public void update(Tag tag){
        try {
            tag.setUpdateDate(new Date());
            tagRepository.save(tag);
        }catch (Exception e){
            log.error(e.getLocalizedMessage(), e);
        }
    }
}
