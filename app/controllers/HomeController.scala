package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.libs.json.Json
//import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.ExecutionContext.Implicits.global
import models._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class HomeController @Inject() (aliasDao: AliasDao) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index())
  }

//  val db = Database.forURL("jdbc:mysql://localhost/pfix", driver = "com.mysql.jdbc.Driver", user = "root", password = "2003Flower1101")
//  def byId(id: Int) = db.withSession { implicit session =>
//    println(Alias.filter(_.id === 1).run)
//  }

  def getAll() = Action.async { implicit request =>
    aliasDao.getAll map {
      alias => Ok(Json.toJson(alias))
    }
  }

//  def findById(id: Int): Future[Option[Tables.AliasRow]] = {
//    val query: Query[Tables.Alias, Tables.Alias#TableElementType, Seq] = Alias.filter(_.id === id)
//    val action = query.result.headOption
//    db.run(action)
//  }
}
