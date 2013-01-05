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

sealed trait Tile {
  def isEmpty = false
}

trait BoulderTile extends Tile

trait DiamondTile extends Tile

trait DirtTile extends Tile

trait SpaceTile extends Tile {
  override def isEmpty = true
}

trait PlayerCharacterTile extends Tile

trait WallTile extends Tile

trait SteelWallTile extends Tile

trait ButterflyTile extends Tile

trait ExitTile extends Tile

trait ExplosionTile extends Tile


// Remove or move these!
case object PreExit extends SteelWallTile

case object Exit extends ExitTile

case object PlayerCharacter extends PlayerCharacterTile with ExplosiveTile {
  override def explodeTo = Space
}

case object Butterfly extends ButterflyTile with ExplosiveTile {
  override def explodeTo = Diamond
}

case object FallingDiamond extends DiamondTile