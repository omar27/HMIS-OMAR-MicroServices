package com.omar.hmis.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A InPatient.
 */
@Entity
@Table(name = "in_patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "inpatient")
public class InPatient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "admit_date", nullable = false)
    private LocalDate admitDate;

    @NotNull
    @Column(name = "discharge_date", nullable = false)
    private LocalDate dischargeDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("inPatients")
    private Room room;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("inPatients")
    private Staff suggestedBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("inPatients")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAdmitDate() {
        return admitDate;
    }

    public InPatient admitDate(LocalDate admitDate) {
        this.admitDate = admitDate;
        return this;
    }

    public void setAdmitDate(LocalDate admitDate) {
        this.admitDate = admitDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public InPatient dischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
        return this;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public Room getRoom() {
        return room;
    }

    public InPatient room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Staff getSuggestedBy() {
        return suggestedBy;
    }

    public InPatient suggestedBy(Staff staff) {
        this.suggestedBy = staff;
        return this;
    }

    public void setSuggestedBy(Staff staff) {
        this.suggestedBy = staff;
    }

    public Patient getPatient() {
        return patient;
    }

    public InPatient patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InPatient)) {
            return false;
        }
        return id != null && id.equals(((InPatient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InPatient{" +
            "id=" + getId() +
            ", admitDate='" + getAdmitDate() + "'" +
            ", dischargeDate='" + getDischargeDate() + "'" +
            "}";
    }
}
