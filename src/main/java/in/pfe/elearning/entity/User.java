package in.pfe.elearning.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection="Users")
public class User {
    @Id
    protected String id;
    protected String firstname;
    protected String lastname;
    private List<Client> clients ;
    @Indexed(unique = true)
    protected String email;
    protected String password;
    protected String tel;
    @DBRef
    protected List<Role> roles;

    
    public User(String Firstname, String Lastname, String email, String password,String tel) {
        this.firstname = Firstname;
        this.lastname = Lastname;
        this.email = email;
        this.password = password;
        this.tel=tel;
    }
}
