package com.ecse428.project.repository;

import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.Cocktail;
import com.ecse428.project.model.Modifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    @Query("SELECT c FROM Cocktail c WHERE UPPER(c.name) LIKE UPPER(concat('%', ?1,'%'))")
    List<Cocktail> findByNameContaining(String cocktailName);

    @Query(value = "SELECT c FROM Cocktail c WHERE UPPER(c.name) LIKE UPPER(concat('%', ?1,'%'))" +
            "AND (?2 MEMBER OF c.alcohols OR ?2 IS null) AND (?3 MEMBER OF c.modifiers OR (?3) IS null) AND (?4 MEMBER OF c.tasteTypes OR (?4) IS null) AND " +
            "(?5 = c.strengthType OR (?5) IS null) AND (?6 = c.servingSize OR (?6) IS null)")
    List<Cocktail> findByParams(String cName, Alcohol alcohol, Modifier modifier, Cocktail.TasteType tasteType,
                                Cocktail.StrengthType strengthType, Cocktail.ServingSize servingSize);
}