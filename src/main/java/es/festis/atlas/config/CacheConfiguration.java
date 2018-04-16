package es.festis.atlas.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(es.festis.atlas.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(es.festis.atlas.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Festival.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Edition.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Edition.class.getName() + ".attendants", jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Edition.class.getName() + ".announcements", jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Edition.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Artist.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Artist.class.getName() + ".followers", jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Artist.class.getName() + ".announcements", jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Announcement.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Announcement.class.getName() + ".artists", jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Follow.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.UserExtra.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.Attend.class.getName(), jcacheConfiguration);
            cm.createCache(es.festis.atlas.domain.EntityAuditEvent.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
