package pl.prodzajto.estolowkabackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> getUsers(@PageableDefault(sort = "surname") Pageable pageable) {
        return userService.findUsers(pageable);
    }

    @PostMapping(value = "/status")
    public void changeUserStatus(@RequestBody @NotBlank String email) {
        userService.changeUserStatus(email);
    }

    @PostMapping(value = "/change/password")
    public void changePassword(@RequestBody Map<String, String> params) {
        userService.changePassword(params.get("email"), params.get("oldPassword"), params.get("newPassword"));
    }

}
