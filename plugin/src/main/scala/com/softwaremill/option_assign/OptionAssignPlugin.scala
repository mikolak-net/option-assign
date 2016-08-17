package com.softwaremill.option_assign


import com.softwaremill.option_assign.components.{OptionTypeComponent, AssignOperatorComponent}

import scala.tools.nsc.Global
import scala.tools.nsc.plugins.{PluginComponent, Plugin}

class OptionAssignPlugin(val global: Global) extends Plugin {
  val name: String = "optionassign"
  val components: List[PluginComponent] = List(new OptionTypeComponent(global), new AssignOperatorComponent(global))
  val description: String = "option assign - := variant"
}