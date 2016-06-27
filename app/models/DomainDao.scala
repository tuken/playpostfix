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
}