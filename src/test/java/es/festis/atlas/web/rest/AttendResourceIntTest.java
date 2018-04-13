package es.festis.atlas.web.rest;

import es.festis.atlas.FestisApp;

import es.festis.atlas.domain.Attend;
import es.festis.atlas.domain.User;
import es.festis.atlas.domain.Edition;
import es.festis.atlas.repository.AttendRepository;
import es.festis.atlas.service.AttendService;
import es.festis.atlas.service.dto.AttendDTO;
import es.festis.atlas.service.mapper.AttendMapper;
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
 * Test class for the AttendResource REST controller.
 *
 * @see AttendResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FestisApp.class)
public class AttendResourceIntTest {

    @Autowired
    private AttendRepository attendRepository;

    @Autowired
    private AttendMapper attendMapper;

    @Autowired
    private AttendService attendService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAttendMockMvc;

    private Attend attend;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttendResource attendResource = new AttendResource(attendService);
        this.restAttendMockMvc = MockMvcBuilders.standaloneSetup(attendResource)
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
    public static Attend createEntity(EntityManager em) {
        Attend attend = new Attend();
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        attend.setUser(user);
        // Add required entity
        Edition edition = EditionResourceIntTest.createEntity(em);
        em.persist(edition);
        em.flush();
        attend.setEdition(edition);
        return attend;
    }

    @Before
    public void initTest() {
        attend = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttend() throws Exception {
        int databaseSizeBeforeCreate = attendRepository.findAll().size();

        // Create the Attend
        AttendDTO attendDTO = attendMapper.toDto(attend);
        restAttendMockMvc.perform(post("/api/attends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendDTO)))
            .andExpect(status().isCreated());

        // Validate the Attend in the database
        List<Attend> attendList = attendRepository.findAll();
        assertThat(attendList).hasSize(databaseSizeBeforeCreate + 1);
        Attend testAttend = attendList.get(attendList.size() - 1);
    }

    @Test
    @Transactional
    public void createAttendWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendRepository.findAll().size();

        // Create the Attend with an existing ID
        attend.setId(1L);
        AttendDTO attendDTO = attendMapper.toDto(attend);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendMockMvc.perform(post("/api/attends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attend in the database
        List<Attend> attendList = attendRepository.findAll();
        assertThat(attendList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAttends() throws Exception {
        // Initialize the database
        attendRepository.saveAndFlush(attend);

        // Get all the attendList
        restAttendMockMvc.perform(get("/api/attends?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attend.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAttend() throws Exception {
        // Initialize the database
        attendRepository.saveAndFlush(attend);

        // Get the attend
        restAttendMockMvc.perform(get("/api/attends/{id}", attend.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attend.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAttend() throws Exception {
        // Get the attend
        restAttendMockMvc.perform(get("/api/attends/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttend() throws Exception {
        // Initialize the database
        attendRepository.saveAndFlush(attend);
        int databaseSizeBeforeUpdate = attendRepository.findAll().size();

        // Update the attend
        Attend updatedAttend = attendRepository.findOne(attend.getId());
        // Disconnect from session so that the updates on updatedAttend are not directly saved in db
        em.detach(updatedAttend);
        AttendDTO attendDTO = attendMapper.toDto(updatedAttend);

        restAttendMockMvc.perform(put("/api/attends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendDTO)))
            .andExpect(status().isOk());

        // Validate the Attend in the database
        List<Attend> attendList = attendRepository.findAll();
        assertThat(attendList).hasSize(databaseSizeBeforeUpdate);
        Attend testAttend = attendList.get(attendList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAttend() throws Exception {
        int databaseSizeBeforeUpdate = attendRepository.findAll().size();

        // Create the Attend
        AttendDTO attendDTO = attendMapper.toDto(attend);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAttendMockMvc.perform(put("/api/attends")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendDTO)))
            .andExpect(status().isCreated());

        // Validate the Attend in the database
        List<Attend> attendList = attendRepository.findAll();
        assertThat(attendList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAttend() throws Exception {
        // Initialize the database
        attendRepository.saveAndFlush(attend);
        int databaseSizeBeforeDelete = attendRepository.findAll().size();

        // Get the attend
        restAttendMockMvc.perform(delete("/api/attends/{id}", attend.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Attend> attendList = attendRepository.findAll();
        assertThat(attendList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attend.class);
        Attend attend1 = new Attend();
        attend1.setId(1L);
        Attend attend2 = new Attend();
        attend2.setId(attend1.getId());
        assertThat(attend1).isEqualTo(attend2);
        attend2.setId(2L);
        assertThat(attend1).isNotEqualTo(attend2);
        attend1.setId(null);
        assertThat(attend1).isNotEqualTo(attend2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendDTO.class);
        AttendDTO attendDTO1 = new AttendDTO();
        attendDTO1.setId(1L);
        AttendDTO attendDTO2 = new AttendDTO();
        assertThat(attendDTO1).isNotEqualTo(attendDTO2);
        attendDTO2.setId(attendDTO1.getId());
        assertThat(attendDTO1).isEqualTo(attendDTO2);
        attendDTO2.setId(2L);
        assertThat(attendDTO1).isNotEqualTo(attendDTO2);
        attendDTO1.setId(null);
        assertThat(attendDTO1).isNotEqualTo(attendDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(attendMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(attendMapper.fromId(null)).isNull();
    }
}
