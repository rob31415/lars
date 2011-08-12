/**
is sent when
-selection in table changes
-save button clicked
-delete button clicked
-window geo changes
-editing starts
-row filter selection changes
-col-setup selection changes
*/

package lards.view.event

import lards.global.Event
import lards.model.dto.Role


class Role(override val meaning: Symbol,
           val dto_list: Option[java.util.Set[lards.model.dto.Role]] = None)
  extends Event
