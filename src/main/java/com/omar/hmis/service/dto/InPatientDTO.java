package com.omar.hmis.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.omar.hmis.domain.InPatient} entity.
 */
public class InPatientDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate admitDate;

    @NotNull
    private LocalDate dischargeDate;


    private Long roomId;

    private Long suggestedById;

    private Long patientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAdmitDate() {
        return admitDate;
    }

    public void setAdmitDate(LocalDate admitDate) {
        this.admitDate = admitDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getSuggestedById() {
        return suggestedById;
    }

    public void setSuggestedById(Long staffId) {
        this.suggestedById = staffId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InPatientDTO inPatientDTO = (InPatientDTO) o;
        if (inPatientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inPatientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InPatientDTO{" +
            "id=" + getId() +
            ", admitDate='" + getAdmitDate() + "'" +
            ", dischargeDate='" + getDischargeDate() + "'" +
            ", room=" + getRoomId() +
            ", suggestedBy=" + getSuggestedById() +
            ", patient=" + getPatientId() +
            "}";
    }
}
