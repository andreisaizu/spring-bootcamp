package com.deloittedigital.library.service.local;

import com.deloittedigital.library.exception.EmailAlreadyUsedException;
import com.deloittedigital.library.model.domain.User;
import com.deloittedigital.library.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocalUserService implements IUserService {

    private List<User> users = initUsers();


    public Optional<User> get(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public List<User> getAll() {
        return users;
    }

    public User add(User user) {
        if (users.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new EmailAlreadyUsedException();
        }
        user.setId(users.size() + 1L);
        users.add(user);

        return user;
    }


    private static List<User> initUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Ioana");
        user1.setLastName("Trandafir");
        user1.setEmail("itrandafir@deloittece.com");
        user1.setTitle("Mrs.");
        user1.setMobilePhone("+40772305435");
        users.add(user1);
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Alex");
        user2.setLastName("Petrescu");
        user2.setEmail("apetrescu@deloittece.com");
        user2.setTitle("Mr.");
        user2.setMobilePhone("+407723535884");
        users.add(user2);
        return users;
    }

}
