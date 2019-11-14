package com.omar.hmis.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.omar.hmis.domain.enumeration.Days;

/**
 * A WorkingSchedule.
 */
@Entity
@Table(name = "working_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "workingschedule")
public class WorkingSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day", nullable = false)
    private Days day;

    @Column(name = "is_off")
    private Boolean isOff;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @NotNull
    @Column(name = "end", nullable = false)
    private String end;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Days getDay() {
        return day;
    }

    public WorkingSchedule day(Days day) {
        this.day = day;
        return this;
    }

    public void setDay(Days day) {
        this.day = day;
    }

    public Boolean isIsOff() {
        return isOff;
    }

    public WorkingSchedule isOff(Boolean isOff) {
        this.isOff = isOff;
        return this;
    }

    public void setIsOff(Boolean isOff) {
        this.isOff = isOff;
    }

    public String getStartTime() {
        return startTime;
    }

    public WorkingSchedule startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEnd() {
        return end;
    }

    public WorkingSchedule end(String end) {
        this.end = end;
        return this;
    }

    public void setEnd(String end) {
        this.end = end;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingSchedule)) {
            return false;
        }
        return id != null && id.equals(((WorkingSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WorkingSchedule{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", isOff='" + isIsOff() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
