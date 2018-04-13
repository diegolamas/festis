package es.festis.atlas.repository;

import es.festis.atlas.domain.Festival;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Festival entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long> {

}
