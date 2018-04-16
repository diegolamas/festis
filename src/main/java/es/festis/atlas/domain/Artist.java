package es.festis.atlas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Artist.
 */
@Entity
@Table(name = "artist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Artist extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "preview_url")
    private String previewUrl;

    @Column(name = "web")
    private String web;

    @Column(name = "spotify")
    private String spotify;

    @Column(name = "googlemusic")
    private String googlemusic;

    @Column(name = "applemusic")
    private String applemusic;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "youtube")
    private String youtube;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "popularity")
    private Long popularity;

    @OneToMany(mappedBy = "artist")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Follow> followers = new HashSet<>();

    @ManyToMany(mappedBy = "artists")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Announcement> announcements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Artist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public Artist previewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
        return this;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getWeb() {
        return web;
    }

    public Artist web(String web) {
        this.web = web;
        return this;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getSpotify() {
        return spotify;
    }

    public Artist spotify(String spotify) {
        this.spotify = spotify;
        return this;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    public String getGooglemusic() {
        return googlemusic;
    }

    public Artist googlemusic(String googlemusic) {
        this.googlemusic = googlemusic;
        return this;
    }

    public void setGooglemusic(String googlemusic) {
        this.googlemusic = googlemusic;
    }

    public String getApplemusic() {
        return applemusic;
    }

    public Artist applemusic(String applemusic) {
        this.applemusic = applemusic;
        return this;
    }

    public void setApplemusic(String applemusic) {
        this.applemusic = applemusic;
    }

    public String getFacebook() {
        return facebook;
    }

    public Artist facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public Artist twitter(String twitter) {
        this.twitter = twitter;
        return this;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public Artist instagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getYoutube() {
        return youtube;
    }

    public Artist youtube(String youtube) {
        this.youtube = youtube;
        return this;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Artist imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getPopularity() {
        return popularity;
    }

    public Artist popularity(Long popularity) {
        this.popularity = popularity;
        return this;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public Set<Follow> getFollowers() {
        return followers;
    }

    public Artist followers(Set<Follow> follows) {
        this.followers = follows;
        return this;
    }

    public Artist addFollower(Follow follow) {
        this.followers.add(follow);
        follow.setArtist(this);
        return this;
    }

    public Artist removeFollower(Follow follow) {
        this.followers.remove(follow);
        follow.setArtist(null);
        return this;
    }

    public void setFollowers(Set<Follow> follows) {
        this.followers = follows;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public Artist announcements(Set<Announcement> announcements) {
        this.announcements = announcements;
        return this;
    }

    public Artist addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);
        announcement.getArtists().add(this);
        return this;
    }

    public Artist removeAnnouncement(Announcement announcement) {
        this.announcements.remove(announcement);
        announcement.getArtists().remove(this);
        return this;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Artist artist = (Artist) o;
        if (artist.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), artist.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Artist{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", previewUrl='" + getPreviewUrl() + "'" +
            ", web='" + getWeb() + "'" +
            ", spotify='" + getSpotify() + "'" +
            ", googlemusic='" + getGooglemusic() + "'" +
            ", applemusic='" + getApplemusic() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", twitter='" + getTwitter() + "'" +
            ", instagram='" + getInstagram() + "'" +
            ", youtube='" + getYoutube() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", popularity=" + getPopularity() +
            "}";
    }
}
