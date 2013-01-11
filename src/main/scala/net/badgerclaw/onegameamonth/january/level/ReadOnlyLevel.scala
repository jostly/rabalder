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
package net.badgerclaw.onegameamonth.january.level

import net.badgerclaw.onegameamonth.january.level.tile.Tile
import net.badgerclaw.onegameamonth.january.level.tile.action._

trait ReadOnlyLevel {
  def get(x: Int, y: Int): Tile
  
  def diamondsNeeded: Int
  
  def diamondsTaken: Int
  
  def amoebaTooLarge: Boolean
  
  def amoebaCanGrow: Boolean
  
  def amoebaShouldGrow: Boolean
  
  def randomDirection: Direction
  
  def randomFloat: Float
  
  def playerAction: Option[PlayerAction]
  
  def magicWallHasExpired: Boolean
  
  def magicWallIsActive: Boolean
}