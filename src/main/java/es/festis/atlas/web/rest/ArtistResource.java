package es.festis.atlas.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.festis.atlas.service.ArtistService;
import es.festis.atlas.web.rest.errors.BadRequestAlertException;
import es.festis.atlas.web.rest.util.HeaderUtil;
import es.festis.atlas.web.rest.util.PaginationUtil;
import es.festis.atlas.service.dto.ArtistDTO;
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
 * REST controller for managing Artist.
 */
@RestController
@RequestMapping("/api")
public class ArtistResource {

    private final Logger log = LoggerFactory.getLogger(ArtistResource.class);

    private static final String ENTITY_NAME = "artist";

    private final ArtistService artistService;

    public ArtistResource(ArtistService artistService) {
        this.artistService = artistService;
    }

    /**
     * POST  /artists : Create a new artist.
     *
     * @param artistDTO the artistDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new artistDTO, or with status 400 (Bad Request) if the artist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artists")
    @Timed
    public ResponseEntity<ArtistDTO> createArtist(@Valid @RequestBody ArtistDTO artistDTO) throws URISyntaxException {
        log.debug("REST request to save Artist : {}", artistDTO);
        if (artistDTO.getId() != null) {
            throw new BadRequestAlertException("A new artist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtistDTO result = artistService.save(artistDTO);
        return ResponseEntity.created(new URI("/api/artists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artists : Updates an existing artist.
     *
     * @param artistDTO the artistDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated artistDTO,
     * or with status 400 (Bad Request) if the artistDTO is not valid,
     * or with status 500 (Internal Server Error) if the artistDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artists")
    @Timed
    public ResponseEntity<ArtistDTO> updateArtist(@Valid @RequestBody ArtistDTO artistDTO) throws URISyntaxException {
        log.debug("REST request to update Artist : {}", artistDTO);
        if (artistDTO.getId() == null) {
            return createArtist(artistDTO);
        }
        ArtistDTO result = artistService.save(artistDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, artistDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artists : get all the artists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of artists in body
     */
    @GetMapping("/artists")
    @Timed
    public ResponseEntity<List<ArtistDTO>> getAllArtists(Pageable pageable) {
        log.debug("REST request to get a page of Artists");
        Page<ArtistDTO> page = artistService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/artists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /artists/:id : get the "id" artist.
     *
     * @param id the id of the artistDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the artistDTO, or with status 404 (Not Found)
     */
    @GetMapping("/artists/{id}")
    @Timed
    public ResponseEntity<ArtistDTO> getArtist(@PathVariable Long id) {
        log.debug("REST request to get Artist : {}", id);
        ArtistDTO artistDTO = artistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artistDTO));
    }

    /**
     * DELETE  /artists/:id : delete the "id" artist.
     *
     * @param id the id of the artistDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artists/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        log.debug("REST request to delete Artist : {}", id);
        artistService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
