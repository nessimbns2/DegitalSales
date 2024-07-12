package in.pfe.elearning.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import in.pfe.elearning.entity.Category;


public interface CategoryRepository extends MongoRepository<Category, String> {
}
