package com.omar.hmis.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.omar.hmis.domain.StaffWorkingSchedule} entity.
 */
public class StaffWorkingScheduleDTO implements Serializable {

    private Long id;

    private String description;


    private Long staffId;

    private Long workingScheduleId;

    private String workingScheduleDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getWorkingScheduleId() {
        return workingScheduleId;
    }

    public void setWorkingScheduleId(Long workingScheduleId) {
        this.workingScheduleId = workingScheduleId;
    }

    public String getWorkingScheduleDay() {
        return workingScheduleDay;
    }

    public void setWorkingScheduleDay(String workingScheduleDay) {
        this.workingScheduleDay = workingScheduleDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StaffWorkingScheduleDTO staffWorkingScheduleDTO = (StaffWorkingScheduleDTO) o;
        if (staffWorkingScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), staffWorkingScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StaffWorkingScheduleDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", staff=" + getStaffId() +
            ", workingSchedule=" + getWorkingScheduleId() +
            ", workingSchedule='" + getWorkingScheduleDay() + "'" +
            "}";
    }
}
