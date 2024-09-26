
package in.pfe.elearning.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import in.pfe.elearning.entity.Facture;

@Repository
public interface FactureRepository extends MongoRepository<Facture, String> {

    List<Facture> findByUserId(String userId);
}