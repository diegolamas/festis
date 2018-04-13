package es.festis.atlas.service.mapper;

import es.festis.atlas.domain.*;
import es.festis.atlas.service.dto.EditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Edition and its DTO EditionDTO.
 */
@Mapper(componentModel = "spring", uses = {FestivalMapper.class})
public interface EditionMapper extends EntityMapper<EditionDTO, Edition> {

    @Mapping(source = "festival.id", target = "festivalId")
    @Mapping(source = "festival.name", target = "festivalName")
    EditionDTO toDto(Edition edition);

    @Mapping(target = "attendants", ignore = true)
    @Mapping(target = "announcements", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(source = "festivalId", target = "festival")
    Edition toEntity(EditionDTO editionDTO);

    default Edition fromId(Long id) {
        if (id == null) {
            return null;
        }
        Edition edition = new Edition();
        edition.setId(id);
        return edition;
    }
}
