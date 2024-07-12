package in.pfe.elearning.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.pfe.elearning.entity.User;
import in.pfe.elearning.repository.UserRepository;


@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
            return user.get();

    }
}
