package com.tp.soa.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.tp.soa.domain.Albums} entity.
 */
public class AlbumsDTO implements Serializable {
    
    private Long id;

    private String title;

    private String type;


    private Long singerrId;

    private String singerrFname;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSingerrId() {
        return singerrId;
    }

    public void setSingerrId(Long singerId) {
        this.singerrId = singerId;
    }

    public String getSingerrFname() {
        return singerrFname;
    }

    public void setSingerrFname(String singerFname) {
        this.singerrFname = singerFname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AlbumsDTO)) {
            return false;
        }

        return id != null && id.equals(((AlbumsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlbumsDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", singerrId=" + getSingerrId() +
            ", singerrFname='" + getSingerrFname() + "'" +
            "}";
    }
}
