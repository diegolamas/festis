package es.festis.atlas.service.mapper;

import es.festis.atlas.domain.*;
import es.festis.atlas.service.dto.ArtistDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Artist and its DTO ArtistDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ArtistMapper extends EntityMapper<ArtistDTO, Artist> {


    @Mapping(target = "followers", ignore = true)
    @Mapping(target = "announcements", ignore = true)
    Artist toEntity(ArtistDTO artistDTO);

    default Artist fromId(Long id) {
        if (id == null) {
            return null;
        }
        Artist artist = new Artist();
        artist.setId(id);
        return artist;
    }
}
