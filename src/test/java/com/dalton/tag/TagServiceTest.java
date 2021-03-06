package com.dalton.tag;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by dalton on 13/01/18.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @MockBean
    private TagRepository tagRepository;

    @Before
    public void setup() {

        Tag tag = new Tag();
        tag.setName("tag");
        tag.setDescription("new tag");
        tag.setId(1l);
        tag.setStatus(true);
        tag.setCreationDate(new Date());

        when(this.tagRepository.findOne(1l)).thenReturn(tag);

    }

    @Test
    public void deleteTest(){
        Tag tagDeleted = tagService.delete(1l);
        assertEquals((long) 1, (long)tagDeleted.getId());
        assertEquals(true, !tagDeleted.isStatus());
    }

}
