package com.excilys.model;

import java.io.File;
import java.io.IOException;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.querydsl.jpa.codegen.JPADomainExporter;

public class QClassGenerator {

  private static final Logger LOGGER = LoggerFactory.getLogger(QClassGenerator.class);

  public static void main(String[] args) {
    ApplicationContext appContext     = new ClassPathXmlApplicationContext("classpath:/spring/qclassContext.xml");
    SessionFactory     sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
    JPADomainExporter  export         = new JPADomainExporter(new File("src/main/java"), sessionFactory.getMetamodel());
    try {
      export.execute();
      LOGGER.info("QClasses generated");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}
