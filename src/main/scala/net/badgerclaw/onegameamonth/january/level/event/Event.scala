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
package net.badgerclaw.onegameamonth.january.level.event

import net.badgerclaw.onegameamonth.january.level.tile._

/**
 * Messages from Level to LevelView regarding what has happened.
 * 
 */
sealed abstract class Event

/**
 * Something has moved
 * 
 * Examples:
 * Moved(PlayerCharacter, Dirt) -> player has moved onto dirt
 * Moved(Boulder, _) -> a boulder starts moving
 * 
 */
case class Moved(what: Tile, onto: Tile) extends Event

/**
 * Something has exploded, and will leave behind remains
 * 
 * Example: Exploded(PlayerCharacter, Space), Explode(Butterfly, Diamond)
 */
case class Exploded(what: Tile, remains: Tile) extends Event

/**
 * A tile has been removed
 * 
 * Examples:
 * Removed(Diamond, PlayerCharacter) -> The player has collected a diamond
 * Removed(_, Amoeba) -> The amoeba has grown
 */
case class Removed(what: Tile, by: Tile) extends Event

/**
 * A tile has transformed into another
 * 
 * Examples:
 * Transformed(Amoeba, Boulder) -> The amoeba is transformed to boulders
 * Transformed(PreExit, Exit) -> The exit has opened
 * Transformed(Boulder, FallingBoulder) -> A boulder has started falling
 */
case class Transformed(what: Tile, into: Tile) extends Event


