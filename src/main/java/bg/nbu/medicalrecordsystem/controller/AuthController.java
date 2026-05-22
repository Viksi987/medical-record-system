package bg.nbu.medicalrecordsystem.controller;

import bg.nbu.medicalrecordsystem.entity.User;
import bg.nbu.medicalrecordsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return userService.saveUser(user);
    }
}