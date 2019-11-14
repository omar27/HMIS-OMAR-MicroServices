package com.omar.hmis.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.omar.hmis.domain.enumeration.PatientStatus;

/**
 * A DTO for the {@link com.omar.hmis.domain.Appointment} entity.
 */
public class AppointmentDTO implements Serializable {

    private Long id;

    @NotNull
    private PatientStatus patientStatus;

    @NotNull
    private String diseaseIdentified;

    @NotNull
    private String cureSuggested;

    @NotNull
    private String testsSuggested;


    private Long appointmentScheduleId;

    private Long billId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    public String getDiseaseIdentified() {
        return diseaseIdentified;
    }

    public void setDiseaseIdentified(String diseaseIdentified) {
        this.diseaseIdentified = diseaseIdentified;
    }

    public String getCureSuggested() {
        return cureSuggested;
    }

    public void setCureSuggested(String cureSuggested) {
        this.cureSuggested = cureSuggested;
    }

    public String getTestsSuggested() {
        return testsSuggested;
    }

    public void setTestsSuggested(String testsSuggested) {
        this.testsSuggested = testsSuggested;
    }

    public Long getAppointmentScheduleId() {
        return appointmentScheduleId;
    }

    public void setAppointmentScheduleId(Long appointmentScheduleId) {
        this.appointmentScheduleId = appointmentScheduleId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppointmentDTO appointmentDTO = (AppointmentDTO) o;
        if (appointmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AppointmentDTO{" +
            "id=" + getId() +
            ", patientStatus='" + getPatientStatus() + "'" +
            ", diseaseIdentified='" + getDiseaseIdentified() + "'" +
            ", cureSuggested='" + getCureSuggested() + "'" +
            ", testsSuggested='" + getTestsSuggested() + "'" +
            ", appointmentSchedule=" + getAppointmentScheduleId() +
            ", bill=" + getBillId() +
            "}";
    }
}
