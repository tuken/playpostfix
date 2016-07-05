package controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.libs.json.Json._
import play.api.libs.json.JsValue
import play.api.mvc._

import scala.concurrent.Future
import models._
import play.Logger

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

  private def successResponse(data: JsValue, message: String) = {
    obj("status" -> "success", "data" -> data, "msg" -> message)
  }

  def list = Action.async { implicit request =>
    domainDao.findAll map { domain =>
      Ok(Json.toJson(domain))
    }
  }

  def get(id: Int) = Action.async { implicit request =>
    domainDao.findById(id) map { domain =>
      Ok(Json.toJson(domain))
    }
  }

  def create = Action.async(parse.json) { implicit request =>
    Logger.debug(request.toString())
    request.body.validate[DomainApi].map { domainApi =>
        domainDao.create(domainApi) map { did =>
          //Ok(Json.obj("id" -> did))
          Ok(successResponse(Json.toJson(Map("id" -> did)), "Domain has been created successfully."))
        }
    } recoverTotal { t =>
      Future.successful(BadRequest(Json.obj("error" -> "Wrong JSON format")))
    }
  }

  def delete(id: Int) = Action.async { implicit request =>
    Logger.debug(request.toString())
    domainDao.delete(id) map { numDeleted =>
      //Ok(Json.obj("count" -> numDeleted))
      Ok(successResponse(Json.toJson(Map("count" -> numDeleted)), "Domain has been deleted successfully."))
    }
  }

  def update(id: Int) = Action.async(parse.json) { implicit request =>
    request.body.validate[DomainApi].map { domainApi =>
      domainDao.update(id, domainApi) map { numUpdated =>
        Ok(successResponse(Json.toJson(Map("count" -> numUpdated)), "Domain has been updated successfully."))
      }
    } recoverTotal { t =>
      Future.successful(BadRequest(Json.obj("error" -> "Wring JSON Format")))
    }
  }
}
