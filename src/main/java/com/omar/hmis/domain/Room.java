package com.omar.hmis.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.omar.hmis.domain.enumeration.RoomCategory;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private RoomCategory category;

    @NotNull
    @Min(value = 100L)
    @Column(name = "rent", nullable = false)
    private Long rent;

    @Column(name = "room_id")
    private String roomId;

    @NotNull
    @Column(name = "availablity", nullable = false)
    private Boolean availablity;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("rooms")
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public Room category(RoomCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(RoomCategory category) {
        this.category = category;
    }

    public Long getRent() {
        return rent;
    }

    public Room rent(Long rent) {
        this.rent = rent;
        return this;
    }

    public void setRent(Long rent) {
        this.rent = rent;
    }

    public String getRoomId() {
        return roomId;
    }

    public Room roomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Boolean isAvailablity() {
        return availablity;
    }

    public Room availablity(Boolean availablity) {
        this.availablity = availablity;
        return this;
    }

    public void setAvailablity(Boolean availablity) {
        this.availablity = availablity;
    }

    public Department getDepartment() {
        return department;
    }

    public Room department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        return id != null && id.equals(((Room) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", rent=" + getRent() +
            ", roomId='" + getRoomId() + "'" +
            ", availablity='" + isAvailablity() + "'" +
            "}";
    }
}
