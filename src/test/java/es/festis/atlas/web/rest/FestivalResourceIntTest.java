package es.festis.atlas.web.rest;

import es.festis.atlas.FestisApp;

import es.festis.atlas.domain.Festival;
import es.festis.atlas.repository.FestivalRepository;
import es.festis.atlas.service.dto.FestivalDTO;
import es.festis.atlas.service.mapper.FestivalMapper;
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

import javax.persistence.EntityManager;
import java.util.List;

import static es.festis.atlas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FestivalResource REST controller.
 *
 * @see FestivalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FestisApp.class)
public class FestivalResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEB = "AAAAAAAAAA";
    private static final String UPDATED_WEB = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM = "BBBBBBBBBB";

    private static final String DEFAULT_YOUTUBE = "AAAAAAAAAA";
    private static final String UPDATED_YOUTUBE = "BBBBBBBBBB";

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private FestivalMapper festivalMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFestivalMockMvc;

    private Festival festival;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FestivalResource festivalResource = new FestivalResource(festivalRepository, festivalMapper);
        this.restFestivalMockMvc = MockMvcBuilders.standaloneSetup(festivalResource)
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
    public static Festival createEntity(EntityManager em) {
        Festival festival = new Festival()
            .name(DEFAULT_NAME)
            .web(DEFAULT_WEB)
            .facebook(DEFAULT_FACEBOOK)
            .twitter(DEFAULT_TWITTER)
            .instagram(DEFAULT_INSTAGRAM)
            .youtube(DEFAULT_YOUTUBE);
        return festival;
    }

    @Before
    public void initTest() {
        festival = createEntity(em);
    }

    @Test
    @Transactional
    public void createFestival() throws Exception {
        int databaseSizeBeforeCreate = festivalRepository.findAll().size();

        // Create the Festival
        FestivalDTO festivalDTO = festivalMapper.toDto(festival);
        restFestivalMockMvc.perform(post("/api/festivals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(festivalDTO)))
            .andExpect(status().isCreated());

        // Validate the Festival in the database
        List<Festival> festivalList = festivalRepository.findAll();
        assertThat(festivalList).hasSize(databaseSizeBeforeCreate + 1);
        Festival testFestival = festivalList.get(festivalList.size() - 1);
        assertThat(testFestival.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFestival.getWeb()).isEqualTo(DEFAULT_WEB);
        assertThat(testFestival.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testFestival.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testFestival.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testFestival.getYoutube()).isEqualTo(DEFAULT_YOUTUBE);
    }

    @Test
    @Transactional
    public void createFestivalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = festivalRepository.findAll().size();

        // Create the Festival with an existing ID
        festival.setId(1L);
        FestivalDTO festivalDTO = festivalMapper.toDto(festival);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFestivalMockMvc.perform(post("/api/festivals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(festivalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Festival in the database
        List<Festival> festivalList = festivalRepository.findAll();
        assertThat(festivalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = festivalRepository.findAll().size();
        // set the field null
        festival.setName(null);

        // Create the Festival, which fails.
        FestivalDTO festivalDTO = festivalMapper.toDto(festival);

        restFestivalMockMvc.perform(post("/api/festivals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(festivalDTO)))
            .andExpect(status().isBadRequest());

        List<Festival> festivalList = festivalRepository.findAll();
        assertThat(festivalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFestivals() throws Exception {
        // Initialize the database
        festivalRepository.saveAndFlush(festival);

        // Get all the festivalList
        restFestivalMockMvc.perform(get("/api/festivals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(festival.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].web").value(hasItem(DEFAULT_WEB.toString())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.toString())))
            .andExpect(jsonPath("$.[*].youtube").value(hasItem(DEFAULT_YOUTUBE.toString())));
    }

    @Test
    @Transactional
    public void getFestival() throws Exception {
        // Initialize the database
        festivalRepository.saveAndFlush(festival);

        // Get the festival
        restFestivalMockMvc.perform(get("/api/festivals/{id}", festival.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(festival.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.web").value(DEFAULT_WEB.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM.toString()))
            .andExpect(jsonPath("$.youtube").value(DEFAULT_YOUTUBE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFestival() throws Exception {
        // Get the festival
        restFestivalMockMvc.perform(get("/api/festivals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFestival() throws Exception {
        // Initialize the database
        festivalRepository.saveAndFlush(festival);
        int databaseSizeBeforeUpdate = festivalRepository.findAll().size();

        // Update the festival
        Festival updatedFestival = festivalRepository.findOne(festival.getId());
        // Disconnect from session so that the updates on updatedFestival are not directly saved in db
        em.detach(updatedFestival);
        updatedFestival
            .name(UPDATED_NAME)
            .web(UPDATED_WEB)
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .instagram(UPDATED_INSTAGRAM)
            .youtube(UPDATED_YOUTUBE);
        FestivalDTO festivalDTO = festivalMapper.toDto(updatedFestival);

        restFestivalMockMvc.perform(put("/api/festivals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(festivalDTO)))
            .andExpect(status().isOk());

        // Validate the Festival in the database
        List<Festival> festivalList = festivalRepository.findAll();
        assertThat(festivalList).hasSize(databaseSizeBeforeUpdate);
        Festival testFestival = festivalList.get(festivalList.size() - 1);
        assertThat(testFestival.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFestival.getWeb()).isEqualTo(UPDATED_WEB);
        assertThat(testFestival.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testFestival.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testFestival.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testFestival.getYoutube()).isEqualTo(UPDATED_YOUTUBE);
    }

    @Test
    @Transactional
    public void updateNonExistingFestival() throws Exception {
        int databaseSizeBeforeUpdate = festivalRepository.findAll().size();

        // Create the Festival
        FestivalDTO festivalDTO = festivalMapper.toDto(festival);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFestivalMockMvc.perform(put("/api/festivals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(festivalDTO)))
            .andExpect(status().isCreated());

        // Validate the Festival in the database
        List<Festival> festivalList = festivalRepository.findAll();
        assertThat(festivalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFestival() throws Exception {
        // Initialize the database
        festivalRepository.saveAndFlush(festival);
        int databaseSizeBeforeDelete = festivalRepository.findAll().size();

        // Get the festival
        restFestivalMockMvc.perform(delete("/api/festivals/{id}", festival.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Festival> festivalList = festivalRepository.findAll();
        assertThat(festivalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Festival.class);
        Festival festival1 = new Festival();
        festival1.setId(1L);
        Festival festival2 = new Festival();
        festival2.setId(festival1.getId());
        assertThat(festival1).isEqualTo(festival2);
        festival2.setId(2L);
        assertThat(festival1).isNotEqualTo(festival2);
        festival1.setId(null);
        assertThat(festival1).isNotEqualTo(festival2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FestivalDTO.class);
        FestivalDTO festivalDTO1 = new FestivalDTO();
        festivalDTO1.setId(1L);
        FestivalDTO festivalDTO2 = new FestivalDTO();
        assertThat(festivalDTO1).isNotEqualTo(festivalDTO2);
        festivalDTO2.setId(festivalDTO1.getId());
        assertThat(festivalDTO1).isEqualTo(festivalDTO2);
        festivalDTO2.setId(2L);
        assertThat(festivalDTO1).isNotEqualTo(festivalDTO2);
        festivalDTO1.setId(null);
        assertThat(festivalDTO1).isNotEqualTo(festivalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(festivalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(festivalMapper.fromId(null)).isNull();
    }
}
