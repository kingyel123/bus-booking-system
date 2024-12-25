package com.java.busbookingsystem.auth.controller;



import com.java.busbookingsystem.auth.model.AuthRequest;
import com.java.busbookingsystem.auth.service.AuthService;
import com.java.busbookingsystem.email.dto.ForgotPasswordRequest;
import com.java.busbookingsystem.email.dto.ResetPasswordRequest;
import com.java.busbookingsystem.email.service.PasswordResetService;
import com.java.busbookingsystem.users.model.User;
import com.java.busbookingsystem.users.service.UserServiceImpl;
import com.java.busbookingsystem.utils.RestHelper;
import com.java.busbookingsystem.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordResetService passwordResetService;

    /**
     * Handles the authentication for the user provided credentials.
     *
     * @param authRequest The authentication credentials containing object
     * @return The access keys and refresh keys for the associated authenticated user.
     */
    @PostMapping("/login")
    public ResponseEntity<RestResponse> login(@RequestBody AuthRequest authRequest) {
        Map<String, Object> listHashMap = new HashMap<>(authService.authenticate(authRequest));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Handles token refresh using a valid refresh token
     *
     * @param authorizationHeader Headers with Authorization keyword
     * @return New access and refresh tokens
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<RestResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        // Extract token from Bearer authorization header
        String refreshToken = authorizationHeader.substring(7); // Remove "Bearer "
        Map<String, Object> tokenMap = new HashMap<>(authService.refreshToken(refreshToken));
        return RestHelper.responseSuccess(tokenMap);
    }

    /**
     * Signing up the new user.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userService.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Sends the password reset link to the concerned email.
     *
     * @param request The password request body containing the email of the user whose password is to be reset.
     * @return The confirmation that the password reset link has been sent.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<RestResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String message = passwordResetService.forgotPassword(request);
        return RestHelper.responseMessage(message);
    }

    /**
     * Resets the password from the provided token and the password.
     *
     * @param resetPasswordRequest The reset password request containing the jwt token and the password.
     * @return The message indicating that the password has been reset successfully.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<RestResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String message = passwordResetService.resetPassword(resetPasswordRequest);
        return RestHelper.responseMessage(message);
    }
}

