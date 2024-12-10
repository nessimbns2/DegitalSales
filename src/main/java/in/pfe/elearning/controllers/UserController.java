
package in.pfe.elearning.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import in.pfe.elearning.entity.Client;
import in.pfe.elearning.services.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

        @Autowired
        private UserService userService;    

        @PostMapping("/{userID}/clients")
        public ResponseEntity<String> addClient(@PathVariable String userID, @RequestBody Client client) {
            try {
                userService.addClient(userID, client);
                return new ResponseEntity<>("Client added successfully", HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }        


        @DeleteMapping("/{userID}/clients/{clientID}")
        public ResponseEntity<String> deleteClient(@PathVariable String userID, @PathVariable String clientID) {
            try {
                userService.deleteClient(userID, clientID);
                return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        }
}
