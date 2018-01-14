package com.dalton.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dalton on 22/12/17.
 */

@RestController
@RequestMapping("/api/v1/")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @GetMapping("tags")
    public List<Tag> findAllTags(){
        return tagRepository.findAll();
    }

    @GetMapping("tag/{id}")
    public Tag findOne(@PathVariable Long id){
        return tagRepository.findOne(id);
    }

    @PostMapping("tag")
    public Tag save(@RequestBody Tag tag){
        return tagRepository.save(tag);
    }

    @PutMapping("tag")
    public Tag update(@RequestBody Tag tag){
        return tagRepository.save(tag);
    }

    @DeleteMapping("tag/{id}")
    public Tag delete(@PathVariable Long id){
        return tagService.delete(id);
    }



}
