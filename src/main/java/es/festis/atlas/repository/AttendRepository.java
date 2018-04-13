package es.festis.atlas.repository;

import es.festis.atlas.domain.Attend;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Attend entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendRepository extends JpaRepository<Attend, Long> {

    @Query("select attend from Attend attend where attend.user.login = ?#{principal.username}")
    List<Attend> findByUserIsCurrentUser();

}
