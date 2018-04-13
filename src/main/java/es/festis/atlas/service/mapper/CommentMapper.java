package es.festis.atlas.service.mapper;

import es.festis.atlas.domain.*;
import es.festis.atlas.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {EditionMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "edition.id", target = "editionId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "editionId", target = "edition")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
