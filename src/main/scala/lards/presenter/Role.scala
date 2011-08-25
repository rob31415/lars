/**
*/

package lards.presenter

import lards.global.Applocal
import lards.presenter.Editwindow


class Role(view: lards.view.Role, model: lards.model.service.Role)
  extends Editwindow(view, model, 'role)
