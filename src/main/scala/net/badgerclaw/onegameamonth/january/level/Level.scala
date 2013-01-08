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
import scala.util.Random
import event._
import tile._
import net.badgerclaw.onegameamonth.january.level.tile.action._

class Level(data: Array[Tile]) extends ReadOnlyLevel {
  def this() = this(Level.empty)
  
  val width = 40
  val height = 22
  
  var diamondsNeeded = 12
  var diamondsWorth = 10
  var extraDiamondsWorth = 15
  var caveTime = 150
  var ticksElapsed = 0
  
  def time = ticksElapsed / 10
  
  var events: List[Event] = List.empty
  
  var diamondsTaken = 0
  
  var finished = false
  
  var amoebaTooLarge = false
  
  var amoebaCanGrow = true
  
  var slowGrowth = 20
  
  var playerAction: Option[PlayerAction] = None
  
  private var lastKnownPlayerPosition = (0, 0)
  
  private val random = new Random()
  
  def randomFloat = random.nextFloat
  
  def amoebaShouldGrow: Boolean = randomFloat <= (if (time > slowGrowth) 0.25f else 0.03125f)
  
  def randomDirection = random.nextInt(4) match {
    case 0 => Up
    case 1 => Right
    case 2 => Down
    case 3 => Left
  }
  
  def pollEvents = {
    val t = events
    events = List.empty
    t
  }
  
  private def addEvent(e: Event) {
    events ::= e
  }
  
  def playerPosition = {
    val t = findPlayer()
    if (t._1 >= 0 && t._2 >= 0) {
      lastKnownPlayerPosition = t
    }
    lastKnownPlayerPosition
  }
  
  def playerExists = findPlayer() != (-1, -1)
  
  def get(x: Int, y: Int) = data(y*width+x)
  
  def set(x: Int, y: Int)(tile: Tile) {
    data(y*width+x) = tile
  }

  private def findPlayer(): (Int, Int) = {
    for (x <- 0 until width) {
      for (y <- 0 until height) {
        get(x, y) match {
          case _: PlayerCharacterTile => return (x, y)
          case _ =>
        }
      }
    }
    (-1, -1)
  }
  
  def tick() {
    var lastAmoebaCount = 0
    var amoebaCouldGrow = false
    var excluded = Set.empty[(Int, Int)]
    for (y <- 0 until height) {
      for (x <- 0 until width if (!excluded.contains((x,y)))) {
        def exclude(ex: Int, ey: Int) {
          if (ey > y || (ey == y) && (ex > x)) {
        	excluded += ((ex, ey))
          }
        }
        val tile = get(x,y)
        if (tile == Amoeba) lastAmoebaCount += 1
        
        tile match {
          case action: ActionTile => action.act(x, y, this).foreach( {
            case Move(direction) => {
              val current = get(x, y)
              val tx = x + direction.dx
              val ty = y + direction.dy
              val destination = get(tx, ty)
              addEvent(Moved(current, destination))
              set(tx, ty)(current)
              set(x, y)(Space)
              exclude(tx, ty)
              if (current == PlayerCharacterExited && destination == Exit) {
                finished = true
              }
            }
            case Become(what) => {
              addEvent(Transformed(get(x, y), what))
              set(x, y)(what)
              if (what == Amoeba) {
                amoebaCouldGrow = true
              }
            }
            case Explode(direction, remains) => {
              addEvent(Exploded(get(x, y), remains))
              for (explosionX <- -1 to 1) {
                for (explosionY <- -1 to 1) {
                  val ex = x + direction.dx + explosionX
                  val ey = y + direction.dy + explosionY
                  val tile = get(ex, ey)
                  if (tile.canBeDestroyed) {
                    if (tile == PlayerCharacter) finished = true
                    
                    set(ex, ey)(Explosion(1, remains))                    
                    exclude(ex, ey)
                  }
                }
              }
            }
            case Remove(direction) => {
              val rx = x + direction.dx
              val ry = y + direction.dy
              (tile, get(rx, ry)) match {
                case (_: PlayerCharacterTile, _: DiamondTile) => {
                  diamondsTaken += 1
                }
                case _ =>
              }
              addEvent(Removed(get(rx, ry), tile))
              set(rx, ry)(Space)
            }
            case Push(direction) => {
              val tx = x + direction.dx
              val ty = y + direction.dy
              val pushedTile = get(tx, ty)
              addEvent(Moved(pushedTile, get(tx + direction.dx, ty + direction.dy)))
              set(tx + direction.dx, ty + direction.dy)(pushedTile)
              set(tx, ty)(Space)
              exclude(tx + direction.dx, ty + direction.dy)
            }
          })
          case _ =>
        }
      }
    }   
    ticksElapsed += 1
    if (time >= caveTime) {
      finished = true
    }
    if (lastAmoebaCount >= 200) amoebaTooLarge = true
    amoebaCanGrow = amoebaCouldGrow
  }
  
  def toDebugString = {
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
        level.slowGrowth = growth.toInt
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
    case 'X' => PlayerCharacter
    case 'd' => Diamond
    case 'B' => Butterfly(Down)
    case 'q' => Firefly(Left)
    case 'a' => Amoeba
    case 'P' => PreExit
    case _ => Space
  }
  
  private def stringOf(t: Tile): String = t match {
    case SteelWall => "W"
    case Wall => "w"
    case Dirt => "."
    case Boulder => "r"
    case FallingBoulder => "R"
    case PreExit => "P"
    case Exit => "∏"
    case Diamond => "d"
    case FallingDiamond => "D"
    case Butterfly(_) => "B"
    case Firefly(_) => "q"
    case Amoeba => "a"
    case PlayerCharacter => "X"
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