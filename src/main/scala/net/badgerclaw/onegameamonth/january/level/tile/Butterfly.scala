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

import net.badgerclaw.onegameamonth.january.level.tile.action._

import net.badgerclaw.onegameamonth.january.level.ReadOnlyLevel

case class Butterfly(direction: Direction) extends ButterflyTile with ExplosiveTile with ActionTile {
  override def explodeTo = Diamond
  
  override def act(x: Int, y: Int, level: ReadOnlyLevel) = {
    def get(d: Delta) = level.get(x + d.dx, y + d.dy)
    def hasNeighbor(f: Tile => Boolean): Boolean =
      f(get(Down)) || f(get(Left)) || f(get(Up)) || f(get(Right))
        
    if (hasNeighbor( _ == PlayerCharacter )) {
      
      List(Explode(Delta(0,0), explodeTo))
      
    } else if (get(direction.turnRight).isEmpty) {
      
      List(Become(Butterfly(direction.turnRight)), Move(direction.turnRight))
      
    } else if(get(direction.ahead).isEmpty) {
      
      List(Move(direction.ahead))
      
    } else {
      
      List(Become(Butterfly(direction.turnLeft)))
      
    }
  }  
}
