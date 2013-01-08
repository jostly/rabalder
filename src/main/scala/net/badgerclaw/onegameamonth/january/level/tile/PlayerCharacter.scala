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

case object PlayerCharacter extends PlayerCharacterTile with ExplosiveTile with ActionTile {
  override def explodeTo = Space
  
  override def act(x: Int, y: Int, level: ReadOnlyLevel): Seq[Action] = {
    def get(d: Offset) = level.get(x + d.dx, y + d.dy)
    def canMoveOnto(t: Tile) = (t.isEmpty || t == Dirt || t == Diamond || t == FallingDiamond)
    
    level.playerAction match {
      case Some(Move(dir: Direction)) => {
	    val tile = get(dir)
	    
	    if (canMoveOnto(tile)) {
	      
	      if (tile.isEmpty) List(Move(dir))
	      else List(Remove(dir), Move(dir))
	      
	    } else if (tile == Exit) {
	      
	      List(Become(PlayerCharacterExited), Move(dir))
	      
	    } else if ((dir == Left || dir == Right) && 
	        tile == Boulder && 
	        get(dir+dir).isEmpty && 
	        level.randomFloat <= 0.125f) {
	     
	      List(Push(dir), Move(dir))
	    } else {
	      List()
	    }
      }
      case Some(Remove(dir: Direction)) => {
	    val tile = get(dir)
	    
	    if (canMoveOnto(tile) && !tile.isEmpty) List(Remove(dir))
	    else List()
      }
      case _ => List()
    }
  }
}

case object PlayerCharacterExited extends PlayerCharacterTile
