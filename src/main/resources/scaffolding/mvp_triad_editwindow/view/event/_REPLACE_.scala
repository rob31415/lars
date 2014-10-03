/**
*/

package lars.view.event

import lars.global.Event
import lars.model.dto.{_REPLACE_ => Dto}
import lars.model.dto.Dtos


class _REPLACE_(override val meaning: Symbol,
           override val dtos: Dtos = new Dtos)
  extends lars.view.event.Editwindow(meaning, dtos)
