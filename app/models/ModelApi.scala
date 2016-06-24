package models

import java.sql.Timestamp
import org.joda.time.DateTime
import play.api.libs.json._
import play.api.libs.json.Json

/**
  * Created by t.ikawa on 2016/06/23.
  */
case class AliasApi (id: Int, domainId: Int, address: String, goto: String, active: Boolean)

case class DomainApi (id: Int, domain: String, description: String, active: Boolean)

object AliasApi {
  def timestampToDateTime(t: Timestamp): DateTime = new DateTime(t.getTime)
  def dateTimeToTimestamp(dt: DateTime): Timestamp = new Timestamp(dt.getMillis)

  implicit val timestampFormat = new Format[Timestamp] {
    def writes(t: Timestamp): JsValue = Json.toJson(timestampToDateTime(t))
    def reads(json: JsValue): JsResult[Timestamp] = Json.fromJson[DateTime](json).map(dateTimeToTimestamp)
  }

  implicit val aliasFormat = Json.format[AliasApi]
}

object DomainApi {
  implicit val domainFormat = Json.format[DomainApi]
}
