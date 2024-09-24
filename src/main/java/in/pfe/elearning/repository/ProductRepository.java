package in.pfe.elearning.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import in.pfe.elearning.entity.Product;


public interface ProductRepository extends MongoRepository<Product, String> {
    

    List<Product> findByUserId(String userId);
}
