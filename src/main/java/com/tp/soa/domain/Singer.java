package com.tp.soa.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Singer.
 */
@Entity
@Table(name = "singer")
public class Singer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fname")
    private String fname;

    @Column(name = "lname")
    private String lname;

    @Column(name = "adress")
    private String adress;

    @OneToMany(mappedBy = "singerr")
    private Set<Albums> avoir_albums = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public Singer fname(String fname) {
        this.fname = fname;
        return this;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public Singer lname(String lname) {
        this.lname = lname;
        return this;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAdress() {
        return adress;
    }

    public Singer adress(String adress) {
        this.adress = adress;
        return this;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Set<Albums> getAvoir_albums() {
        return avoir_albums;
    }

    public Singer avoir_albums(Set<Albums> albums) {
        this.avoir_albums = albums;
        return this;
    }

    public Singer addAvoir_album(Albums albums) {
        this.avoir_albums.add(albums);
        albums.setSingerr(this);
        return this;
    }

    public Singer removeAvoir_album(Albums albums) {
        this.avoir_albums.remove(albums);
        albums.setSingerr(null);
        return this;
    }

    public void setAvoir_albums(Set<Albums> albums) {
        this.avoir_albums = albums;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Singer)) {
            return false;
        }
        return id != null && id.equals(((Singer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Singer{" +
            "id=" + getId() +
            ", fname='" + getFname() + "'" +
            ", lname='" + getLname() + "'" +
            ", adress='" + getAdress() + "'" +
            "}";
    }
}
