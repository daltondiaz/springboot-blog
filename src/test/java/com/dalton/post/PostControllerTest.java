package com.dalton.post;

import com.dalton.security.TokenHelper;
import com.dalton.utils.DeviceDummy;
import com.dalton.utils.TokenUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by dalton on 24/12/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private DeviceDummy device;

    @Before
    public void setup(){

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenHelper, TokenUtils.EXPIRES_IN,60);
        ReflectionTestUtils.setField(tokenHelper, TokenUtils.MOBILE_EXPIRES_IN, 120);
        ReflectionTestUtils.setField(tokenHelper, TokenUtils.SECRET, "jwtblog");

        device.setTablet(false);
        device.setNormal(false);
        device.setMobile(false);
    }

    @Test
    public void shouldNotPostWithoutValidToken() throws Exception{
        this.mockMvc
                .perform(post("/api/v1/post")
                        .header("Authorization", "Bearer "))
                .andExpect(status().isUnauthorized());
    }

}
