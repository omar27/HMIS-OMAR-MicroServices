package com.omar.hmis.domain;

import com.omar.hmis.domain.enumeration.StaffType;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Staff.class)
public abstract class Staff_ {

	public static volatile SingularAttribute<Staff, String> qualification;
	public static volatile SingularAttribute<Staff, Boolean> isRegular;
	public static volatile SingularAttribute<Staff, LocalDate> joiningDate;
	public static volatile SingularAttribute<Staff, Long> id;
	public static volatile SingularAttribute<Staff, StaffType> staffType;
	public static volatile SingularAttribute<Staff, Integer> experience;
	public static volatile SingularAttribute<Staff, Department> department;
	public static volatile SingularAttribute<Staff, JobDetails> jobDetails;
	public static volatile SingularAttribute<Staff, StaffWorkingSchedule> staffWorkingSchedule;

	public static final String QUALIFICATION = "qualification";
	public static final String IS_REGULAR = "isRegular";
	public static final String JOINING_DATE = "joiningDate";
	public static final String ID = "id";
	public static final String STAFF_TYPE = "staffType";
	public static final String EXPERIENCE = "experience";
	public static final String DEPARTMENT = "department";
	public static final String JOB_DETAILS = "jobDetails";
	public static final String STAFF_WORKING_SCHEDULE = "staffWorkingSchedule";

}

