package com.unn.security;

import java.util.Optional;
import javax.annotation.PostConstruct;
import com.unn.constants.UserTypes;
import com.unn.dto.LoginRequest;
import com.unn.dto.SignupRequest;
import com.unn.model.User;
import com.unn.model.UserType;
import com.unn.repository.UserRepo;
import com.unn.service.impl.UserService;
import com.unn.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityService implements UserDetailsService {
    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    private final PasswordEncoder encoder;

    @PostConstruct
    private void createAdmin() {
        Optional<UserType> adminType = userService.findUserTypeById(UserTypes.ADMIN.getId());
        if (adminType.isEmpty() || userRepo.existsById(1L)) {
            return;
        }

        User admin = new User(adminType.get(), "admin", encoder.encode("admin"), "admin@mail.com");
        admin.setAccountNonExpired(true);
        admin.setAccountNonLocked(true);
        admin.setCredentialsNonExpired(true);
        admin.setEnabled(true);
        admin.setLoggedIn(false);

        log.info("Created admin {}", userRepo.save(admin));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUsername(username);

        if (user.isEmpty()) {
            log.error("User with name {} not found!", username);
            throw new UsernameNotFoundException("User with name " + username + " not found!");
        }

        if (!user.get().isLoggedIn()) {
            throw new BadCredentialsException("User " + username + " is not logged in");
        }

        return user.get().addAuthority(user.get().createAuthority());
    }

    public Optional<User> signup(SignupRequest request) {
        request.setPassword(encoder.encode(request.getPassword()));

        Optional<User> createdUser = userService.createUser(request);
        if (createdUser.isEmpty()) {
            return Optional.empty();
        }

        createdUser.get().setAccountNonLocked(true);
        createdUser.get().setAccountNonExpired(true);
        createdUser.get().setCredentialsNonExpired(true);
        createdUser.get().setEnabled(true);
        createdUser.get().setLoggedIn(false);
        createdUser.get().addAuthority(createdUser.get().createAuthority());

        if (userService.updateUser(createdUser.get()).isPresent()) {
            return createdUser;
        }

        return Optional.empty();
    }

    public Optional<String> login(LoginRequest request) {
        Optional<User> user = userService.findByUsername(request.getUsername());

        if (user.isPresent() && encoder.matches(request.getPassword(), user.get().getPassword())) {
            user.get().setLoggedIn(true);
            userService.updateUser(user.get());

            return Optional.of(Base64Coder.encodeString(request.getUsername() + ":" + request.getPassword()));
        }

        return Optional.empty();
    }

    public Optional<Boolean> logout(Authentication auth) {
        if (AuthUtils.notAuthenticated(auth)) {
            return Optional.of(false);
        }

        Optional<User> user = userService.findByUsername(auth.getName());
        if (user.isEmpty()) {
            return Optional.of(false);
        }

        SecurityContextHolder.getContext().setAuthentication(null);
        user.get().setLoggedIn(false);
        userService.updateUser(user.get());
        return Optional.of(true);
    }

    public Optional<Boolean> isLoggedIn(Authentication auth) {
        if (AuthUtils.notAuthenticated(auth)) {
            return Optional.of(false);
        }

        return Optional.of(
            userRepo.existsByUsername(auth.getName()) && userRepo.findByUsername(auth.getName()).get().isLoggedIn()
        );
    }
}
