package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by t.ikawa on 2016/06/23.
  */
@Singleton
class AliasDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends Tables with HasDatabaseConfigProvider[JdbcProfile] {
  val profile = driver
  import driver.api._

  def getAll(implicit ec: ExecutionContext): Future[Seq[AliasApi]] = {
    db.run(getAliasQuery().result)
//      db.run(getAliasQuery().result) map {
//      dataTuples =>
//        val groupedByPerson = dataTuples.groupBy(_._1)
//        groupedByPerson.map {
//          AliasApi(
//            id = )
//        }
//    }
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
