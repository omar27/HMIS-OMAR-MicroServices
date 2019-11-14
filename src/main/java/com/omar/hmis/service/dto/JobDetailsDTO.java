package com.omar.hmis.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.omar.hmis.domain.enumeration.JobType;

/**
 * A DTO for the {@link com.omar.hmis.domain.JobDetails} entity.
 */
public class JobDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String designation;

    @NotNull
    @Min(value = 1)
    @Max(value = 7)
    private Integer grade;

    @NotNull
    private JobType type;

    @NotNull
    @DecimalMin(value = "0")
    private Double salary;

    @NotNull
    private String details;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public JobType getType() {
        return type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobDetailsDTO jobDetailsDTO = (JobDetailsDTO) o;
        if (jobDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobDetailsDTO{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", grade=" + getGrade() +
            ", type='" + getType() + "'" +
            ", salary=" + getSalary() +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
