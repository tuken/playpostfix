package models

import play.api.libs.json.Json

/**
  * Created by t.ikawa on 2016/06/23.
  */
case class AliasApi (id: Int, domainId: Int, address: String, goto: String, createdAt: java.sql.Timestamp, updatedAt: Option[java.sql.Timestamp], active: Boolean)

case class DomainApi (id: Int, domain: String, description: String, createdAt: java.sql.Timestamp, updatedAt: Option[java.sql.Timestamp], active: Boolean)

object AliasApi {
  implicit val aliasFormat = Json.format[AliasApi]
}

object DomainApi {
  implicit val domainFormat = Json.format[DomainApi]
}
