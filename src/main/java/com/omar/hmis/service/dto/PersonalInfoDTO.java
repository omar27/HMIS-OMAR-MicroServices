package com.omar.hmis.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.omar.hmis.domain.enumeration.Gender;
import com.omar.hmis.domain.enumeration.EntityType;

/**
 * A DTO for the {@link com.omar.hmis.domain.PersonalInfo} entity.
 */
public class PersonalInfoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 13, max = 13)
    @Pattern(regexp = "^[0-9]*$")
    private String cnic;

    @NotNull
    @Size(min = 3)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 20)
    private String lastName;

    @NotNull
    @Size(min = 11, max = 13)
    @Pattern(regexp = "^[0-9]*$")
    private String phoneNumber;

    @NotNull
    private String email;

    @NotNull
    private Gender gender;

    @NotNull
    @Min(value = 0)
    private Integer age;

    @NotNull
    @Size(min = 10)
    private String address;

    @NotNull
    private String city;

    @NotNull
    private EntityType entityType;

    @NotNull
    private Integer entityId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonalInfoDTO personalInfoDTO = (PersonalInfoDTO) o;
        if (personalInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personalInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonalInfoDTO{" +
            "id=" + getId() +
            ", cnic='" + getCnic() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", gender='" + getGender() + "'" +
            ", age=" + getAge() +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", entityType='" + getEntityType() + "'" +
            ", entityId=" + getEntityId() +
            "}";
    }
}
