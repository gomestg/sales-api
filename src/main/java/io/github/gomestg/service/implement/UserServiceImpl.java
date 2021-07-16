package io.github.gomestg.service.implement;

import io.github.gomestg.domain.entity.Users;
import io.github.gomestg.domain.repository.UserRepository;
import io.github.gomestg.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    public Users save(Users user){
        return repository.save(user);
    }

    public UserDetails authentic(Users user){
        UserDetails userDetails = loadUserByUsername(user.getLogin());
        boolean equalsPassword = encoder.matches(user.getPassword(), userDetails.getPassword());
        if (equalsPassword){
            return userDetails;
        }
        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users user = repository.findByLogin(userName).orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));

        String[] roles = user.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }
}
