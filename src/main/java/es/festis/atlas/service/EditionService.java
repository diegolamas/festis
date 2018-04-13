package es.festis.atlas.service;

import es.festis.atlas.domain.Edition;
import es.festis.atlas.repository.EditionRepository;
import es.festis.atlas.service.dto.EditionDTO;
import es.festis.atlas.service.mapper.EditionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Edition.
 */
@Service
@Transactional
public class EditionService {

    private final Logger log = LoggerFactory.getLogger(EditionService.class);

    private final EditionRepository editionRepository;

    private final EditionMapper editionMapper;

    public EditionService(EditionRepository editionRepository, EditionMapper editionMapper) {
        this.editionRepository = editionRepository;
        this.editionMapper = editionMapper;
    }

    /**
     * Save a edition.
     *
     * @param editionDTO the entity to save
     * @return the persisted entity
     */
    public EditionDTO save(EditionDTO editionDTO) {
        log.debug("Request to save Edition : {}", editionDTO);
        Edition edition = editionMapper.toEntity(editionDTO);
        edition = editionRepository.save(edition);
        return editionMapper.toDto(edition);
    }

    /**
     * Get all the editions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EditionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Editions");
        return editionRepository.findAll(pageable)
            .map(editionMapper::toDto);
    }

    /**
     * Get one edition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public EditionDTO findOne(Long id) {
        log.debug("Request to get Edition : {}", id);
        Edition edition = editionRepository.findOne(id);
        return editionMapper.toDto(edition);
    }

    /**
     * Delete the edition by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Edition : {}", id);
        editionRepository.delete(id);
    }
}
