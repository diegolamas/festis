package es.festis.atlas.service.mapper;

import es.festis.atlas.domain.*;
import es.festis.atlas.service.dto.FollowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Follow and its DTO FollowDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ArtistMapper.class})
public interface FollowMapper extends EntityMapper<FollowDTO, Follow> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "artist.id", target = "artistId")
    @Mapping(source = "artist.name", target = "artistName")
    FollowDTO toDto(Follow follow);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "artistId", target = "artist")
    Follow toEntity(FollowDTO followDTO);

    default Follow fromId(Long id) {
        if (id == null) {
            return null;
        }
        Follow follow = new Follow();
        follow.setId(id);
        return follow;
    }
}
