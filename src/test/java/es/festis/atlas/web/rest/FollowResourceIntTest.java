package es.festis.atlas.web.rest;

import es.festis.atlas.FestisApp;

import es.festis.atlas.domain.Follow;
import es.festis.atlas.domain.User;
import es.festis.atlas.domain.Artist;
import es.festis.atlas.repository.FollowRepository;
import es.festis.atlas.service.FollowService;
import es.festis.atlas.service.dto.FollowDTO;
import es.festis.atlas.service.mapper.FollowMapper;
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
 * Test class for the FollowResource REST controller.
 *
 * @see FollowResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FestisApp.class)
public class FollowResourceIntTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private FollowService followService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFollowMockMvc;

    private Follow follow;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FollowResource followResource = new FollowResource(followService);
        this.restFollowMockMvc = MockMvcBuilders.standaloneSetup(followResource)
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
    public static Follow createEntity(EntityManager em) {
        Follow follow = new Follow();
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        follow.setUser(user);
        // Add required entity
        Artist artist = ArtistResourceIntTest.createEntity(em);
        em.persist(artist);
        em.flush();
        follow.setArtist(artist);
        return follow;
    }

    @Before
    public void initTest() {
        follow = createEntity(em);
    }

    @Test
    @Transactional
    public void createFollow() throws Exception {
        int databaseSizeBeforeCreate = followRepository.findAll().size();

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);
        restFollowMockMvc.perform(post("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isCreated());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeCreate + 1);
        Follow testFollow = followList.get(followList.size() - 1);
    }

    @Test
    @Transactional
    public void createFollowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = followRepository.findAll().size();

        // Create the Follow with an existing ID
        follow.setId(1L);
        FollowDTO followDTO = followMapper.toDto(follow);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowMockMvc.perform(post("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFollows() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get all the followList
        restFollowMockMvc.perform(get("/api/follows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follow.getId().intValue())));
    }

    @Test
    @Transactional
    public void getFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);

        // Get the follow
        restFollowMockMvc.perform(get("/api/follows/{id}", follow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(follow.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFollow() throws Exception {
        // Get the follow
        restFollowMockMvc.perform(get("/api/follows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);
        int databaseSizeBeforeUpdate = followRepository.findAll().size();

        // Update the follow
        Follow updatedFollow = followRepository.findOne(follow.getId());
        // Disconnect from session so that the updates on updatedFollow are not directly saved in db
        em.detach(updatedFollow);
        FollowDTO followDTO = followMapper.toDto(updatedFollow);

        restFollowMockMvc.perform(put("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isOk());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeUpdate);
        Follow testFollow = followList.get(followList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFollow() throws Exception {
        int databaseSizeBeforeUpdate = followRepository.findAll().size();

        // Create the Follow
        FollowDTO followDTO = followMapper.toDto(follow);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFollowMockMvc.perform(put("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(followDTO)))
            .andExpect(status().isCreated());

        // Validate the Follow in the database
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFollow() throws Exception {
        // Initialize the database
        followRepository.saveAndFlush(follow);
        int databaseSizeBeforeDelete = followRepository.findAll().size();

        // Get the follow
        restFollowMockMvc.perform(delete("/api/follows/{id}", follow.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Follow> followList = followRepository.findAll();
        assertThat(followList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Follow.class);
        Follow follow1 = new Follow();
        follow1.setId(1L);
        Follow follow2 = new Follow();
        follow2.setId(follow1.getId());
        assertThat(follow1).isEqualTo(follow2);
        follow2.setId(2L);
        assertThat(follow1).isNotEqualTo(follow2);
        follow1.setId(null);
        assertThat(follow1).isNotEqualTo(follow2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FollowDTO.class);
        FollowDTO followDTO1 = new FollowDTO();
        followDTO1.setId(1L);
        FollowDTO followDTO2 = new FollowDTO();
        assertThat(followDTO1).isNotEqualTo(followDTO2);
        followDTO2.setId(followDTO1.getId());
        assertThat(followDTO1).isEqualTo(followDTO2);
        followDTO2.setId(2L);
        assertThat(followDTO1).isNotEqualTo(followDTO2);
        followDTO1.setId(null);
        assertThat(followDTO1).isNotEqualTo(followDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(followMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(followMapper.fromId(null)).isNull();
    }
}
