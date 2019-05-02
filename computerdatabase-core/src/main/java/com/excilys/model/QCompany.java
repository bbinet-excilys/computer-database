package com.excilys.model;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompany extends EntityPathBase<Company> {

  private static final long serialVersionUID = 1096066712L;

  public static final QCompany company = new QCompany("company");

  public final NumberPath<Long> id = createNumber("id", Long.class);

  public final StringPath name = createString("name");

  public QCompany(String variable) {
    super(Company.class, forVariable(variable));
  }

  public QCompany(Path<? extends Company> path) {
    super(path.getType(), path.getMetadata());
  }

  public QCompany(PathMetadata metadata) {
    super(Company.class, metadata);
  }

}
