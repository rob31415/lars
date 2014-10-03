/**
*/

package lars.view.event

import lars.global.Event
import lars.model.dto.{Role => Dto}
import lars.model.dto.Dtos


class Role(override val meaning: Symbol,
           override val dtos: Dtos = new Dtos)
  extends lars.view.event.Editwindow(meaning, dtos)
