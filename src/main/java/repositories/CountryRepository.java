package repositories;
import models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{
    @Query("SELECT c.countryId FROM Country c WHERE c.countryName = ?1")
    Integer findByName(String countryName); // Changed from int to Integer
}
