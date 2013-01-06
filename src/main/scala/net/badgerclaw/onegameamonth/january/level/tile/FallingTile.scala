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
import net.badgerclaw.onegameamonth.january.level.tile.action.Move
import net.badgerclaw.onegameamonth.january.level.tile.action.Explode
import net.badgerclaw.onegameamonth.january.level.tile.action.Become
import net.badgerclaw.onegameamonth.january.level.tile.action.Action
import net.badgerclaw.onegameamonth.january.level.tile.action.Right
import net.badgerclaw.onegameamonth.january.level.tile.action.Left
import net.badgerclaw.onegameamonth.january.level.tile.action.Down
import net.badgerclaw.onegameamonth.january.level.tile.action.Delta

trait FallingTile {
  this: Tile =>
    
  def checkFalling(x: Int, y: Int, level: ReadOnlyLevel, tileInMotion: Tile): Seq[Action] = {
    def get(d: Delta) = level.get(x + d.dx, y + d.dy)

    get(Down) match {
      case _: RoundedTile if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Become(tileInMotion), Move(Left))
      case _: RoundedTile if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Become(tileInMotion), Move(Right))
            
      case _: SpaceTile => List(Become(tileInMotion), Move(Down))
      
      case _ => List() 
    } 
  }
  
  def continueFalling(x: Int, y: Int, level: ReadOnlyLevel, tileAtRest: Tile): Seq[Action] = {
    def get(d: Delta) = level.get(x + d.dx, y + d.dy)
    
    get(Down) match {
      case _: RoundedTile if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Move(Left))
      case _: RoundedTile if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Move(Right))      

      case _: SpaceTile => List(Move(Down))
      
      case explosive: ExplosiveTile => List(Explode(Down, explosive.explodeTo))
      
      case _ => List(Become(tileAtRest))
    }
  }

}