package com.omar.hmis.domain;

import com.omar.hmis.domain.enumeration.JobType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(JobDetails.class)
public abstract class JobDetails_ {

	public static volatile SingularAttribute<JobDetails, Integer> grade;
	public static volatile SingularAttribute<JobDetails, String> details;
	public static volatile SingularAttribute<JobDetails, Long> id;
	public static volatile SingularAttribute<JobDetails, String> designation;
	public static volatile SingularAttribute<JobDetails, JobType> type;
	public static volatile SingularAttribute<JobDetails, Double> salary;

	public static final String GRADE = "grade";
	public static final String DETAILS = "details";
	public static final String ID = "id";
	public static final String DESIGNATION = "designation";
	public static final String TYPE = "type";
	public static final String SALARY = "salary";

}

