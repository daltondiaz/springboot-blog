package com.dalton.security.auth;

import com.dalton.common.DeviceProvider;
import com.dalton.user.UserTokenState;
import com.dalton.security.TokenHelper;
import com.dalton.user.impl.CustomUserDetailsService;
import com.dalton.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dalton on 22/12/17.
 */

@RestController
@RequestMapping( value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE )
public class AuthenticationController {

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DeviceProvider deviceProvider;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest,
            HttpServletResponse response,
            Device device
    ) throws AuthenticationException, IOException {

        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        // Inject into security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // token creation
        User user = (User)authentication.getPrincipal();
        String jws = tokenHelper.generateToken( user.getUsername(), device);
        int expiresIn = tokenHelper.getExpiredIn(device);
        // Return the token
      //  response.addHeader("Access-Control-Expose-Headers","Authorization");
       // response.addHeader("Access-Control-Max-Age","600");
        response.addHeader("Authorization", "Bearer "+jws);
        return ResponseEntity.ok(new UserTokenState(jws, expiresIn));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal
            ) {

        String authToken = tokenHelper.getToken( request );

        Device device = deviceProvider.getCurrentDevice(request);

        if (authToken != null && principal != null) {

            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken, device);
            int expiresIn = tokenHelper.getExpiredIn(device);

            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
        Map<String, String> result = new HashMap<>();
        result.put( "result", "success" );
        return ResponseEntity.accepted().body(result);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }
}