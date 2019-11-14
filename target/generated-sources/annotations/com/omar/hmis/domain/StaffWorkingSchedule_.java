package com.omar.hmis.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StaffWorkingSchedule.class)
public abstract class StaffWorkingSchedule_ {

	public static volatile SingularAttribute<StaffWorkingSchedule, String> description;
	public static volatile SingularAttribute<StaffWorkingSchedule, Staff> staff;
	public static volatile SingularAttribute<StaffWorkingSchedule, Long> id;
	public static volatile SingularAttribute<StaffWorkingSchedule, WorkingSchedule> workingSchedule;

	public static final String DESCRIPTION = "description";
	public static final String STAFF = "staff";
	public static final String ID = "id";
	public static final String WORKING_SCHEDULE = "workingSchedule";

}

