package com.test.runner

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  plugin =Array("json","html:FeaturesReport"),
  features = Array("src/test/resources/features/"),
  glue = Array("classpath:com.test.spark.wiki.steps")
)
class Runner



