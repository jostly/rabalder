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
package net.badgerclaw.onegameamonth.january.level.tile.action

sealed trait Direction extends Offset {
  def turnRight: Direction
  
  def turnLeft: Direction
  
  def ahead: Direction = this
}

object Left extends Delta(-1, 0) with Direction {
  override val turnRight = Up
  override val turnLeft  = Down
  override def toString = "Left"
}

object Right extends Delta(1, 0) with Direction {
  override val turnRight = Down
  override val turnLeft  = Up
  override def toString = "Right"
}

object Up extends Delta(0, -1) with Direction {
  override val turnRight = Right
  override val turnLeft  = Left
  override def toString = "Up"
}

object Down extends Delta(0, 1) with Direction {
  override val turnRight = Left
  override val turnLeft  = Right
  override def toString = "Down"
}
