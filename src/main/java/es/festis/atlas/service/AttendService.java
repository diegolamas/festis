package es.festis.atlas.service;

import es.festis.atlas.domain.Attend;
import es.festis.atlas.repository.AttendRepository;
import es.festis.atlas.service.dto.AttendDTO;
import es.festis.atlas.service.mapper.AttendMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Attend.
 */
@Service
@Transactional
public class AttendService {

    private final Logger log = LoggerFactory.getLogger(AttendService.class);

    private final AttendRepository attendRepository;

    private final AttendMapper attendMapper;

    public AttendService(AttendRepository attendRepository, AttendMapper attendMapper) {
        this.attendRepository = attendRepository;
        this.attendMapper = attendMapper;
    }

    /**
     * Save a attend.
     *
     * @param attendDTO the entity to save
     * @return the persisted entity
     */
    public AttendDTO save(AttendDTO attendDTO) {
        log.debug("Request to save Attend : {}", attendDTO);
        Attend attend = attendMapper.toEntity(attendDTO);
        attend = attendRepository.save(attend);
        return attendMapper.toDto(attend);
    }

    /**
     * Get all the attends.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AttendDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attends");
        return attendRepository.findAll(pageable)
            .map(attendMapper::toDto);
    }

    /**
     * Get one attend by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AttendDTO findOne(Long id) {
        log.debug("Request to get Attend : {}", id);
        Attend attend = attendRepository.findOne(id);
        return attendMapper.toDto(attend);
    }

    /**
     * Delete the attend by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Attend : {}", id);
        attendRepository.delete(id);
    }
}
