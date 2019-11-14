package com.omar.hmis.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.omar.hmis.domain.enumeration.BillPaidStatus;

/**
 * A DTO for the {@link com.omar.hmis.domain.Bill} entity.
 */
public class BillDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private Double doctorFee;

    @NotNull
    @DecimalMin(value = "0")
    private Double medicineCharges;

    @NotNull
    @DecimalMin(value = "0")
    private Double testsFee;

    @NotNull
    @DecimalMin(value = "0")
    private Double roomCharges;

    @NotNull
    @DecimalMin(value = "0")
    private Double otherCharges;

    @NotNull
    private Double totalBill;

    @NotNull
    private BillPaidStatus paidStatus;


    private Long patientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(Double doctorFee) {
        this.doctorFee = doctorFee;
    }

    public Double getMedicineCharges() {
        return medicineCharges;
    }

    public void setMedicineCharges(Double medicineCharges) {
        this.medicineCharges = medicineCharges;
    }

    public Double getTestsFee() {
        return testsFee;
    }

    public void setTestsFee(Double testsFee) {
        this.testsFee = testsFee;
    }

    public Double getRoomCharges() {
        return roomCharges;
    }

    public void setRoomCharges(Double roomCharges) {
        this.roomCharges = roomCharges;
    }

    public Double getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(Double otherCharges) {
        this.otherCharges = otherCharges;
    }

    public Double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Double totalBill) {
        this.totalBill = totalBill;
    }

    public BillPaidStatus getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(BillPaidStatus paidStatus) {
        this.paidStatus = paidStatus;
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

        BillDTO billDTO = (BillDTO) o;
        if (billDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), billDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BillDTO{" +
            "id=" + getId() +
            ", doctorFee=" + getDoctorFee() +
            ", medicineCharges=" + getMedicineCharges() +
            ", testsFee=" + getTestsFee() +
            ", roomCharges=" + getRoomCharges() +
            ", otherCharges=" + getOtherCharges() +
            ", totalBill=" + getTotalBill() +
            ", paidStatus='" + getPaidStatus() + "'" +
            ", patient=" + getPatientId() +
            "}";
    }
}
