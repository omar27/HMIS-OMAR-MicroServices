package com.omar.hmis.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.omar.hmis.domain.enumeration.BillPaidStatus;

/**
 * A Bill.
 */
@Entity
@Table(name = "bill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bill")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "doctor_fee", nullable = false)
    private Double doctorFee;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "medicine_charges", nullable = false)
    private Double medicineCharges;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "tests_fee", nullable = false)
    private Double testsFee;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "room_charges", nullable = false)
    private Double roomCharges;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "other_charges", nullable = false)
    private Double otherCharges;

    @NotNull
    @Column(name = "total_bill", nullable = false)
    private Double totalBill;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "paid_status", nullable = false)
    private BillPaidStatus paidStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("bills")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDoctorFee() {
        return doctorFee;
    }

    public Bill doctorFee(Double doctorFee) {
        this.doctorFee = doctorFee;
        return this;
    }

    public void setDoctorFee(Double doctorFee) {
        this.doctorFee = doctorFee;
    }

    public Double getMedicineCharges() {
        return medicineCharges;
    }

    public Bill medicineCharges(Double medicineCharges) {
        this.medicineCharges = medicineCharges;
        return this;
    }

    public void setMedicineCharges(Double medicineCharges) {
        this.medicineCharges = medicineCharges;
    }

    public Double getTestsFee() {
        return testsFee;
    }

    public Bill testsFee(Double testsFee) {
        this.testsFee = testsFee;
        return this;
    }

    public void setTestsFee(Double testsFee) {
        this.testsFee = testsFee;
    }

    public Double getRoomCharges() {
        return roomCharges;
    }

    public Bill roomCharges(Double roomCharges) {
        this.roomCharges = roomCharges;
        return this;
    }

    public void setRoomCharges(Double roomCharges) {
        this.roomCharges = roomCharges;
    }

    public Double getOtherCharges() {
        return otherCharges;
    }

    public Bill otherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
        return this;
    }

    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Double getTotalBill() {
        return totalBill;
    }

    public Bill totalBill(Double totalBill) {
        this.totalBill = totalBill;
        return this;
    }

    public void setTotalBill(Double totalBill) {
        this.totalBill = totalBill;
    }

    public BillPaidStatus getPaidStatus() {
        return paidStatus;
    }

    public Bill paidStatus(BillPaidStatus paidStatus) {
        this.paidStatus = paidStatus;
        return this;
    }

    public void setPaidStatus(BillPaidStatus paidStatus) {
        this.paidStatus = paidStatus;
    }

    public Patient getPatient() {
        return patient;
    }

    public Bill patient(Patient patient) {
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
        if (!(o instanceof Bill)) {
            return false;
        }
        return id != null && id.equals(((Bill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Bill{" +
            "id=" + getId() +
            ", doctorFee=" + getDoctorFee() +
            ", medicineCharges=" + getMedicineCharges() +
            ", testsFee=" + getTestsFee() +
            ", roomCharges=" + getRoomCharges() +
            ", otherCharges=" + getOtherCharges() +
            ", totalBill=" + getTotalBill() +
            ", paidStatus='" + getPaidStatus() + "'" +
            "}";
    }
}
