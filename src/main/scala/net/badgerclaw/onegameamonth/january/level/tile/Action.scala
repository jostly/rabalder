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

case class Direction(val dx: Int, val dy: Int) {
  def +(that: Direction) = Direction(dx + that.dx, dy + that.dy)
}

object Left extends Direction(-1, 0)

object Right extends Direction(1, 0)

object Up extends Direction(0, -1)

object Down extends Direction(0, 1)


sealed abstract class Action

case class Move(direction: Direction) extends Action

case class Become(what: Tile) extends Action

case class Explode(center: Direction, remains: Tile) extends Action