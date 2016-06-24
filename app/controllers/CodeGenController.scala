package controllers

import javax.inject.Inject

import play.api.mvc._
import slick.codegen.SourceCodeGenerator
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import slick.{model => m}
import slick.model.{Column, Model, Table}
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

/**
  * Created by t.ikawa on 2016/06/24.
  */
class CustomSourceCodeGenerator(model: m.Model) extends SourceCodeGenerator(model) {

  override def code = "import com.github.tototoshi.slick.MySQLJodaSupport._\n" + "import org.joda.time.DateTime\n" + super.code
  override def Table = new Table(_) {
    override def autoIncLastAsOption = true
    override def Column = new Column(_) {
      override def rawType = model.tpe match {
        case "java.sql.Timestamp" => "DateTime" // kill j.s.Timestamp
        case "java.sql.Date" => "DateTime" // kill j.s.Timestamp
        case _ => {
          //println(s"${model.table.table}#${model.name} tpe=${model.tpe} rawType=${super.rawType}")
          super.rawType
        }
      }
    }
  }
}

class CodeGenController @Inject() extends Controller {
  def index = Action {
    val slickDriver = "slick.driver.MySQLDriver"
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost/pfix"
    val outputFolder = "app"
    val pkg = "models"
    val user = "root"
    val password = "2003Flower1101"
    val driver: JdbcProfile = slick.driver.MySQLDriver
    val db = { Database.forURL(url, driver = jdbcDriver, user = user, password = password) }

    // 非同期をぶった斬って処理する
    val model = Await.result(db.run(driver.createModel(None, false)(ExecutionContext.global).withPinnedSession), Duration.Inf)

    // 登録日(create_at)と更新日(update_at)はMySql側の設定で対応するのでTablesからは除外する
    val ts = (for {
      t <- model.tables
      c = t.columns.filter(_.name != "created_at").filter(_.name != "updated_at")
    } yield(slick.model.Table(t.name, c, t.primaryKey, t.foreignKeys, t.indices, t.options)))
    val fModel = Model(tables = ts)

    val codeGenFuture = new CustomSourceCodeGenerator(fModel).writeToFile(slickDriver, outputFolder , pkg, "Tables", "Tables.scala")

    Ok(views.html.index("Finish code generating!"))
  }
}
