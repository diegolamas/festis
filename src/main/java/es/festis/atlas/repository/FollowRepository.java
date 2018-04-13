package es.festis.atlas.repository;

import es.festis.atlas.domain.Follow;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Follow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Query("select follow from Follow follow where follow.user.login = ?#{principal.username}")
    List<Follow> findByUserIsCurrentUser();

}
