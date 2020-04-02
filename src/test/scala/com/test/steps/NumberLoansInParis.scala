package com.test.steps

import com.test.services.Services
import com.test.utils.BddUtils
import cucumber.api.scala.{EN, ScalaDsl}
import io.cucumber.datatable.DataTable
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.Matchers


class NumberLoansInParis extends EN with ScalaDsl with Matchers {

  implicit val spark = SparkSession
    .builder()
    .master("local[*]")
    .getOrCreate()

  var clientDF: DataFrame = _
  var agenciesDF: DataFrame = _

  Given("""^this table which contains information about clients$""") { (table : DataTable) =>
    clientDF = BddUtils.parseDataTable(table:DataTable)
  }

  And("""^agencies table$""") { (table : DataTable) =>
    agenciesDF = BddUtils.parseDataTable(table:DataTable)
  }
  When("""^I join the two tables and compute the number of clients whose made a loan in Paris$""") { () =>

  }

  Then("""^the result$""") { (table: DataTable) =>
    val expectedLoanNumberInParis = table.cells().get(0).get(0)
    val foundLoanNumberInParis = Services.loanNumberInParis(clientDF,agenciesDF)

    assert(expectedLoanNumberInParis.toString == foundLoanNumberInParis.toString)
}
}







