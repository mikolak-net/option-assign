package com.softwaremill.option_assign

private[option_assign] object Test extends App {

  var test = ?[String]

  println(test) //None

  test ?= "Blah"

  println(test) //Some("Blah")

}
