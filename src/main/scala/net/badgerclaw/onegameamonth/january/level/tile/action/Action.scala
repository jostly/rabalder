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

import net.badgerclaw.onegameamonth.january.level.tile.Tile

sealed abstract class Action

/**
 * Move the tile by offset
 */
case class Move(offset: Offset) extends Action

/**
 * Become a new tile
 */
case class Become(what: Tile) extends Action

/**
 * Explode with center offset, leaving behind remains eventually
 */
case class Explode(offset: Offset, remains: Tile) extends Action

/**
 * Remove the tile at offset
 */
case class Remove(offset: Offset) extends Action

/**
 * Push the tile in the specified direction
 */
case class Push(direction: Direction) extends Action
