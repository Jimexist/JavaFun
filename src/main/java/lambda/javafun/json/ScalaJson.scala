package lambda.javafun.json

abstract class JValue {
  def toString: String

  private def commaJoin(sb: StringBuilder, s: String): StringBuilder = sb.append(", ").append(s)

  protected def wrapJoin(values: Seq[String], begin: String, end: String): String =
    values match {
      case Nil => begin + end
      case hd :: tl => begin + tl.foldLeft(new StringBuilder(hd))(commaJoin).toString + end
    }
}

object JValue {
  def parseJValue(str: String): JValue = ???

}

case class JObject(val values: Seq[(String, JValue)]) extends JValue {
  override def toString = wrapJoin(values.map(p => "\"" + p._1 + "\":" + p._2.toString), "{", "}")
}

case class JArray(val values: Seq[JValue]) extends JValue {
  override def toString = wrapJoin(values.map(_.toString), "[", "]")
}

case class JNumber(val value: Double) extends JValue {
  override def toString = value.toString
}

case class JString(val value: String) extends JValue {
  override def toString = value
}

case class JBoolean(val value: Boolean) extends JValue {
  override def toString = value.toString
}

case class JNull() extends JValue {
  override def toString = "null"
}