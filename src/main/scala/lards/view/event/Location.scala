/**
*/

package lards.view.event

import lards.global.Event
import lards.model.dto.{Location => Dto}
import lards.model.dto.Dtos


class Location(override val meaning: Symbol,
           override val dtos: Dtos = new Dtos)
  extends lards.view.event.Editwindow(meaning, dtos)
