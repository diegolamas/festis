package es.festis.atlas.service.mapper;

import es.festis.atlas.domain.*;
import es.festis.atlas.service.dto.FestivalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Festival and its DTO FestivalDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FestivalMapper extends EntityMapper<FestivalDTO, Festival> {



    default Festival fromId(Long id) {
        if (id == null) {
            return null;
        }
        Festival festival = new Festival();
        festival.setId(id);
        return festival;
    }
}
