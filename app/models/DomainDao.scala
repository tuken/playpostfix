package models

import javax.inject.{Inject, Singleton}

import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by t.ikawa on 2016/06/23.
  */
@Singleton()
class DomainDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends Tables with HasDatabaseConfigProvider[JdbcProfile] {
  val profile = driver
  import driver.api._

  def getAll(implicit ec: ExecutionContext): Future[List[DomainApi]] = {
    val query = Domain
    val action = query.result
    val futureDomains = db.run(action)

    futureDomains.map(
      _.map {
        d => DomainApi(
          id = d.id,
          domain = d.domain,
          description = d.description,
          active = d.active
        )
      }.toList
    )
  }

  private def getDomainQuery(maybeId: Option[Int] = None) = {
    val domainQuery = maybeId match {
      case None => Domain
      case Some(id) => Domain.filter(_.id === id)
    }
    domainQuery

//    val withAliasQuery = for {
//
//    }
  }

  def create(dmn: DomainApi) (implicit ec: ExecutionContext): Future[Int] = {
    val domainRow = DomainRow(id = 0, domain = dmn.domain, description = dmn.description, active = dmn.active)
    db.run((Domain returning Domain.map(_.id)) += domainRow)
  }

  def delete(id: Int) (implicit ec: ExecutionContext): Future[Int] = {
    db.run(Domain.filter(_.id === id).delete)
  }
}
