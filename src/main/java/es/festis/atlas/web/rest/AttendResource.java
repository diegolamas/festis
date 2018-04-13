package es.festis.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.festis.atlas.service.AttendService;
import es.festis.atlas.web.rest.errors.BadRequestAlertException;
import es.festis.atlas.web.rest.util.HeaderUtil;
import es.festis.atlas.web.rest.util.PaginationUtil;
import es.festis.atlas.service.dto.AttendDTO;
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
 * REST controller for managing Attend.
 */
@RestController
@RequestMapping("/api")
public class AttendResource {

    private final Logger log = LoggerFactory.getLogger(AttendResource.class);

    private static final String ENTITY_NAME = "attend";

    private final AttendService attendService;

    public AttendResource(AttendService attendService) {
        this.attendService = attendService;
    }

    /**
     * POST  /attends : Create a new attend.
     *
     * @param attendDTO the attendDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attendDTO, or with status 400 (Bad Request) if the attend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attends")
    @Timed
    public ResponseEntity<AttendDTO> createAttend(@Valid @RequestBody AttendDTO attendDTO) throws URISyntaxException {
        log.debug("REST request to save Attend : {}", attendDTO);
        if (attendDTO.getId() != null) {
            throw new BadRequestAlertException("A new attend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendDTO result = attendService.save(attendDTO);
        return ResponseEntity.created(new URI("/api/attends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attends : Updates an existing attend.
     *
     * @param attendDTO the attendDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attendDTO,
     * or with status 400 (Bad Request) if the attendDTO is not valid,
     * or with status 500 (Internal Server Error) if the attendDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attends")
    @Timed
    public ResponseEntity<AttendDTO> updateAttend(@Valid @RequestBody AttendDTO attendDTO) throws URISyntaxException {
        log.debug("REST request to update Attend : {}", attendDTO);
        if (attendDTO.getId() == null) {
            return createAttend(attendDTO);
        }
        AttendDTO result = attendService.save(attendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attends : get all the attends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of attends in body
     */
    @GetMapping("/attends")
    @Timed
    public ResponseEntity<List<AttendDTO>> getAllAttends(Pageable pageable) {
        log.debug("REST request to get a page of Attends");
        Page<AttendDTO> page = attendService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /attends/:id : get the "id" attend.
     *
     * @param id the id of the attendDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attendDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attends/{id}")
    @Timed
    public ResponseEntity<AttendDTO> getAttend(@PathVariable Long id) {
        log.debug("REST request to get Attend : {}", id);
        AttendDTO attendDTO = attendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(attendDTO));
    }

    /**
     * DELETE  /attends/:id : delete the "id" attend.
     *
     * @param id the id of the attendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attends/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttend(@PathVariable Long id) {
        log.debug("REST request to delete Attend : {}", id);
        attendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
