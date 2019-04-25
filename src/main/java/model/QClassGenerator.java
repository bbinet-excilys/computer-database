package model;

import java.io.File;
import java.io.IOException;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.querydsl.jpa.codegen.JPADomainExporter;

public class QClassGenerator {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    ApplicationContext appContext     = new ClassPathXmlApplicationContext("classpath:/spring/cliContext.xml");
    SessionFactory     sessionFactory = (SessionFactory) appContext.getBean("sessionFactory");
    JPADomainExporter  export         = new JPADomainExporter(new File("src/main/java"), sessionFactory.getMetamodel());
    try {
      export.execute();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

}
