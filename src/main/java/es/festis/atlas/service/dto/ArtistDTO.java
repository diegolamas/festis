package es.festis.atlas.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Artist entity.
 */
public class ArtistDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String previewUrl;

    private String web;

    private String spotify;

    private String googlemusic;

    private String applemusic;

    private String facebook;

    private String twitter;

    private String instagram;

    private String youtube;

    private String imageUrl;

    private Long popularity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getSpotify() {
        return spotify;
    }

    public void setSpotify(String spotify) {
        this.spotify = spotify;
    }

    public String getGooglemusic() {
        return googlemusic;
    }

    public void setGooglemusic(String googlemusic) {
        this.googlemusic = googlemusic;
    }

    public String getApplemusic() {
        return applemusic;
    }

    public void setApplemusic(String applemusic) {
        this.applemusic = applemusic;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArtistDTO artistDTO = (ArtistDTO) o;
        if(artistDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), artistDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArtistDTO{" +
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
