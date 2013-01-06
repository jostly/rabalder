/*
Copyright 2013 Johan Östling

This file is part of Rabalder.

Rabalder is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.badgerclaw.onegameamonth.january.level.tile

import net.badgerclaw.onegameamonth.january.level.ReadOnlyLevel
import net.badgerclaw.onegameamonth.january.level.tile.action.ActionTile
import net.badgerclaw.onegameamonth.january.level.tile.action.Become

case class Explosion(stage: Int, remains: Tile) extends ExplosionTile with ActionTile {
  assert(stage >= 1 && stage <= 5, "stage must be between 1 and 5")
  
  override def act(x: Int, y: Int, level: ReadOnlyLevel) = 
    if (stage < 5) List(Become(Explosion(stage+1, remains)))
    else List(Become(remains))
}
