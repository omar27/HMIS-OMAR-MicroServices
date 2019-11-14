package com.omar.hmis.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Patient.class)
public abstract class Patient_ {

	public static volatile SingularAttribute<Patient, Boolean> isRegular;
	public static volatile SingularAttribute<Patient, String> patientId;
	public static volatile SingularAttribute<Patient, Long> id;
	public static volatile SingularAttribute<Patient, Department> department;

	public static final String IS_REGULAR = "isRegular";
	public static final String PATIENT_ID = "patientId";
	public static final String ID = "id";
	public static final String DEPARTMENT = "department";

}

