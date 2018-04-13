package es.festis.atlas.service.mapper;

import es.festis.atlas.domain.*;
import es.festis.atlas.service.dto.AttendDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Attend and its DTO AttendDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EditionMapper.class})
public interface AttendMapper extends EntityMapper<AttendDTO, Attend> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "edition.id", target = "editionId")
    AttendDTO toDto(Attend attend);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "editionId", target = "edition")
    Attend toEntity(AttendDTO attendDTO);

    default Attend fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attend attend = new Attend();
        attend.setId(id);
        return attend;
    }
}
