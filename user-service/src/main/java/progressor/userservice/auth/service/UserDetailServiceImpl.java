package progressor.userservice.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import progressor.userservice.auth.repository.UserAuthRepository;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
