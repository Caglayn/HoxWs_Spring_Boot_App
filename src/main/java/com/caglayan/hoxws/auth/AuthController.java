package com.caglayan.hoxws.auth;

import com.caglayan.hoxws.error.ApiError;
import com.caglayan.hoxws.shared.Views;
import com.caglayan.hoxws.user.User;
import com.caglayan.hoxws.user.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/1.0/auth")
    @JsonView(Views.Base.class)
    public ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization", required = false) String authorization){
        if (authorization == null) {
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        String base64encoded = authorization.split("Basic ")[1];
        String decoded = new String(Base64.getDecoder().decode(base64encoded));
        String[] parts = decoded.split(":");
        String username = parts[0];
        String password = parts[1];
        User inDB = userService.findByUsername(username);
        if (inDB == null){
            ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        else{
            if (!passwordEncoder.matches(password, inDB.getPassword())){
                ApiError error = new ApiError(401, "Unauthorized request", "/api/1.0/auth");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }
            else {
//                Map<String, String> responseBody = new HashMap<>();
//                responseBody.put("username", inDB.getUsername());
//                responseBody.put("displayName", inDB.getDisplayName());
//                responseBody.put("image", inDB.getImage());
                return ResponseEntity.ok(inDB);
            }
        }
    }
}
