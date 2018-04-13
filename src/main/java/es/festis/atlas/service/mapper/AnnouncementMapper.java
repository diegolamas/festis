package es.festis.atlas.service.mapper;

import es.festis.atlas.domain.*;
import es.festis.atlas.service.dto.AnnouncementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Announcement and its DTO AnnouncementDTO.
 */
@Mapper(componentModel = "spring", uses = {ArtistMapper.class, EditionMapper.class})
public interface AnnouncementMapper extends EntityMapper<AnnouncementDTO, Announcement> {

    @Mapping(source = "edition.id", target = "editionId")
    AnnouncementDTO toDto(Announcement announcement);

    @Mapping(source = "editionId", target = "edition")
    Announcement toEntity(AnnouncementDTO announcementDTO);

    default Announcement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Announcement announcement = new Announcement();
        announcement.setId(id);
        return announcement;
    }
}
