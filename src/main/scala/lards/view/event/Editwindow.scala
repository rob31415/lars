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


class Editwindow[Dto](override val meaning: Symbol,
           val dto_list: Option[java.util.Set[Dto]] = None)
  extends Event
