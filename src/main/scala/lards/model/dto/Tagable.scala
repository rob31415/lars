/**
to strictly enforce seperation of concerns, 
an aspect must not access anything from a
transport but it's tags.

this trait helps to accomplish that restriction.
*/

package lards.model.dto


trait Tagable {
  def get_tags(): java.util.HashSet[lards.model.dto.Tag]
  def set_tags(tag: java.util.HashSet[lards.model.dto.Tag])
}
