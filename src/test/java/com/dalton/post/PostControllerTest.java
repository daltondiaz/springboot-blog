package com.dalton.post;

import com.dalton.common.TimeProvider;
import com.dalton.roles.Role;
import com.dalton.security.TokenHelper;
import com.dalton.tag.Tag;
import com.dalton.user.User;
import com.dalton.user.UserRepository;
import com.dalton.user.impl.CustomUserDetailsService;
import com.dalton.utils.DeviceDummy;
import com.dalton.utils.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by dalton on 24/12/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PostControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private DeviceDummy device;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private TimeProvider timeProviderMock;

    @Before
    public void setup(){

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User user = new User();
        user.setUsername("user");
        user.setId(1l);
        Role role = new Role();
        role.setId(0l);
        role.setRoleName("STANDARD_USER");
        List<Role> roles = Arrays.asList(role);
        user.setRoles(roles);
        user.setLastPasswordResetDate(new Timestamp(DateUtil.yesterday().getTime()));

        when(this.userDetailsService.loadUserByUsername("user")).thenReturn(user);
        when(this.userRepository.findOne(1l)).thenReturn(user);

        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenHelper, TokenUtils.EXPIRES_IN,120);
        ReflectionTestUtils.setField(tokenHelper, TokenUtils.MOBILE_EXPIRES_IN, 120);
        ReflectionTestUtils.setField(tokenHelper, TokenUtils.SECRET, "jwtblog");

        device.setTablet(false);
        device.setNormal(false);
        device.setMobile(false);
    }

    @Test
    public void shouldNotPostWithoutValidToken() throws Exception{
        this.mockMvc
                .perform(post("/api/v1/post"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void getAllPost() throws Exception{

        User user = (User) userDetailsService.loadUserByUsername("user");

        List<Post> posts = new ArrayList<Post>();
        Post post = new Post();
        post.setStatus(true);
        post.setTitle("Test");
        post.setId(1l);
        post.setDescription("Test description");
        post.setUser(user);
        post.setCreationDate(new Date());
        posts.add(post);

        post = new Post();
        post.setStatus(true);
        post.setTitle("Test2");
        post.setId(2l);
        post.setDescription("Test description 2");
        post.setUser(user);
        post.setCreationDate(new Date());
        posts.add(post);

        when(this.postRepository.findByUserAndStatusOrderByCreationDateDesc(user, true)).thenReturn(posts);
        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions perform = this.mockMvc
                .perform(get("/api/v1/posts"));

        perform.andExpect(content().json(objectMapper.writeValueAsString(posts)));
    }

    @Test
    public void shouldSavePost() throws Exception{

        User user = (User) userDetailsService.loadUserByUsername("user");

        Post post = new Post();
        //post.setUser(user);
        post.setId(0l);
        post.setTitle("Post test");
        post.setDescription("My new post from a test");
        post.setCreationDate(new Date());
        post.setStatus(true);

        List<Tag> tags = new ArrayList<Tag>();
        Tag tag = new Tag();
        tag.setId(1l);
        tags.add(tag);

        post.setTags(tags);

        ObjectMapper objectMapper = new ObjectMapper();
        String strPost = objectMapper.writeValueAsString(post);

        device.setNormal(true);
        when(timeProviderMock.now()).thenReturn(new Date(DateUtil.now().getTime() - 10 * 1000));
        String token = createToken(device);
        this.mockMvc.perform(post("/api/v1/post")
            .header("Authorization","Bearer "+token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(strPost))
        .andExpect(status().is2xxSuccessful());
    }

    private String createToken(Device device){
        return tokenHelper.generateToken("user", device);
    }


}
