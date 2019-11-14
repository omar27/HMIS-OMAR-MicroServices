package com.omar.hmis.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.omar.hmis.domain.enumeration.PatientStatus;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "appointment")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "patient_status", nullable = false)
    private PatientStatus patientStatus;

    @NotNull
    @Column(name = "disease_identified", nullable = false)
    private String diseaseIdentified;

    @NotNull
    @Column(name = "cure_suggested", nullable = false)
    private String cureSuggested;

    @NotNull
    @Column(name = "tests_suggested", nullable = false)
    private String testsSuggested;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("appointments")
    private AppointmentSchedule appointmentSchedule;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("appointments")
    private Bill bill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public Appointment patientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
        return this;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    public String getDiseaseIdentified() {
        return diseaseIdentified;
    }

    public Appointment diseaseIdentified(String diseaseIdentified) {
        this.diseaseIdentified = diseaseIdentified;
        return this;
    }

    public void setDiseaseIdentified(String diseaseIdentified) {
        this.diseaseIdentified = diseaseIdentified;
    }

    public String getCureSuggested() {
        return cureSuggested;
    }

    public Appointment cureSuggested(String cureSuggested) {
        this.cureSuggested = cureSuggested;
        return this;
    }

    public void setCureSuggested(String cureSuggested) {
        this.cureSuggested = cureSuggested;
    }

    public String getTestsSuggested() {
        return testsSuggested;
    }

    public Appointment testsSuggested(String testsSuggested) {
        this.testsSuggested = testsSuggested;
        return this;
    }

    public void setTestsSuggested(String testsSuggested) {
        this.testsSuggested = testsSuggested;
    }

    public AppointmentSchedule getAppointmentSchedule() {
        return appointmentSchedule;
    }

    public Appointment appointmentSchedule(AppointmentSchedule appointmentSchedule) {
        this.appointmentSchedule = appointmentSchedule;
        return this;
    }

    public void setAppointmentSchedule(AppointmentSchedule appointmentSchedule) {
        this.appointmentSchedule = appointmentSchedule;
    }

    public Bill getBill() {
        return bill;
    }

    public Appointment bill(Bill bill) {
        this.bill = bill;
        return this;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Appointment)) {
            return false;
        }
        return id != null && id.equals(((Appointment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", patientStatus='" + getPatientStatus() + "'" +
            ", diseaseIdentified='" + getDiseaseIdentified() + "'" +
            ", cureSuggested='" + getCureSuggested() + "'" +
            ", testsSuggested='" + getTestsSuggested() + "'" +
            "}";
    }
}
