package controllers

import javax.inject._

import play.api._
import play.api.mvc._

import scala.concurrent._
import models._
import models.Tables._
//import play.libs.Json
import play.api.libs.json._
import play.api.libs.json.Json

import slick.driver.MySQLDriver.api._

//object Tables extends{ // or just use object demo.Tables, which is hard-wired to the driver stated during generation
//  val profile = slick.driver.MySQLDriver
//} with models.Tables
//import Tables._
//import Tables.profile.backend._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  object JsonFormat {
    implicit val aliasFormat = Json.format[Alias]
  }

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

//  val db = Database.forURL("jdbc:mysql://localhost/pfix", driver = "com.mysql.jdbc.Driver", user = "root", password = "2003Flower1101")
//  def byId(id: Int) = db.withSession { implicit session =>
//    println(Alias.filter(_.id === 1).run)
//  }

  private def successResponse(data: JsValue, message: String) = {
    obj("status" -> "success", "data" -> data, "msg" -> message)
  }

  def getAll() = Action.async {
    Tables.getAll().map { res =>
      Ok(successResponse(Json.toJson(res), "Getting alias list successfully"))
    }
  }

  def findById(id: Int): Future[Option[Tables.AliasRow]] = {
    val query: Query[Tables.Alias, Tables.Alias#TableElementType, Seq] = Alias.filter(_.id === id)
    val action = query.result.headOption
    db.run(action)
  }
}
