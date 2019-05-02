package com.excilys.persistence;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.excilys.model.QUser;
import com.excilys.model.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class UserDAO {

  private JPAQueryFactory queryFactory;
  private SessionFactory  sessionFactory;
  private QUser           qUser = QUser.user;

  public void setQueryFactory(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void create(User user) {
    Session session = sessionFactory.openSession();
    session.save(user);
    session.close();
  }

  public Optional<User> read(String username) {
    User user = queryFactory.selectFrom(qUser)
                            .where(qUser.username.eq(username))
                            .fetchOne();
    return Optional.ofNullable(user);
  }

}
