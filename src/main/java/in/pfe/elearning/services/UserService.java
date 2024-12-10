package in.pfe.elearning.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pfe.elearning.entity.Client;
import in.pfe.elearning.entity.User;
import in.pfe.elearning.repository.UserRepository;
import java.util.UUID;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }

    public void addClient(String userID, Client client) {
        Optional<User> userOptional = userRepository.findById(userID);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with ID: " + userID);
        } else {
            User user = userOptional.get();
            List<Client> clients = user.getClients();
            for (Client c : clients) {
                if (c.getTel().equals(client.getTel())) {
                    throw new RuntimeException("Client already exists with the phone number: " + client.getTel());
                }
            }
            client.setId(UUID.randomUUID().toString());
            clients.add(client);
            userRepository.save(user);
        }
    }

    public void deleteClient(String userID, String clientID) {
        Optional<User> userOptional = userRepository.findById(userID);
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with ID: " + userID);
        } else {
            User user = userOptional.get();
            List<Client> clients = user.getClients();
            clients.removeIf(client -> client.getId().equals(clientID));
            userRepository.save(user);
        }
    }
}
