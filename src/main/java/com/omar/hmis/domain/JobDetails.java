package com.omar.hmis.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.omar.hmis.domain.enumeration.JobType;

/**
 * A JobDetails.
 */
@Entity
@Table(name = "job_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "jobdetails")
public class JobDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @NotNull
    @Min(value = 1)
    @Max(value = 7)
    @Column(name = "grade", nullable = false)
    private Integer grade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private JobType type;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "salary", nullable = false)
    private Double salary;

    @NotNull
    @Column(name = "details", nullable = false)
    private String details;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public JobDetails designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getGrade() {
        return grade;
    }

    public JobDetails grade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public JobType getType() {
        return type;
    }

    public JobDetails type(JobType type) {
        this.type = type;
        return this;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public Double getSalary() {
        return salary;
    }

    public JobDetails salary(Double salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDetails() {
        return details;
    }

    public JobDetails details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobDetails)) {
            return false;
        }
        return id != null && id.equals(((JobDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "JobDetails{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", grade=" + getGrade() +
            ", type='" + getType() + "'" +
            ", salary=" + getSalary() +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
