package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.MySQLDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Alias.schema ++ Domain.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Alias
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param domainId Database column domain_id SqlType(INT)
   *  @param address Database column address SqlType(VARCHAR), Length(255,true), Default()
   *  @param goto Database column goto SqlType(TEXT)
   *  @param createdAt Database column created_at SqlType(TIMESTAMP)
   *  @param updatedAt Database column updated_at SqlType(DATETIME), Default(None)
   *  @param active Database column active SqlType(BIT), Default(true) */
  case class AliasRow(id: Int, domainId: Int, address: String = "", goto: String, createdAt: java.sql.Timestamp, updatedAt: Option[java.sql.Timestamp] = None, active: Boolean = true)
  /** GetResult implicit for fetching AliasRow objects using plain SQL queries */
  implicit def GetResultAliasRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Timestamp]], e4: GR[Boolean]): GR[AliasRow] = GR{
    prs => import prs._
    AliasRow.tupled((<<[Int], <<[Int], <<[String], <<[String], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<[Boolean]))
  }
  /** Table description of table alias. Objects of this class serve as prototypes for rows in queries. */
  class Alias(_tableTag: Tag) extends Table[AliasRow](_tableTag, "alias") {
    def * = (id, domainId, address, goto, createdAt, updatedAt, active) <> (AliasRow.tupled, AliasRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(domainId), Rep.Some(address), Rep.Some(goto), Rep.Some(createdAt), updatedAt, Rep.Some(active)).shaped.<>({r=>import r._; _1.map(_=> AliasRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6, _7.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column domain_id SqlType(INT) */
    val domainId: Rep[Int] = column[Int]("domain_id")
    /** Database column address SqlType(VARCHAR), Length(255,true), Default() */
    val address: Rep[String] = column[String]("address", O.Length(255,varying=true), O.Default(""))
    /** Database column goto SqlType(TEXT) */
    val goto: Rep[String] = column[String]("goto")
    /** Database column created_at SqlType(TIMESTAMP) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
    /** Database column active SqlType(BIT), Default(true) */
    val active: Rep[Boolean] = column[Boolean]("active", O.Default(true))

    /** Foreign key referencing Domain (database name alias_fk_domain) */
    lazy val domainFk = foreignKey("alias_fk_domain", domainId, Domain)(r => r.id, onUpdate=ForeignKeyAction.Cascade, onDelete=ForeignKeyAction.Cascade)
  }
  /** Collection-like TableQuery object for table Alias */
  lazy val Alias = new TableQuery(tag => new Alias(tag))

  /** Entity class storing rows of table Domain
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param domain Database column domain SqlType(VARCHAR), Length(255,true), Default()
   *  @param description Database column description SqlType(VARCHAR), Length(255,true), Default()
   *  @param createdAt Database column created_at SqlType(TIMESTAMP)
   *  @param updatedAt Database column updated_at SqlType(DATETIME), Default(None)
   *  @param active Database column active SqlType(BIT), Default(true) */
  case class DomainRow(id: Int, domain: String = "", description: String = "", createdAt: java.sql.Timestamp, updatedAt: Option[java.sql.Timestamp] = None, active: Boolean = true)
  /** GetResult implicit for fetching DomainRow objects using plain SQL queries */
  implicit def GetResultDomainRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Timestamp], e3: GR[Option[java.sql.Timestamp]], e4: GR[Boolean]): GR[DomainRow] = GR{
    prs => import prs._
    DomainRow.tupled((<<[Int], <<[String], <<[String], <<[java.sql.Timestamp], <<?[java.sql.Timestamp], <<[Boolean]))
  }
  /** Table description of table domain. Objects of this class serve as prototypes for rows in queries. */
  class Domain(_tableTag: Tag) extends Table[DomainRow](_tableTag, "domain") {
    def * = (id, domain, description, createdAt, updatedAt, active) <> (DomainRow.tupled, DomainRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(domain), Rep.Some(description), Rep.Some(createdAt), updatedAt, Rep.Some(active)).shaped.<>({r=>import r._; _1.map(_=> DomainRow.tupled((_1.get, _2.get, _3.get, _4.get, _5, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column domain SqlType(VARCHAR), Length(255,true), Default() */
    val domain: Rep[String] = column[String]("domain", O.Length(255,varying=true), O.Default(""))
    /** Database column description SqlType(VARCHAR), Length(255,true), Default() */
    val description: Rep[String] = column[String]("description", O.Length(255,varying=true), O.Default(""))
    /** Database column created_at SqlType(TIMESTAMP) */
    val createdAt: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("created_at")
    /** Database column updated_at SqlType(DATETIME), Default(None) */
    val updatedAt: Rep[Option[java.sql.Timestamp]] = column[Option[java.sql.Timestamp]]("updated_at", O.Default(None))
    /** Database column active SqlType(BIT), Default(true) */
    val active: Rep[Boolean] = column[Boolean]("active", O.Default(true))
  }
  /** Collection-like TableQuery object for table Domain */
  lazy val Domain = new TableQuery(tag => new Domain(tag))
}
