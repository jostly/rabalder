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

case object Boulder extends BoulderTile with ActionTile {
  override def act(x: Int, y: Int, level: ReadOnlyLevel): Seq[Action] = {
    def get(d: Direction) = level.get(x + d.dx, y + d.dy)

    get(Down) match {
      case Boulder if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Become(FallingBoulder), Move(Left))
      case Boulder if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Become(FallingBoulder), Move(Right))
      
      case Diamond if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Become(FallingBoulder), Move(Left))
      case Diamond if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Become(FallingBoulder), Move(Right))
      
      case Wall if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Become(FallingBoulder), Move(Left))
      case Wall if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Become(FallingBoulder), Move(Right))
      
      case Space => List(Become(FallingBoulder), Move(Down))
      
      case _ => List() 
    } 
  }
}

case object FallingBoulder extends BoulderTile with ActionTile {
  override def act(x: Int, y: Int, level: ReadOnlyLevel): Seq[Action] = {
    def get(d: Direction) = level.get(x + d.dx, y + d.dy)
    
    get(Down) match {
      case Boulder if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Move(Left))
      case Boulder if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Move(Right))      

      case Wall if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Move(Left))
      case Wall if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Move(Right))      

      case Diamond if (get(Left).isEmpty && get(Left+Down).isEmpty) => List(Move(Left))
      case Diamond if (get(Right).isEmpty && get(Right+Down).isEmpty) => List(Move(Right))      
      
      case Space => List(Move(Down))
      
      case explosive: ExplosiveTile => List(Explode(Down, explosive.explodeTo))
      
      case _ => List(Become(Boulder))
    }
  }
}