package lambda.javafun.json

import org.specs2._

class ScalaJsonTest extends Specification with ScalaCheck {
  def is =
    "JNull" ! check {
      checkNull
    } ^
      "JString" ! check {
        checkString
      } ^
      "JBoolean" ! check {
        checkBoolean
      } ^
      "JNumber" ! check {
        checkNumber
      } ^
      end


  def checkNull = new JNull().toString mustEqual "null"

  def checkString = (a: String) => new JString(a).toString mustEqual a

  def checkBoolean = (a: Boolean) => new JBoolean(a).toString mustEqual a

  def checkNumber = (a: Double) => new JNumber(a).toString mustEqual a.toString

}
