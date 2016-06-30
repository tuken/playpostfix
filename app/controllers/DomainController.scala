package controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc._
import scala.concurrent.Future
import models._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class DomainController @Inject()(domainDao: DomainDao) extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.domainindex())
  }

  def getAll = Action.async { implicit request =>
    domainDao.getAll map {
      domain => Ok(Json.toJson(domain))
    }
  }

  def create = Action.async(parse.json) { implicit request =>
    request.body.validate[DomainApi].map {
      domainApi =>
        domainDao.create(domainApi) map {
          did => Ok(Json.obj("id" -> did))
        }
    } recoverTotal { t =>
      Future.successful(BadRequest(Json.obj("error" -> "Wrong JSON format")))
    }
  }
}
