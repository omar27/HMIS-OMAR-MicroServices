package com.omar.hmis.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.omar.hmis.domain.Hospital} entity.
 */
public class HospitalDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 4)
    private String name;

    @NotNull
    private String email;

    @NotNull
    @Size(min = 10)
    private String address;

    @NotNull
    @Size(min = 11, max = 13)
    @Pattern(regexp = "^[0-9]*$")
    private String phoneNumber;

    @NotNull
    @Size(min = 4, max = 4)
    private String registrationNumber;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HospitalDTO hospitalDTO = (HospitalDTO) o;
        if (hospitalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hospitalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HospitalDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            "}";
    }
}
