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
import lards.model.dto.Dtos


class Editwindow(override val meaning: Symbol,
           val dtos: Dtos)
  extends Event
