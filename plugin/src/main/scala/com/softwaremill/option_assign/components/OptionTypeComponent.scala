package com.softwaremill.option_assign.components

import scala.reflect.api.Trees
import scala.tools.nsc.Global
import scala.tools.nsc.plugins.PluginComponent
import scala.tools.nsc.transform.Transform

class OptionTypeComponent(val global: Global) extends PluginComponent with Transform {
  protected def newTransformer(unit: global.CompilationUnit) = OptionTypeTransformer

  val runsAfter: List[String] = List("parser")
  override val runsBefore = List[String](AssignOperatorComponent.Name)
  val phaseName: String = "option_assign_none_type"

  import global._

  object OptionTypeTransformer extends Transformer {
    override def transform(tree: global.Tree) = {
      val transformed = super.transform(tree)
      transformed match {
        case q"var $ident:$defType.? = _" => {
          val optionType = rewriteTypeToOption(defType)
          q"var $ident:$optionType = scala.None"
        }
        case q"val $ident:$defType.? = null" => {
          val optionType = rewriteTypeToOption(defType)
          q"val $ident:$optionType = scala.None"
        }
        case _ => transformed
      }
    }

    def rewriteTypeToOption(defType: Trees#Tree): global.Tree = {
      val defTypeName = defType match {
        case Ident(TermName(term)) => term
      }
      val internOptionType = TypeName(defTypeName)
      tq"scala.Option[$internOptionType]"
    }
  }
}
