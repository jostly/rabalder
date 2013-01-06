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
import net.badgerclaw.onegameamonth.january.level.tile.action.Action

case object Diamond extends DiamondTile with ActionTile with FallingTile {
  
  override def act(x: Int, y: Int, level: ReadOnlyLevel): Seq[Action] = 
    checkFalling(x, y, level, FallingDiamond)
    
}