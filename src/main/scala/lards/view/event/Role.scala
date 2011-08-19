/**
*/

package lards.view.event

import lards.global.Event
import lards.model.dto.Role


class Role(override val meaning: Symbol,
           override val dto_list: Option[java.util.Set[lards.model.dto.Role]] = None)
  extends lards.view.event.Editwindow[lards.model.dto.Role](meaning, dto_list)
