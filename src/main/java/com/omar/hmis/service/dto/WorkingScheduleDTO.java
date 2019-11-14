package com.omar.hmis.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.omar.hmis.domain.enumeration.Days;

/**
 * A DTO for the {@link com.omar.hmis.domain.WorkingSchedule} entity.
 */
public class WorkingScheduleDTO implements Serializable {

    private Long id;

    @NotNull
    private Days day;

    private Boolean isOff;

    @NotNull
    private String startTime;

    @NotNull
    private String end;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Days getDay() {
        return day;
    }

    public void setDay(Days day) {
        this.day = day;
    }

    public Boolean isIsOff() {
        return isOff;
    }

    public void setIsOff(Boolean isOff) {
        this.isOff = isOff;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkingScheduleDTO workingScheduleDTO = (WorkingScheduleDTO) o;
        if (workingScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workingScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkingScheduleDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", isOff='" + isIsOff() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
