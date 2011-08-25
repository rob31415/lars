/**
*/

package lards.view.event

import lards.global.Event
import lards.model.dto.{Role => Dto}
import lards.model.dto.Dtos


class Role(override val meaning: Symbol,
           override val dtos: Dtos = new Dtos)
  extends lards.view.event.Editwindow(meaning, dtos)
