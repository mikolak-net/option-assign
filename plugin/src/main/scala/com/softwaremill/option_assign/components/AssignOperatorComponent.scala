package com.softwaremill.option_assign.components

import scala.tools.nsc.Global
import scala.tools.nsc.plugins.PluginComponent
import scala.tools.nsc.transform.Transform

class AssignOperatorComponent(val global: Global) extends PluginComponent with Transform {
  protected def newTransformer(unit: global.CompilationUnit) = AssignOperatorTransformer

  val runsAfter: List[String] = List("parser")
  override val runsBefore = List[String]("namer")
  val phaseName: String = AssignOperatorComponent.Name

  import global._

  object AssignOperatorTransformer extends Transformer {
    override def transform(tree: global.Tree) = {
      val transformed = super.transform(tree)
      transformed match {
        case q"$ident := $value" => q"""$ident = scala.Some($value)"""
        case _ => transformed
      }
    }
  }
}

object AssignOperatorComponent {
  val Name = "option_assign_assign_operator"
}
