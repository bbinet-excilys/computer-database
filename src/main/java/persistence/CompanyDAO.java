package persistence;

import java.util.List;
import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import model.Company;
import model.QCompany;

public class CompanyDAO {

  private QCompany        qCompany = QCompany.company;
  private JPAQueryFactory queryFactory;

  public void setQueryFactory(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public List<Company> list() {
    return queryFactory.selectFrom(qCompany).fetch();
  }

  public Optional<Company> read(Long id) {
    return Optional.ofNullable(queryFactory.selectFrom(qCompany).where(qCompany.id.eq(id)).fetchOne());
  }

  public void delete(Company company) {
    queryFactory.delete(qCompany).where(qCompany.id.eq(company.getId())).execute();
  }

}
