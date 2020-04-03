# BDD APPROACH                                                            

## What is a BDD?                                      
BDD(Behavior driven development) is a very interesting  practice which consists on writing tests before the code implementation,  is progressively adopted by developers. Not only developers! justly, I think the strong point of BDD is the fact that is a tool which allows a collaboration between developers and non-technical teams, so it is a way to work together by using a common language which helps in understanding more the behavior of the application and planify what to do. So it is rather functional than technical test like TDD(Test Driven Development).

## Cucumber BDD:  step by step !
Cucumber is a framework that supports BDD, it uses the Gherkin language  for describing the test cases.
In order to carry out a cucumber BDD we should start by understanding how it functions, the three important notions to understand are: features, steps, and Runner.

The functional needs are expressed in what we call Features, written by all those involved in the project, even tech or non-tech, each one imagines and suggests all possible scenarios, A feature is a set of scenarios and in each scenario we find a description of a functionality, using a bloc of :
Describe the input data or the actual context (Given)
Describe the method (When)
Describe the expected result (Then)
for this purpose, cucumber gives as the possibility to use files .feature.

Each feature has its own steps, in a step we call the appropriate methods, and we also should find a bloc of Given/When/Then with the same description as the according scenario ( in order to make the link between them) .

And finally, a Runner which allows us to run the test  with desired options. 


## BDD Benefits!
In addition to encouraging collaboration,  thinking functional, ensuring the quality of results, provided by BDD, They also are a source of documentation since they allow us to generate  documents (html, json,Pretty,..) for all the scenarios.

## Practical example (Should use java 1.8 in (file -> project structure -> java 1.8))
After making a general view of BDD tests above, Now, I’ll illustrate how it works in practice with a simple example using Scala. ( you can find this example in my github). So let’s practise!

prerequisite : 
I create a maven project in Intellij with Scala, and I use the next versions (cucumber: 4.7.1,  gherkin: 2.12.2, scala: 2.11).
1- Install Cucumber plugin & gherkin one in Intellij
2- add all the cucumber necessary dependencies to the POM

In this example, I suggest that I have a table of clients information and agencies one, And I want to compute the number of loan in Paris, for this purpose before implementing the code, I first perform a BDD test, 
First of all, In src/Test/resources I define the .feature file, the data is passed as cucumber table 
Feature: Loans

      Scenario:  Get the number of loans made in Paris
          Given this table which contains information about clients
          | clientId: Integer | loan: Boolean | agencyId: Integer   |
          |  01               | true          | 01                  |
          |  02               | false         | 01                  |
          |  03               | true          | 02                  |
          |  04               | false         | 02                  |
          |  05               | true          | 01                  |

          And agencies table
          | agencyId: Integer | agencyLocation: String           |
          | 01                | Paris                            |
          | 02                | Lyon                             |

          When I join the two tables and compute the number of clients whose made a loan in Paris

          Then the result
          |2|

I’ll create a scala class in which I’ll execute my functions, and make the assertion that the result is the same thing as the expected value. but before that I have created a method that allows me to convert a cucumber table to a dataframe to make the processing more easier. 

The second step is to define the appropriate step (in the src/test/scala/steps), as next:

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


In order to execute the test I create a Runner with specifying the feature and the step to execute:

      @RunWith(classOf[Cucumber])
      @CucumberOptions(
        plugin =Array("json","html:FeaturesReport"),
        features = Array("src/test/resources/features/"),
        glue = Array("classpath:com.test.spark.wiki.steps")
      )
      class Runner

## Generate documentation

There are a several reporter plugins that we can use in cucumber for example : Pretty, html, json,.. 
In the runner  the option plugin = Array(“json”,”html:FeaturesReport”) allows us to generate json and html documentation while the execution.




## Conclusion:
To conclude, The first-test approach BDD and also TDD, is a very useful process. Proceeding by carrying out  these tests before implementing methods,helps in the understanding of the global behavior of the software, and make the user more efficient.
