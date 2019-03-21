package pl.prodzajto.estolowkabackend.user.block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserBlockController {
    @Autowired
    private UserBlockService userBlockService;

    public UserBlockController(UserBlockService userBlockService) {
        this.userBlockService = userBlockService;
    }

    @GetMapping("/getuserstoblock")
    public Page<UserMapper> getPageWithUsers(@RequestParam int page, @RequestParam int size) {
        return userBlockService.getPageWithUsers(page, size);
    }
}
