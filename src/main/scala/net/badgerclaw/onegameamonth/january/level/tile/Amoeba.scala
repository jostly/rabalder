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
import action._

case object Amoeba extends AmoebaTile with ActionTile {
  
  override def act(x: Int, y: Int, level: ReadOnlyLevel): Seq[Action] = {
    def get(d: Offset) = level.get(x + d.dx, y + d.dy)
    def tryToGrow(d: Direction) =
      if (get(d) == Dirt || get(d).isEmpty) List(Move(d))
      else List()
      
    def canGrow(t: Tile) = (t == Space || t == Dirt)
    
    def checkGrowth() = 
      canGrow(get(Up)) || canGrow(get(Right)) || canGrow(get(Down)) || canGrow(get(Left))    

    if (level.amoebaTooLarge) List(Become(Boulder))
    else if (!level.amoebaCanGrow) List(Become(Diamond))
    else {
      if (checkGrowth()) {
        (if (level.amoebaShouldGrow) tryToGrow(level.randomDirection) else List()) :+ Become(Amoeba)
      } else {
        List()
      }
    }    
  }
    
}