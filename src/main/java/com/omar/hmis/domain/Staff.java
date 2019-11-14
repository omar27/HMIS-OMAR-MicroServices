package com.omar.hmis.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.omar.hmis.domain.enumeration.StaffType;

/**
 * A Staff.
 */
@Entity
@Table(name = "staff")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "staff")
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "staff_type", nullable = false)
    private StaffType staffType;

    @NotNull
    @Column(name = "qualification", nullable = false)
    private String qualification;

    @NotNull
    @Column(name = "joining_date", nullable = false)
    private LocalDate joiningDate;

    @NotNull
    @Min(value = 0)
    @Column(name = "experience", nullable = false)
    private Integer experience;

    @Column(name = "is_regular")
    private Boolean isRegular;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("staff")
    private Department department;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("staff")
    private JobDetails jobDetails;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("staff")
    private StaffWorkingSchedule staffWorkingSchedule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StaffType getStaffType() {
        return staffType;
    }

    public Staff staffType(StaffType staffType) {
        this.staffType = staffType;
        return this;
    }

    public void setStaffType(StaffType staffType) {
        this.staffType = staffType;
    }

    public String getQualification() {
        return qualification;
    }

    public Staff qualification(String qualification) {
        this.qualification = qualification;
        return this;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public Staff joiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
        return this;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Integer getExperience() {
        return experience;
    }

    public Staff experience(Integer experience) {
        this.experience = experience;
        return this;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Boolean isIsRegular() {
        return isRegular;
    }

    public Staff isRegular(Boolean isRegular) {
        this.isRegular = isRegular;
        return this;
    }

    public void setIsRegular(Boolean isRegular) {
        this.isRegular = isRegular;
    }

    public Department getDepartment() {
        return department;
    }

    public Staff department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public JobDetails getJobDetails() {
        return jobDetails;
    }

    public Staff jobDetails(JobDetails jobDetails) {
        this.jobDetails = jobDetails;
        return this;
    }

    public void setJobDetails(JobDetails jobDetails) {
        this.jobDetails = jobDetails;
    }

    public StaffWorkingSchedule getStaffWorkingSchedule() {
        return staffWorkingSchedule;
    }

    public Staff staffWorkingSchedule(StaffWorkingSchedule staffWorkingSchedule) {
        this.staffWorkingSchedule = staffWorkingSchedule;
        return this;
    }

    public void setStaffWorkingSchedule(StaffWorkingSchedule staffWorkingSchedule) {
        this.staffWorkingSchedule = staffWorkingSchedule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Staff)) {
            return false;
        }
        return id != null && id.equals(((Staff) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Staff{" +
            "id=" + getId() +
            ", staffType='" + getStaffType() + "'" +
            ", qualification='" + getQualification() + "'" +
            ", joiningDate='" + getJoiningDate() + "'" +
            ", experience=" + getExperience() +
            ", isRegular='" + isIsRegular() + "'" +
            "}";
    }
}
