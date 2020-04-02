package com.test.utils

import java.util.{List => JList}
import io.cucumber.datatable.DataTable
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import scala.collection.JavaConversions._

object BddUtils {

    def parseDataTable(table: DataTable)(implicit spark: SparkSession): DataFrame = {
      val dataTableAsListOfList:List[java.util.List[String]] = table.cells().toList
      convertListToDF(dataTableAsListOfList)
    }

    def getSchema(headerList: List[String]): StructType = {

      val fields:List[StructField] =  headerList.map(header =>{
        val splitedHeader: Array[String] = header.split(":")
        val name =  splitedHeader(0)
        val dataType:DataType =  splitedHeader.size match {
          case 0 => StringType
          case 1 => StringType
          case _ => {
            val dataTypeString = splitedHeader(1)
            dataTypeString.toLowerCase.trim match {
              case "string" => StringType
              case "integer" => IntegerType
              case "boolean" => BooleanType
              case "double" => DoubleType
              case _ => StringType
            }
          }
        }
        StructField(name, dataType, true)
      })

      StructType(fields)


    }

    def convertListToDF(dataTableAsListOfList:List[JList[String]])(implicit spark: SparkSession):DataFrame = {
      val DataSchemaFromHeader= getSchema(dataTableAsListOfList.get(0).toList)
      val data:List[Row] = dataTableAsListOfList.tail.map{
        case row:JList[String] => {
          val dataSeq:Seq[Any] =  row.toList.zipWithIndex.map {
            case(elt, index) => {
              val field: StructField = DataSchemaFromHeader.fields.toList.get(index)
              field.dataType match {
                case IntegerType => elt.toInt
                case BooleanType => elt.toBoolean
                case _ => elt
              }
            }
          }
          Row.fromSeq(dataSeq)
        }
      }
      spark.createDataFrame(data, DataSchemaFromHeader)
    }
  }




