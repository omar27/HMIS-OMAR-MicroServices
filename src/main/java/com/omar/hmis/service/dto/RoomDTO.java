package com.omar.hmis.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.omar.hmis.domain.enumeration.RoomCategory;

/**
 * A DTO for the {@link com.omar.hmis.domain.Room} entity.
 */
public class RoomDTO implements Serializable {

    private Long id;

    @NotNull
    private RoomCategory category;

    @NotNull
    @Min(value = 100L)
    private Long rent;

    private String roomId;

    @NotNull
    private Boolean availablity;


    private Long departmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomCategory getCategory() {
        return category;
    }

    public void setCategory(RoomCategory category) {
        this.category = category;
    }

    public Long getRent() {
        return rent;
    }

    public void setRent(Long rent) {
        this.rent = rent;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Boolean isAvailablity() {
        return availablity;
    }

    public void setAvailablity(Boolean availablity) {
        this.availablity = availablity;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoomDTO roomDTO = (RoomDTO) o;
        if (roomDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", rent=" + getRent() +
            ", roomId='" + getRoomId() + "'" +
            ", availablity='" + isAvailablity() + "'" +
            ", department=" + getDepartmentId() +
            "}";
    }
}
