package extensions

import kotlinx.css.LinearDimension

val Number.rem: LinearDimension get() = LinearDimension(if (this == 0) "0" else this.toString() + "rem")