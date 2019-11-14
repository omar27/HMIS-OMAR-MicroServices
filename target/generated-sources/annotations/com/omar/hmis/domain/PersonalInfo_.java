package com.omar.hmis.domain;

import com.omar.hmis.domain.enumeration.EntityType;
import com.omar.hmis.domain.enumeration.Gender;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersonalInfo.class)
public abstract class PersonalInfo_ {

	public static volatile SingularAttribute<PersonalInfo, String> firstName;
	public static volatile SingularAttribute<PersonalInfo, String> lastName;
	public static volatile SingularAttribute<PersonalInfo, String> phoneNumber;
	public static volatile SingularAttribute<PersonalInfo, String> address;
	public static volatile SingularAttribute<PersonalInfo, Gender> gender;
	public static volatile SingularAttribute<PersonalInfo, String> city;
	public static volatile SingularAttribute<PersonalInfo, EntityType> entityType;
	public static volatile SingularAttribute<PersonalInfo, Integer> entityId;
	public static volatile SingularAttribute<PersonalInfo, Long> id;
	public static volatile SingularAttribute<PersonalInfo, String> cnic;
	public static volatile SingularAttribute<PersonalInfo, String> email;
	public static volatile SingularAttribute<PersonalInfo, Integer> age;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String ADDRESS = "address";
	public static final String GENDER = "gender";
	public static final String CITY = "city";
	public static final String ENTITY_TYPE = "entityType";
	public static final String ENTITY_ID = "entityId";
	public static final String ID = "id";
	public static final String CNIC = "cnic";
	public static final String EMAIL = "email";
	public static final String AGE = "age";

}

