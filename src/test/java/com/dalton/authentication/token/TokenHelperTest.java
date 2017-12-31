package com.dalton.authentication.token;

import com.dalton.common.TimeProvider;
import com.dalton.security.TokenHelper;
import com.dalton.utils.DeviceDummy;
import com.dalton.utils.TokenUtils;
import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mobile.device.Device;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by dalton on 24/12/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TokenHelperTest {

    private static final String TEST_USERNAME = "testUser";

    @InjectMocks
    private TokenHelper tokenHelper;

    @Mock
    private TimeProvider timeProviderMock;

    @InjectMocks
    private DeviceDummy deviceDummy;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenHelper, TokenUtils.EXPIRES_IN, 10);
        ReflectionTestUtils.setField(tokenHelper, TokenUtils.MOBILE_EXPIRES_IN, 30);
        ReflectionTestUtils.setField(tokenHelper, TokenUtils.SECRET, "secretTest");
    }

    @Test
    public void getUsernameFromToken(){
        final Date now = DateUtil.now();
        when(timeProviderMock.now()).thenReturn(DateUtil.now());

        final String token = createToken(deviceDummy);

        assertThat(tokenHelper.getIssuedAtDateFromToken(token)).isInSameMinuteWindowAs(now);
    }

    private String createToken(Device device){
        return tokenHelper.generateToken(TEST_USERNAME, device);
    }
}
