package es.festis.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.festis.atlas.domain.Festival;

import es.festis.atlas.repository.FestivalRepository;
import es.festis.atlas.web.rest.errors.BadRequestAlertException;
import es.festis.atlas.web.rest.util.HeaderUtil;
import es.festis.atlas.web.rest.util.PaginationUtil;
import es.festis.atlas.service.dto.FestivalDTO;
import es.festis.atlas.service.mapper.FestivalMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Festival.
 */
@RestController
@RequestMapping("/api")
public class FestivalResource {

    private final Logger log = LoggerFactory.getLogger(FestivalResource.class);

    private static final String ENTITY_NAME = "festival";

    private final FestivalRepository festivalRepository;

    private final FestivalMapper festivalMapper;

    public FestivalResource(FestivalRepository festivalRepository, FestivalMapper festivalMapper) {
        this.festivalRepository = festivalRepository;
        this.festivalMapper = festivalMapper;
    }

    /**
     * POST  /festivals : Create a new festival.
     *
     * @param festivalDTO the festivalDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new festivalDTO, or with status 400 (Bad Request) if the festival has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/festivals")
    @Timed
    public ResponseEntity<FestivalDTO> createFestival(@Valid @RequestBody FestivalDTO festivalDTO) throws URISyntaxException {
        log.debug("REST request to save Festival : {}", festivalDTO);
        if (festivalDTO.getId() != null) {
            throw new BadRequestAlertException("A new festival cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Festival festival = festivalMapper.toEntity(festivalDTO);
        festival = festivalRepository.save(festival);
        FestivalDTO result = festivalMapper.toDto(festival);
        return ResponseEntity.created(new URI("/api/festivals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /festivals : Updates an existing festival.
     *
     * @param festivalDTO the festivalDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated festivalDTO,
     * or with status 400 (Bad Request) if the festivalDTO is not valid,
     * or with status 500 (Internal Server Error) if the festivalDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/festivals")
    @Timed
    public ResponseEntity<FestivalDTO> updateFestival(@Valid @RequestBody FestivalDTO festivalDTO) throws URISyntaxException {
        log.debug("REST request to update Festival : {}", festivalDTO);
        if (festivalDTO.getId() == null) {
            return createFestival(festivalDTO);
        }
        Festival festival = festivalMapper.toEntity(festivalDTO);
        festival = festivalRepository.save(festival);
        FestivalDTO result = festivalMapper.toDto(festival);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, festivalDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /festivals : get all the festivals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of festivals in body
     */
    @GetMapping("/festivals")
    @Timed
    public ResponseEntity<List<FestivalDTO>> getAllFestivals(Pageable pageable) {
        log.debug("REST request to get a page of Festivals");
        Page<Festival> page = festivalRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/festivals");
        return new ResponseEntity<>(festivalMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /festivals/:id : get the "id" festival.
     *
     * @param id the id of the festivalDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the festivalDTO, or with status 404 (Not Found)
     */
    @GetMapping("/festivals/{id}")
    @Timed
    public ResponseEntity<FestivalDTO> getFestival(@PathVariable Long id) {
        log.debug("REST request to get Festival : {}", id);
        Festival festival = festivalRepository.findOne(id);
        FestivalDTO festivalDTO = festivalMapper.toDto(festival);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(festivalDTO));
    }

    /**
     * DELETE  /festivals/:id : delete the "id" festival.
     *
     * @param id the id of the festivalDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/festivals/{id}")
    @Timed
    public ResponseEntity<Void> deleteFestival(@PathVariable Long id) {
        log.debug("REST request to delete Festival : {}", id);
        festivalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
