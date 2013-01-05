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
 * Move(PlayerCharacter, Dirt) -> player has moved onto dirt
 * Move(Boulder, _) -> a boulder starts moving
 * 
 */
case class Move(what: Tile, onto: Tile) extends Event

/**
 * Something hits something else (that is not Space)
 * 
 * Examples:
 * Hit(FallingDiamond, _) -> a diamond has fallen and hit something other than Space
 * Hit(FallingBoulder, Firefly) -> a falling boulder has hit a firefly 
 */
case class Hit(what: Tile, where: Tile) extends Event

/**
 * Something has exploded, and will leave behind remains
 * 
 * Example: Explode(PlayerCharacter, Space), Explode(Butterfly, Diamond)
 */
case class Explode(what: Tile, remains: Tile) extends Event

/**
 * A tile has been removed
 * 
 * Examples:
 * Remove(Diamond, PlayerCharacter) -> The player has collected a diamond
 * Remove(_, Amoeba) -> The amoeba has grown
 */
case class Remove(what: Tile, by: Tile) extends Event

/**
 * A tile has transformed into another
 * 
 * Examples:
 * Transform(Amoeba, Boulder) -> The amoeba is transformed to boulders
 * Transform(PreExit, Exit) -> The exit has opened
 */
case class Transform(what: Tile, into: Tile) extends Event


