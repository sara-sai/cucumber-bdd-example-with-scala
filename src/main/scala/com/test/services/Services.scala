package com.test.services

import org.apache.spark.sql.{DataFrame, SparkSession}

object Services {

  def loanNumberInParis(df1: DataFrame, df2: DataFrame)(implicit spark : SparkSession ): Long ={

    df1.join(df2, df1("agencyId") === df2("agencyId"))
       .drop("agencyId")
       .filter("loan == 'true'")
       .filter("agencyLocation == 'Paris'")
       .count()
  }
}
