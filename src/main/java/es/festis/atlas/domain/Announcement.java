package es.festis.atlas.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Announcement.
 */
@Entity
@Table(name = "announcement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Announcement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text")
    private String text;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "announcement_artist",
               joinColumns = @JoinColumn(name="announcements_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="artists_id", referencedColumnName="id"))
    private Set<Artist> artists = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Edition edition;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Announcement text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getImage() {
        return image;
    }

    public Announcement image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Announcement imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public Announcement artists(Set<Artist> artists) {
        this.artists = artists;
        return this;
    }

    public Announcement addArtist(Artist artist) {
        this.artists.add(artist);
        artist.getAnnouncements().add(this);
        return this;
    }

    public Announcement removeArtist(Artist artist) {
        this.artists.remove(artist);
        artist.getAnnouncements().remove(this);
        return this;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Edition getEdition() {
        return edition;
    }

    public Announcement edition(Edition edition) {
        this.edition = edition;
        return this;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
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
        Announcement announcement = (Announcement) o;
        if (announcement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), announcement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Announcement{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
