package es.festis.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.festis.atlas.domain.Follow;

import es.festis.atlas.repository.FollowRepository;
import es.festis.atlas.web.rest.errors.BadRequestAlertException;
import es.festis.atlas.web.rest.util.HeaderUtil;
import es.festis.atlas.web.rest.util.PaginationUtil;
import es.festis.atlas.service.dto.FollowDTO;
import es.festis.atlas.service.mapper.FollowMapper;
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
 * REST controller for managing Follow.
 */
@RestController
@RequestMapping("/api")
public class FollowResource {

    private final Logger log = LoggerFactory.getLogger(FollowResource.class);

    private static final String ENTITY_NAME = "follow";

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    public FollowResource(FollowRepository followRepository, FollowMapper followMapper) {
        this.followRepository = followRepository;
        this.followMapper = followMapper;
    }

    /**
     * POST  /follows : Create a new follow.
     *
     * @param followDTO the followDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new followDTO, or with status 400 (Bad Request) if the follow has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/follows")
    @Timed
    public ResponseEntity<FollowDTO> createFollow(@Valid @RequestBody FollowDTO followDTO) throws URISyntaxException {
        log.debug("REST request to save Follow : {}", followDTO);
        if (followDTO.getId() != null) {
            throw new BadRequestAlertException("A new follow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Follow follow = followMapper.toEntity(followDTO);
        follow = followRepository.save(follow);
        FollowDTO result = followMapper.toDto(follow);
        return ResponseEntity.created(new URI("/api/follows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /follows : Updates an existing follow.
     *
     * @param followDTO the followDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated followDTO,
     * or with status 400 (Bad Request) if the followDTO is not valid,
     * or with status 500 (Internal Server Error) if the followDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/follows")
    @Timed
    public ResponseEntity<FollowDTO> updateFollow(@Valid @RequestBody FollowDTO followDTO) throws URISyntaxException {
        log.debug("REST request to update Follow : {}", followDTO);
        if (followDTO.getId() == null) {
            return createFollow(followDTO);
        }
        Follow follow = followMapper.toEntity(followDTO);
        follow = followRepository.save(follow);
        FollowDTO result = followMapper.toDto(follow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, followDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /follows : get all the follows.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of follows in body
     */
    @GetMapping("/follows")
    @Timed
    public ResponseEntity<List<FollowDTO>> getAllFollows(Pageable pageable) {
        log.debug("REST request to get a page of Follows");
        Page<Follow> page = followRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/follows");
        return new ResponseEntity<>(followMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /follows/:id : get the "id" follow.
     *
     * @param id the id of the followDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the followDTO, or with status 404 (Not Found)
     */
    @GetMapping("/follows/{id}")
    @Timed
    public ResponseEntity<FollowDTO> getFollow(@PathVariable Long id) {
        log.debug("REST request to get Follow : {}", id);
        Follow follow = followRepository.findOne(id);
        FollowDTO followDTO = followMapper.toDto(follow);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(followDTO));
    }

    /**
     * DELETE  /follows/:id : delete the "id" follow.
     *
     * @param id the id of the followDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/follows/{id}")
    @Timed
    public ResponseEntity<Void> deleteFollow(@PathVariable Long id) {
        log.debug("REST request to delete Follow : {}", id);
        followRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
