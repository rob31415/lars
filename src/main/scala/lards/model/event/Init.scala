package lards.model.event

import lards.global.Event


class Init(override val meaning: Symbol = 'undefined) extends Event /*extends Model*/ {
}
