package models

import javax.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile
import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Singleton

/**
  * Created by t.ikawa on 2016/06/23.
  */
@Singleton()
class AliasDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends Tables with HasDatabaseConfigProvider[JdbcProfile] {
  val profile = driver
  import driver.api._

  def getAll(implicit ec: ExecutionContext): Future[List[AliasApi]] = {
    val query = Alias
    val action = query.result
    val futureAliases = db.run(action)

    futureAliases.map(
      _.map {
        a => AliasApi(
          id = a.id,
          domainId = a.domainId,
          address = a.address,
          goto = a.goto,
          active = a.active
        )
      }.toList
    )
  }

  private def getAliasQuery(maybeId: Option[Int] = None) = {
    val aliasQuery = maybeId match {
      case None => Alias
      case Some(id) => Alias.filter(_.id === id)
    }
    aliasQuery

//    val withAliasQuery = for {
//
//    }
  }
}
