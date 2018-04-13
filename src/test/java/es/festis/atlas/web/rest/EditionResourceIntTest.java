package es.festis.atlas.web.rest;

import es.festis.atlas.FestisApp;

import es.festis.atlas.domain.Edition;
import es.festis.atlas.domain.Festival;
import es.festis.atlas.repository.EditionRepository;
import es.festis.atlas.service.EditionService;
import es.festis.atlas.service.dto.EditionDTO;
import es.festis.atlas.service.mapper.EditionMapper;
import es.festis.atlas.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static es.festis.atlas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EditionResource REST controller.
 *
 * @see EditionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FestisApp.class)
public class EditionResourceIntTest {

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_HAS_CAMPING = false;
    private static final Boolean UPDATED_HAS_CAMPING = true;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final byte[] DEFAULT_COVER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COVER = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_COVER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COVER_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_POSTER = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_POSTER = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_POSTER_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_POSTER_CONTENT_TYPE = "image/png";

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private EditionMapper editionMapper;

    @Autowired
    private EditionService editionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEditionMockMvc;

    private Edition edition;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EditionResource editionResource = new EditionResource(editionService);
        this.restEditionMockMvc = MockMvcBuilders.standaloneSetup(editionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Edition createEntity(EntityManager em) {
        Edition edition = new Edition()
            .location(DEFAULT_LOCATION)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .hasCamping(DEFAULT_HAS_CAMPING)
            .price(DEFAULT_PRICE)
            .cover(DEFAULT_COVER)
            .coverContentType(DEFAULT_COVER_CONTENT_TYPE)
            .poster(DEFAULT_POSTER)
            .posterContentType(DEFAULT_POSTER_CONTENT_TYPE);
        // Add required entity
        Festival festival = FestivalResourceIntTest.createEntity(em);
        em.persist(festival);
        em.flush();
        edition.setFestival(festival);
        return edition;
    }

    @Before
    public void initTest() {
        edition = createEntity(em);
    }

    @Test
    @Transactional
    public void createEdition() throws Exception {
        int databaseSizeBeforeCreate = editionRepository.findAll().size();

        // Create the Edition
        EditionDTO editionDTO = editionMapper.toDto(edition);
        restEditionMockMvc.perform(post("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isCreated());

        // Validate the Edition in the database
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeCreate + 1);
        Edition testEdition = editionList.get(editionList.size() - 1);
        assertThat(testEdition.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testEdition.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEdition.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEdition.isHasCamping()).isEqualTo(DEFAULT_HAS_CAMPING);
        assertThat(testEdition.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testEdition.getCover()).isEqualTo(DEFAULT_COVER);
        assertThat(testEdition.getCoverContentType()).isEqualTo(DEFAULT_COVER_CONTENT_TYPE);
        assertThat(testEdition.getPoster()).isEqualTo(DEFAULT_POSTER);
        assertThat(testEdition.getPosterContentType()).isEqualTo(DEFAULT_POSTER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEditionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = editionRepository.findAll().size();

        // Create the Edition with an existing ID
        edition.setId(1L);
        EditionDTO editionDTO = editionMapper.toDto(edition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEditionMockMvc.perform(post("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Edition in the database
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = editionRepository.findAll().size();
        // set the field null
        edition.setLocation(null);

        // Create the Edition, which fails.
        EditionDTO editionDTO = editionMapper.toDto(edition);

        restEditionMockMvc.perform(post("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isBadRequest());

        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = editionRepository.findAll().size();
        // set the field null
        edition.setStartDate(null);

        // Create the Edition, which fails.
        EditionDTO editionDTO = editionMapper.toDto(edition);

        restEditionMockMvc.perform(post("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isBadRequest());

        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = editionRepository.findAll().size();
        // set the field null
        edition.setEndDate(null);

        // Create the Edition, which fails.
        EditionDTO editionDTO = editionMapper.toDto(edition);

        restEditionMockMvc.perform(post("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isBadRequest());

        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHasCampingIsRequired() throws Exception {
        int databaseSizeBeforeTest = editionRepository.findAll().size();
        // set the field null
        edition.setHasCamping(null);

        // Create the Edition, which fails.
        EditionDTO editionDTO = editionMapper.toDto(edition);

        restEditionMockMvc.perform(post("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isBadRequest());

        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEditions() throws Exception {
        // Initialize the database
        editionRepository.saveAndFlush(edition);

        // Get all the editionList
        restEditionMockMvc.perform(get("/api/editions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(edition.getId().intValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].hasCamping").value(hasItem(DEFAULT_HAS_CAMPING.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].coverContentType").value(hasItem(DEFAULT_COVER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cover").value(hasItem(Base64Utils.encodeToString(DEFAULT_COVER))))
            .andExpect(jsonPath("$.[*].posterContentType").value(hasItem(DEFAULT_POSTER_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].poster").value(hasItem(Base64Utils.encodeToString(DEFAULT_POSTER))));
    }

    @Test
    @Transactional
    public void getEdition() throws Exception {
        // Initialize the database
        editionRepository.saveAndFlush(edition);

        // Get the edition
        restEditionMockMvc.perform(get("/api/editions/{id}", edition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(edition.getId().intValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.hasCamping").value(DEFAULT_HAS_CAMPING.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.coverContentType").value(DEFAULT_COVER_CONTENT_TYPE))
            .andExpect(jsonPath("$.cover").value(Base64Utils.encodeToString(DEFAULT_COVER)))
            .andExpect(jsonPath("$.posterContentType").value(DEFAULT_POSTER_CONTENT_TYPE))
            .andExpect(jsonPath("$.poster").value(Base64Utils.encodeToString(DEFAULT_POSTER)));
    }

    @Test
    @Transactional
    public void getNonExistingEdition() throws Exception {
        // Get the edition
        restEditionMockMvc.perform(get("/api/editions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEdition() throws Exception {
        // Initialize the database
        editionRepository.saveAndFlush(edition);
        int databaseSizeBeforeUpdate = editionRepository.findAll().size();

        // Update the edition
        Edition updatedEdition = editionRepository.findOne(edition.getId());
        // Disconnect from session so that the updates on updatedEdition are not directly saved in db
        em.detach(updatedEdition);
        updatedEdition
            .location(UPDATED_LOCATION)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .hasCamping(UPDATED_HAS_CAMPING)
            .price(UPDATED_PRICE)
            .cover(UPDATED_COVER)
            .coverContentType(UPDATED_COVER_CONTENT_TYPE)
            .poster(UPDATED_POSTER)
            .posterContentType(UPDATED_POSTER_CONTENT_TYPE);
        EditionDTO editionDTO = editionMapper.toDto(updatedEdition);

        restEditionMockMvc.perform(put("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isOk());

        // Validate the Edition in the database
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeUpdate);
        Edition testEdition = editionList.get(editionList.size() - 1);
        assertThat(testEdition.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testEdition.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEdition.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEdition.isHasCamping()).isEqualTo(UPDATED_HAS_CAMPING);
        assertThat(testEdition.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testEdition.getCover()).isEqualTo(UPDATED_COVER);
        assertThat(testEdition.getCoverContentType()).isEqualTo(UPDATED_COVER_CONTENT_TYPE);
        assertThat(testEdition.getPoster()).isEqualTo(UPDATED_POSTER);
        assertThat(testEdition.getPosterContentType()).isEqualTo(UPDATED_POSTER_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEdition() throws Exception {
        int databaseSizeBeforeUpdate = editionRepository.findAll().size();

        // Create the Edition
        EditionDTO editionDTO = editionMapper.toDto(edition);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEditionMockMvc.perform(put("/api/editions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(editionDTO)))
            .andExpect(status().isCreated());

        // Validate the Edition in the database
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEdition() throws Exception {
        // Initialize the database
        editionRepository.saveAndFlush(edition);
        int databaseSizeBeforeDelete = editionRepository.findAll().size();

        // Get the edition
        restEditionMockMvc.perform(delete("/api/editions/{id}", edition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Edition> editionList = editionRepository.findAll();
        assertThat(editionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Edition.class);
        Edition edition1 = new Edition();
        edition1.setId(1L);
        Edition edition2 = new Edition();
        edition2.setId(edition1.getId());
        assertThat(edition1).isEqualTo(edition2);
        edition2.setId(2L);
        assertThat(edition1).isNotEqualTo(edition2);
        edition1.setId(null);
        assertThat(edition1).isNotEqualTo(edition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EditionDTO.class);
        EditionDTO editionDTO1 = new EditionDTO();
        editionDTO1.setId(1L);
        EditionDTO editionDTO2 = new EditionDTO();
        assertThat(editionDTO1).isNotEqualTo(editionDTO2);
        editionDTO2.setId(editionDTO1.getId());
        assertThat(editionDTO1).isEqualTo(editionDTO2);
        editionDTO2.setId(2L);
        assertThat(editionDTO1).isNotEqualTo(editionDTO2);
        editionDTO1.setId(null);
        assertThat(editionDTO1).isNotEqualTo(editionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(editionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(editionMapper.fromId(null)).isNull();
    }
}
