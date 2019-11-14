package com.omar.hmis.domain;

import com.omar.hmis.domain.enumeration.Days;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(WorkingSchedule.class)
public abstract class WorkingSchedule_ {

	public static volatile SingularAttribute<WorkingSchedule, Boolean> isOff;
	public static volatile SingularAttribute<WorkingSchedule, String> startTime;
	public static volatile SingularAttribute<WorkingSchedule, String> end;
	public static volatile SingularAttribute<WorkingSchedule, Long> id;
	public static volatile SingularAttribute<WorkingSchedule, Days> day;

	public static final String IS_OFF = "isOff";
	public static final String START_TIME = "startTime";
	public static final String END = "end";
	public static final String ID = "id";
	public static final String DAY = "day";

}

