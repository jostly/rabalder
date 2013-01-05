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

sealed abstract class Tile {
  def canBeRolledOff: Boolean = false
  def isFalling: Boolean = false
  def canExplode: Boolean = true
}

trait CanFall {
  this: Tile =>
  
  def falling: Tile  
  def stationary: Tile

}

trait Rounded {
  this: Tile =>
    
  override def canBeRolledOff = true
}

trait Falling {
  this: Tile =>
    
  
  override def isFalling: Boolean = true
}

trait Scanned {
  this: Tile =>
    
  def reset: Tile
}

trait Permanent {
  this: Tile =>
    
  override def canExplode = false
}

case object Space extends Tile

case object Dirt extends Tile

case object Wall extends Tile with Rounded

case object SteelWall extends Tile with Permanent

case object Boulder extends Tile with Rounded with CanFall {
  override def falling = FallingBoulderScanned
  override def stationary = Boulder
}

case object BoulderScanned extends Tile with Scanned {
  override def reset = Boulder
}

case object FallingBoulder extends Tile with CanFall with Falling {
  override def falling = FallingBoulderScanned
  override def stationary = BoulderScanned
}

case object FallingBoulderScanned extends Tile with Scanned {
  override def reset = FallingBoulder
}

case object Diamond extends Tile with Rounded with CanFall {
  override def falling = FallingDiamondScanned
  override def stationary = Diamond
}

case object DiamondScanned extends Tile with Scanned {
  override def reset = Diamond
}

case object FallingDiamond extends Tile with CanFall with Falling {
  override def falling = FallingDiamondScanned
  override def stationary = DiamondScanned
}

case object FallingDiamondScanned extends Tile with Scanned {
  override def reset = FallingDiamond
}

case object PlayerCharacter extends Tile

case object PreExit extends Tile with Permanent

case object Exit extends Tile with Permanent


trait Animated {
  this: Tile =>
  
  def next: Tile
}

case class Explosion(stage: Int) extends Tile with Animated {
  override def next = if (stage < 4) Explosion(stage+1) else Space
}


