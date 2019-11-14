package com.omar.hmis.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Hospital.class)
public abstract class Hospital_ {

	public static volatile SingularAttribute<Hospital, String> address;
	public static volatile SingularAttribute<Hospital, String> phoneNumber;
	public static volatile SingularAttribute<Hospital, String> registrationNumber;
	public static volatile SingularAttribute<Hospital, String> name;
	public static volatile SingularAttribute<Hospital, Long> id;
	public static volatile SingularAttribute<Hospital, String> email;

	public static final String ADDRESS = "address";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String REGISTRATION_NUMBER = "registrationNumber";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

