package com.omar.hmis.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.omar.hmis.domain.enumeration.AppointmentScheduleStatus;

/**
 * A DTO for the {@link com.omar.hmis.domain.AppointmentSchedule} entity.
 */
public class AppointmentScheduleDTO implements Serializable {

    private Long id;

    @NotNull
    private AppointmentScheduleStatus status;

    @NotNull
    private LocalDate scheduledDate;


    private Long patientId;

    private Long staffId;

    private Long scheduledById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppointmentScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentScheduleStatus status) {
        this.status = status;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getScheduledById() {
        return scheduledById;
    }

    public void setScheduledById(Long staffId) {
        this.scheduledById = staffId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppointmentScheduleDTO appointmentScheduleDTO = (AppointmentScheduleDTO) o;
        if (appointmentScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointmentScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppointmentScheduleDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", scheduledDate='" + getScheduledDate() + "'" +
            ", patient=" + getPatientId() +
            ", staff=" + getStaffId() +
            ", scheduledBy=" + getScheduledById() +
            "}";
    }
}
