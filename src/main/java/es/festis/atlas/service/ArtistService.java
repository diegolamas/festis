package es.festis.atlas.service;

import es.festis.atlas.domain.Artist;
import es.festis.atlas.repository.ArtistRepository;
import es.festis.atlas.service.dto.ArtistDTO;
import es.festis.atlas.service.mapper.ArtistMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Artist.
 */
@Service
@Transactional
public class ArtistService {

    private final Logger log = LoggerFactory.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    public ArtistService(ArtistRepository artistRepository, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    /**
     * Save a artist.
     *
     * @param artistDTO the entity to save
     * @return the persisted entity
     */
    public ArtistDTO save(ArtistDTO artistDTO) {
        log.debug("Request to save Artist : {}", artistDTO);
        Artist artist = artistMapper.toEntity(artistDTO);
        artist = artistRepository.save(artist);
        return artistMapper.toDto(artist);
    }

    /**
     * Get all the artists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ArtistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Artists");
        return artistRepository.findAll(pageable)
            .map(artistMapper::toDto);
    }

    /**
     * Get one artist by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ArtistDTO findOne(Long id) {
        log.debug("Request to get Artist : {}", id);
        Artist artist = artistRepository.findOne(id);
        return artistMapper.toDto(artist);
    }

    /**
     * Delete the artist by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Artist : {}", id);
        artistRepository.delete(id);
    }
}
