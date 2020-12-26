package com.tp.soa.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.tp.soa.domain.Singer} entity.
 */
public class SingerDTO implements Serializable {
    
    private Long id;

    private String fname;

    private String lname;

    private String adress;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SingerDTO)) {
            return false;
        }

        return id != null && id.equals(((SingerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SingerDTO{" +
            "id=" + getId() +
            ", fname='" + getFname() + "'" +
            ", lname='" + getLname() + "'" +
            ", adress='" + getAdress() + "'" +
            "}";
    }
}
