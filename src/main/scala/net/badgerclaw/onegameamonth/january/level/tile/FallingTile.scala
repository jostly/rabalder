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
import net.badgerclaw.onegameamonth.january.level.tile.action._

trait FallingTile {
  this: Tile =>
    
  def checkFalling(x: Int, y: Int, level: ReadOnlyLevel, tileInMotion: Tile): Seq[Action] = {
    def get(d: Offset) = level.get(x + d.dx, y + d.dy)

    get(Down) match {
      case _: RoundedTile if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Become(tileInMotion), Move(Left))
      case _: RoundedTile if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Become(tileInMotion), Move(Right))
            
      case _: SpaceTile => List(Become(tileInMotion), Move(Down))
      
      case _ => List() 
    } 
  }
  
  def continueFalling(x: Int, y: Int, level: ReadOnlyLevel, tileAtRest: Tile): Seq[Action] = {
    def get(d: Offset) = level.get(x + d.dx, y + d.dy)
    
    get(Down) match {
      case _: RoundedTile if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Move(Left))
      case _: RoundedTile if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Move(Right))      

      case _: SpaceTile => List(Move(Down))
      
      case explosive: ExplosiveTile => List(Explode(Down, explosive.explodeTo))
      
      case _: MagicWallTile => {
        val otherKind = this match {
          case _: BoulderTile => FallingDiamond
          case _: DiamondTile => FallingBoulder
          case _ => this
        }
        if (get(Down+Down).isEmpty) List(Become(otherKind), Move(Down + Down))
        else List(Become(otherKind), Become(Space))
      }
      
      case _ => List(Become(tileAtRest))
    }
  }
  

}