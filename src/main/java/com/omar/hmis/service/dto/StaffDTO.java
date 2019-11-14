package com.omar.hmis.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.omar.hmis.domain.enumeration.StaffType;

/**
 * A DTO for the {@link com.omar.hmis.domain.Staff} entity.
 */
public class StaffDTO implements Serializable {

    private Long id;

    @NotNull
    private StaffType staffType;

    @NotNull
    private String qualification;

    @NotNull
    private LocalDate joiningDate;

    @NotNull
    @Min(value = 0)
    private Integer experience;

    private Boolean isRegular;


    private Long departmentId;

    private String departmentName;

    private Long jobDetailsId;

    private Long staffWorkingScheduleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public void setStaffType(StaffType staffType) {
        this.staffType = staffType;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Boolean isIsRegular() {
        return isRegular;
    }

    public void setIsRegular(Boolean isRegular) {
        this.isRegular = isRegular;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getJobDetailsId() {
        return jobDetailsId;
    }

    public void setJobDetailsId(Long jobDetailsId) {
        this.jobDetailsId = jobDetailsId;
    }

    public Long getStaffWorkingScheduleId() {
        return staffWorkingScheduleId;
    }

    public void setStaffWorkingScheduleId(Long staffWorkingScheduleId) {
        this.staffWorkingScheduleId = staffWorkingScheduleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StaffDTO staffDTO = (StaffDTO) o;
        if (staffDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), staffDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StaffDTO{" +
            "id=" + getId() +
            ", staffType='" + getStaffType() + "'" +
            ", qualification='" + getQualification() + "'" +
            ", joiningDate='" + getJoiningDate() + "'" +
            ", experience=" + getExperience() +
            ", isRegular='" + isIsRegular() + "'" +
            ", department=" + getDepartmentId() +
            ", department='" + getDepartmentName() + "'" +
            ", jobDetails=" + getJobDetailsId() +
            ", staffWorkingSchedule=" + getStaffWorkingScheduleId() +
            "}";
    }
}
