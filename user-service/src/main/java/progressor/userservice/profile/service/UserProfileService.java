package progressor.userservice.profile.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import progressor.userservice.profile.dto.request.ProfileRequest;
import progressor.userservice.profile.dto.response.ProfileResponse;
import progressor.userservice.profile.entity.UserProfile;
import progressor.userservice.auth.entity.UserAuth;
import progressor.userservice.common.exception.created_excpetion.NicknameAlreadyExistsException;
import progressor.userservice.common.exception.created_excpetion.UserProfileNotFoundException;
import progressor.userservice.auth.repository.UserAuthRepository;
import progressor.userservice.profile.repository.UserProfileRepository;

@Service
@AllArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserAuthRepository userAuthRepository;


    @Transactional
    public UserProfile createDefaultProfile(UserAuth userAuth) {

        UserProfile profile = UserProfile.builder()
                .nickname(userAuth.getUsername())
                .avatar("picture.png")
                .statusMessage("New user in our community!")
                .level(1)
                .bossesDefeated(0)
                .questsCompleted(0)
                .build();

        profile.setUser(userAuth);
        userAuth.setProfile(profile);

        log.info("Default profile created for '{}'", userAuth.getUsername());

        return profile;
    }

    public ProfileResponse GetProfile(){
        UserProfile profile = getCurrentUserProfile();

        log.info("Get profile for '{}'", profile.getNickname());
        return map(profile);
    }

    public ProfileResponse getProfileByNickname(String nickname) throws Exception {
        UserProfile profile = userProfileRepository.findByNickname(nickname)
                .orElseThrow(() -> {
                    log.warn("Profile '{}' not found", nickname);
                    return new UserProfileNotFoundException("Profile not found");
                });

        log.info("Get profile for '{}' in getProfileByNickname", profile.getNickname());
        return map(profile);
    }



    public ProfileResponse getProfileById(Long id) throws Exception {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Profile id={} not found", id);
                    return new UserProfileNotFoundException("Profile not found");
                });

        log.info("Get profile for '{}' in getProfileById", profile.getNickname());
        return map(profile);
    }



    @Transactional
    public ProfileResponse updateProfile(Long userId, ProfileRequest request) throws Exception {
        UserProfile profile = getCurrentUserProfile();

        if (!profile.getNickname().equals(request.nickname())
                && userProfileRepository.existsByNickname(request.nickname())) {

            throw new NicknameAlreadyExistsException(
                    "Nickname already exists"
            );
        }

        profile.setNickname(request.nickname());
        profile.setAvatar(request.avatar());
        profile.setStatusMessage(request.statusMessage());

        log.info("Profile '{}' updated", profile.getNickname());

        return map(profile);
    }

    public void updateStatusMessage(String statusMessage) throws Exception {
        UserProfile profile = getCurrentUserProfile();

        UserProfile user = userProfileRepository.findByNickname(profile.getNickname())
                .orElseThrow(() -> {
                    log.warn("UserProfile by {} not found!", profile.getNickname());
                    return new UserProfileNotFoundException("UserProfile not found");
                });

        user.setStatusMessage(statusMessage);
    }

    public boolean nicknameExists(String nickname) throws Exception {
        return userProfileRepository.existsByNickname(nickname);
    }

//=========================================

    private UserProfile getCurrentUserProfile() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UserAuth user = userAuthRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Authenticated user '{}' not found", username);
                    return new UsernameNotFoundException("User not found");
                });

        if (user.getProfile() == null) {
            throw new UserProfileNotFoundException("Profile not found");
        }

        return user.getProfile();
    }

    private ProfileResponse map(UserProfile profile) {

        return new ProfileResponse(
                profile.getNickname(),
                profile.getAvatar(),
                profile.getStatusMessage(),
                profile.getLevel(),
                profile.getBossesDefeated(),
                profile.getQuestsCompleted()
        );
    }

}
