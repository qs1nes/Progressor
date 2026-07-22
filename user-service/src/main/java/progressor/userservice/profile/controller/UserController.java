package progressor.userservice.profile.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progressor.userservice.profile.service.UserProfileService;

@RestController
@RequestMapping("/profile")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    public final UserProfileService userProfileService;


}
