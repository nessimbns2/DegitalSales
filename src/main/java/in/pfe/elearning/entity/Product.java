package in.pfe.elearning.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "Product")
public class Product {
    @Id
    private String id;
    private String name;
    private String image;
    private String description;
    private double price;
    private int quantity;   
    @DBRef
    private Category category;
    private String userId;
}
