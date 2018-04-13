package es.festis.atlas.repository;

import es.festis.atlas.domain.Edition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Edition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EditionRepository extends JpaRepository<Edition, Long> {

}
