package com.excilys.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.QCompany;
import com.excilys.model.QComputer;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ComputerDAO {

  private final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

  private QComputer       qComputer = QComputer.computer;
  private JPAQueryFactory queryFactory;
  private SessionFactory  sessionFactory;

  public void setQueryFactory(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void create(Computer computer) {
    LOGGER.debug(computer.toString());
    Session session = sessionFactory.openSession();
    session.save(computer);
    session.close();
  }

  public void delete(Computer computer) {
    queryFactory.delete(qComputer)
                .where(qComputer.id.eq(computer.getId()))
                .execute();
  }

  public List<Computer> list() {
    List<Computer> computers = queryFactory.selectFrom(qComputer).fetchAll().fetch();
    return computers;
  }

  public Optional<Computer> read(Long id) {
    Computer computer = queryFactory.selectFrom(qComputer)
                                    .where(qComputer.id.eq(id))
                                    .fetchOne();
    return Optional.ofNullable(computer);
  }

  public void update(Computer computer) {
    LOGGER.debug(computer.toString());
    Session session = sessionFactory.openSession();
    session.save(computer);
    session.close();
  }

  public List<Computer> searchByName(String name) {
    return queryFactory.selectFrom(qComputer)
                       .where(qComputer.name.eq(name)
                                            .or(qComputer.company.eq(queryFactory.selectFrom(QCompany.company)
                                                                                 .where(QCompany.company.name.eq(name)))))
                       .fetch();
  }

  public void deleteCompany(Company company) {
    queryFactory.delete(qComputer).where(qComputer.company.eq(company)).execute();
  }
}
