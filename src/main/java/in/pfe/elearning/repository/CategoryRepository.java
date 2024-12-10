package in.pfe.elearning.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import in.pfe.elearning.entity.Category;


public interface CategoryRepository extends MongoRepository<Category, String> {

    List<Category> findByUserId(String userId);
}
