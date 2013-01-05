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

import scala.annotation.tailrec

class Level(data: Array[Tile]) {
  def this() = this(Level.empty)
  
  val width = 40
  val height = 22
  
  var diamondsNeeded = 12
  var diamondsWorth = 10
  var extraDiamondsWorth = 15
  var caveTime = 150
  
  var events: List[Event] = List.empty
  
  var diamondsTaken = 0
  
  var finished = false
  
  private var lastKnownPlayerPosition = (0, 0)
  
  def pollEvents = {
    val t = events
    events = List.empty
    t
  }
  
  def playerPosition = {
    val t = find(PlayerCharacter)
    if (t._1 >= 0 && t._2 >= 0) {
      lastKnownPlayerPosition = t
    }
    lastKnownPlayerPosition
  }
  
  def playerExists = find(PlayerCharacter) != (-1, -1)
  
  def get(x: Int, y: Int) = data(y*width+x)
  
  def set(x: Int, y: Int)(tile: Tile) {
    data(y*width+x) = tile
  }
  
  def find(tile: Tile): (Int, Int) = {
    for (x <- 0 until width) {
      for (y <- 0 until height) {
        if (get(x, y) == tile) return (x, y)
      }
    }
    (-1, -1)
  }
    
  def move(position: (Int, Int), direction: (Int, Int)) = {
    val newpos = (position._1 + direction._1, position._2 + direction._2)    
	val target = get(newpos._1, newpos._2)
	
	if (target == Space || target == Dirt || target == Diamond || target == Exit) {
	  set(newpos._1, newpos._2)(get(position._1, position._2))
	  set(position._1, position._2)(Space)
	  events = Remove(target, PlayerCharacter) :: Move(PlayerCharacter, target) :: events 
	  Some(target)
	} else None    
  }
  
  def tick() {
    for (x <- 1 until width-1) {
      for (y <- 1 until height-1) {
        get(x,y) match {
          case tile: CanFall => get(x, y+1) match {
            case Space => {
              set(x, y+1)(tile.falling)
              set(x, y)(Space)
            }
            case r: Rounded => {
              if (get(x-1, y) == Space && get(x-1, y+1) == Space) {
            	set(x-1, y)(tile.falling)
            	set(x, y)(Space)
              } else if (get(x+1, y) == Space && get(x+1, y+1) == Space) {
            	set(x+1, y)(tile.falling)
            	set(x, y)(Space)                  
              } else if (tile.isFalling) {
                set(x, y)(tile.stationary)                
              }
              events = Hit(tile, r) :: events
            }
            case PlayerCharacter if (tile.isFalling) => {
              for (dx <- x-1 to x+1) {
                for (dy <- y to y+2) {
                  get(dx,dy) match {
                    case _: Permanent =>
                    case _ => set(dx,dy)(Explosion(0)) 
                  }
                  
                }
              }
              finished = true
              events = Explode(PlayerCharacter, Space) :: Hit(tile, PlayerCharacter) :: events
            }
            case t => if (tile.isFalling) {
              set(x, y)(tile.stationary)
              events = Hit(tile, t) :: events
            }
          }
          case PreExit if (diamondsTaken >= diamondsNeeded) => { 
            set(x, y)(Exit)
            events = Transform(PreExit, Exit) :: events
          }
          case _ =>
        }
      }
    }
    
    for (i <- 0 until data.length) {
      data(i) = data(i) match {
        case scanned: Scanned => scanned.reset
        case animated: Animated => animated.next
        case t: Tile => t
      }
    }    
  }
  
  override def toString = {
    def split(list: List[String], size: Int, accum: List[List[String]]): List[List[String]] = list.splitAt(width) match {
      case (Nil, Nil) => accum
      case (a, b) => split(b, size, accum :+ a)
    }
    
    val tiles = split(data.toList.map(Level.stringOf(_)), width, List.empty)
    tiles.map(x => x.mkString).mkString("\n")
  }
  
}

object Level {
  def empty = convert(List.empty.padTo(22, " " * 40))
  
  def apply(map: String): Level = {
    val m = map.split("\n").toList
    val params = m.head.split(";").toList
    val level = new Level(convert(m.tail))
    
    params match {
      case growth :: diamondsWorth :: extraDiamondsWorth :: diamondsNeeded :: caveTime :: Nil => {
        level.diamondsWorth = diamondsWorth.toInt
        level.extraDiamondsWorth = extraDiamondsWorth.toInt
        level.diamondsNeeded = diamondsNeeded.toInt
        level.caveTime = caveTime.toInt
      }
      case _ => 
    }
    
    level
  }

  private def tileOf(c: Char): Tile = c match {
    case 'W' => SteelWall
    case 'w' => Wall
    case '.' => Dirt
    case 'r' => Boulder
    case 'X' => PreExit
    case 'd' => Diamond
    case 'P' => PlayerCharacter
    case _ => Space
  }
  
  private def stringOf(t: Tile): String = t match {
    case SteelWall => "W"
    case Wall => "w"
    case Dirt => "."
    case Boulder => "r"
    case FallingBoulder => "R"
    case PreExit => "x"
    case Exit => "X"
    case Diamond => "d"
    case FallingDiamond => "D"
    case PlayerCharacter => "P"
    case Space => " "
    case _ => "?"
  }
  
  private def convert(input: List[String]): Array[Tile] = {
    @tailrec
    def convertLoop(rows: List[String], accum: List[Tile]): List[Tile] = rows match {
      case x :: xs => convertLoop(xs, accum ++ convertRow(x))
      case Nil => accum
    }
    
    def convertRow(row: String): Seq[Tile] = row.map(tileOf(_))
    
    convertLoop(input, Nil).toArray
  }
}