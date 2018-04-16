package es.festis.atlas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Edition.
 */
@Entity
@Table(name = "edition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Edition extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotNull
    @Column(name = "has_camping", nullable = false)
    private Boolean hasCamping;

    @Column(name = "price")
    private Double price;

    @Lob
    @Column(name = "cover")
    private byte[] cover;

    @Column(name = "cover_content_type")
    private String coverContentType;

    @Lob
    @Column(name = "poster")
    private byte[] poster;

    @Column(name = "poster_content_type")
    private String posterContentType;

    @OneToMany(mappedBy = "edition")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Attend> attendants = new HashSet<>();

    @OneToMany(mappedBy = "edition")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Announcement> announcements = new HashSet<>();

    @OneToMany(mappedBy = "edition")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private Festival festival;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public Edition location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Edition startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Edition endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isHasCamping() {
        return hasCamping;
    }

    public Edition hasCamping(Boolean hasCamping) {
        this.hasCamping = hasCamping;
        return this;
    }

    public void setHasCamping(Boolean hasCamping) {
        this.hasCamping = hasCamping;
    }

    public Double getPrice() {
        return price;
    }

    public Edition price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public byte[] getCover() {
        return cover;
    }

    public Edition cover(byte[] cover) {
        this.cover = cover;
        return this;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getCoverContentType() {
        return coverContentType;
    }

    public Edition coverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
        return this;
    }

    public void setCoverContentType(String coverContentType) {
        this.coverContentType = coverContentType;
    }

    public byte[] getPoster() {
        return poster;
    }

    public Edition poster(byte[] poster) {
        this.poster = poster;
        return this;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public String getPosterContentType() {
        return posterContentType;
    }

    public Edition posterContentType(String posterContentType) {
        this.posterContentType = posterContentType;
        return this;
    }

    public void setPosterContentType(String posterContentType) {
        this.posterContentType = posterContentType;
    }

    public Set<Attend> getAttendants() {
        return attendants;
    }

    public Edition attendants(Set<Attend> attends) {
        this.attendants = attends;
        return this;
    }

    public Edition addAttendant(Attend attend) {
        this.attendants.add(attend);
        attend.setEdition(this);
        return this;
    }

    public Edition removeAttendant(Attend attend) {
        this.attendants.remove(attend);
        attend.setEdition(null);
        return this;
    }

    public void setAttendants(Set<Attend> attends) {
        this.attendants = attends;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public Edition announcements(Set<Announcement> announcements) {
        this.announcements = announcements;
        return this;
    }

    public Edition addAnnouncement(Announcement announcement) {
        this.announcements.add(announcement);
        announcement.setEdition(this);
        return this;
    }

    public Edition removeAnnouncement(Announcement announcement) {
        this.announcements.remove(announcement);
        announcement.setEdition(null);
        return this;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Edition comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Edition addComment(Comment comment) {
        this.comments.add(comment);
        comment.setEdition(this);
        return this;
    }

    public Edition removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setEdition(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Festival getFestival() {
        return festival;
    }

    public Edition festival(Festival festival) {
        this.festival = festival;
        return this;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
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
        Edition edition = (Edition) o;
        if (edition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), edition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Edition{" +
            "id=" + getId() +
            ", location='" + getLocation() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", hasCamping='" + isHasCamping() + "'" +
            ", price=" + getPrice() +
            ", cover='" + getCover() + "'" +
            ", coverContentType='" + getCoverContentType() + "'" +
            ", poster='" + getPoster() + "'" +
            ", posterContentType='" + getPosterContentType() + "'" +
            "}";
    }
}
