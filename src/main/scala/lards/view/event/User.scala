/**
*/

package lards.view.event

import lards.global.Event
import lards.model.dto.{User => Dto}
import lards.model.dto.Dtos


class User(override val meaning: Symbol,
           override val dtos: Dtos = new Dtos)
  extends lards.view.event.Editwindow(meaning, dtos)
