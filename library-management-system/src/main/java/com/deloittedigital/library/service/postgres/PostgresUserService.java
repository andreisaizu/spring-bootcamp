package com.deloittedigital.library.service.postgres;

import com.deloittedigital.library.exception.EmailAlreadyUsedException;
import com.deloittedigital.library.model.domain.User;
import com.deloittedigital.library.repository.UserRepository;
import com.deloittedigital.library.service.IUserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Profile("postgres")
public class PostgresUserService implements IUserService {

    private final UserRepository userRepository;

    public PostgresUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userRepository.deleteAll();
        this.saveUsers();
    }


    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User add(User user) {
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        return userRepository.save(user);
    }

    private List<User> saveUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setFirstName("Ioana");
        user1.setLastName("Trandafir");
        user1.setEmail("itrandafir@deloittece.com");
        user1.setTitle("Mrs.");
        user1.setMobilePhone("+40772305435");
        users.add(user1);
        User user2 = new User();
        user2.setFirstName("Alex");
        user2.setLastName("Petrescu");
        user2.setEmail("apetrescu@deloittece.com");
        user2.setTitle("Mr.");
        user2.setMobilePhone("+407723535884");
        users.add(user2);
        return userRepository.saveAll(users);
    }

}
