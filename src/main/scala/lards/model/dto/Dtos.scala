package lards.model.dto

import lards.model.dto.Dto


class Dtos(var get: Option[Seq[Dto]] = None)

//"illegal inheritance from sealed class Option". that's gay!
//class Dtos extends Option[Seq[Dto]]
