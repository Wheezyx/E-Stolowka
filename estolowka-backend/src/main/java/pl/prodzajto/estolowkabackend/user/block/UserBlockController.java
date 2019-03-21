package pl.prodzajto.estolowkabackend.user.block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@RestController
public class UserBlockController {
    @Autowired
    private UserBlockService userBlockService;

    public UserBlockController(UserBlockService userBlockService) {
        this.userBlockService = userBlockService;
    }

    @GetMapping("/getuserstoblock")
    public Page<UserMapper> getPageWithUsers(@RequestParam @PageableDefault Pageable pageable) {
        return userBlockService.getPageWithUsers(pageable);
    }

    @PostMapping
    public void blockUser(@RequestBody @NotBlank String email, @RequestBody @NotEmpty boolean isEnabled) {
        userBlockService.blockUser(email, isEnabled);
    }

}
