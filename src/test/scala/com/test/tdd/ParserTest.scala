package com.test.tdd

import com.test.utils.BddUtils
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.scalatest.FlatSpec

class ParserTest extends FlatSpec {
  implicit  val  spark: SparkSession=SparkSession
    .builder()
    .master("local[*]")
    .getOrCreate()

  "the extracted schema from the header " should "ok" in {
    //Given
    val header: List[String] = List("a:Integer", "b", "", "d: Double")
    val expected: StructType = StructType(List(
      StructField("a", IntegerType, true),
      StructField("b", StringType, true),
      StructField("", StringType, true),
      StructField("d", DoubleType, true)))

    //When
    val result = BddUtils.getSchema(header)

    //then
    assert(expected == result)
  }
}

