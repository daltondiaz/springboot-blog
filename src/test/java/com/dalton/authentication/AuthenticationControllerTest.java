package com.dalton.authentication;

import com.dalton.common.DeviceProvider;
import com.dalton.common.TimeProvider;
import com.dalton.roles.Role;
import com.dalton.security.TokenHelper;
import com.dalton.user.User;
import com.dalton.user.impl.CustomUserDetailsService;
import com.dalton.utils.DeviceDummy;
import com.dalton.utils.TokenUtils;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by dalton on 22/12/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTest {

    private static final String TEST_USERNAME = "user";
    private MockMvc mvc;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private TimeProvider timeProviderMock;

    @Autowired
    private DeviceDummy device;

    @MockBean
    private DeviceProvider deviceProviderMock;



    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User user = new User();
        user.setUsername("user");
        user.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        Role role = new Role();
        role.setId(0l);
        role.setRoleName("STANDARD_USER");
        List<Role> roles = Arrays.asList(role);
        user.setRoles(roles);
        user.setLastPasswordResetDate(new Timestamp(DateUtil.yesterday().getTime()));

        when(this.userDetailsService.loadUserByUsername("user")).thenReturn(user);
        //when(this.customUserDetailsService.loadUserByUsername("author")).thenReturn(user);

        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenHelper, TokenUtils.EXPIRES_IN,100);
        ReflectionTestUtils.setField(tokenHelper, TokenUtils.MOBILE_EXPIRES_IN, 200);
        ReflectionTestUtils.setField(tokenHelper, TokenUtils.SECRET, "jwtblog");

        device.setMobile(false);
        device.setNormal(false);
        device.setTablet(false);
    }

    private String createToken(Device device){
        return tokenHelper.generateToken(TEST_USERNAME, device);
    }

    @Test
    public void shouldGetEmptyTokenStateWhenGivenValidOldToken() throws Exception{
        when(timeProviderMock.now())
                .thenReturn(DateUtil.yesterday());
        this.mvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer 123"))
                .andExpect(content().json("{access_token:null,expires_in:null}"));
    }

    @Test
    public void shouldNotRefreshExpiredWebToken() throws Exception {
        Date beforeSomeTime = new Date(DateUtil.now().getTime() - 15 * 1000);
        when(timeProviderMock.now())
                .thenReturn(beforeSomeTime);
        device.setNormal(true);
        String token = createToken(device);
        this.mvc.perform(post("/auth/refresh")
                .header("Authorization", "Bearer " + token))
                .andExpect(content().json("{access_token:null,expires_in:null}"));
    }


    @Test
    public void shouldLoginWithSuccess() throws Exception {
        device.setNormal(true);
        when(timeProviderMock.now()).thenReturn(DateUtil.now());
        this.mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user\",\"password\":\"123\"}"))
        .andExpect(status().is2xxSuccessful());
    }



}
