package com.softwaremill

package object option_assign {
  implicit class AssignableOption[T](val opt: Option[T]) extends AnyVal {
    def ?(x: T): Option[T] = Some(x)
  }

  object ? {
    def apply[T]: Option[T] = None
  }
}
