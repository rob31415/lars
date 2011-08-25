/**
*/

package lards.view.event

import lards.global.Event
import lards.model.dto.{_REPLACE_ => Dto}
import lards.model.dto.Dtos


class _REPLACE_(override val meaning: Symbol,
           override val dtos: Dtos = new Dtos)
  extends lards.view.event.Editwindow(meaning, dtos)
