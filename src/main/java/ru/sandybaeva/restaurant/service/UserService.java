package ru.sandybaeva.restaurant.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.sandybaeva.restaurant.AuthorizedUser;
import ru.sandybaeva.restaurant.model.User;
import ru.sandybaeva.restaurant.repository.UserRepository;
import ru.sandybaeva.restaurant.util.exception.DuplicateDataException;

import static ru.sandybaeva.restaurant.util.UserUtil.prepareToSave;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        if (userRepository.getByEmail(user.getEmail()) != null) {
            throw new DuplicateDataException("User with this email already exists");
        }
        return prepareAndSave(user);
    }

    private User prepareAndSave(User user) {
        return userRepository.save(prepareToSave(user, passwordEncoder));
    }

    public User getById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
