package com.omar.hmis.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.omar.hmis.domain.enumeration.AppointmentScheduleStatus;

/**
 * A AppointmentSchedule.
 */
@Entity
@Table(name = "appointment_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "appointmentschedule")
public class AppointmentSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentScheduleStatus status;

    @NotNull
    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("appointmentSchedules")
    private Patient patient;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("appointmentSchedules")
    private Staff staff;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("appointmentSchedules")
    private Staff scheduledBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppointmentScheduleStatus getStatus() {
        return status;
    }

    public AppointmentSchedule status(AppointmentScheduleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AppointmentScheduleStatus status) {
        this.status = status;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public AppointmentSchedule scheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
        return this;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public AppointmentSchedule patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Staff getStaff() {
        return staff;
    }

    public AppointmentSchedule staff(Staff staff) {
        this.staff = staff;
        return this;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Staff getScheduledBy() {
        return scheduledBy;
    }

    public AppointmentSchedule scheduledBy(Staff staff) {
        this.scheduledBy = staff;
        return this;
    }

    public void setScheduledBy(Staff staff) {
        this.scheduledBy = staff;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppointmentSchedule)) {
            return false;
        }
        return id != null && id.equals(((AppointmentSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AppointmentSchedule{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", scheduledDate='" + getScheduledDate() + "'" +
            "}";
    }
}
