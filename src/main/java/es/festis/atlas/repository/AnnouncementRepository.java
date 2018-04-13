package es.festis.atlas.repository;

import es.festis.atlas.domain.Announcement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Announcement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query("select distinct announcement from Announcement announcement left join fetch announcement.artists")
    List<Announcement> findAllWithEagerRelationships();

    @Query("select announcement from Announcement announcement left join fetch announcement.artists where announcement.id =:id")
    Announcement findOneWithEagerRelationships(@Param("id") Long id);

}
