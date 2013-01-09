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
package net.badgerclaw.onegameamonth.january.controllers

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Input.Keys
import net.badgerclaw.onegameamonth.january.level._
import net.badgerclaw.onegameamonth.january.level.tile._
import net.badgerclaw.onegameamonth.january.state._
import net.badgerclaw.onegameamonth.january.level.tile.action._

class LevelController(level: Level)(implicit context: ControllerContext) extends InputAdapter with Controller {

  private var ticksLeftOnMove = 0
  final val ticksToMove = 6
  private var removing = false
  
  override def tick() {
    if (level.playerWon) {
      if (level.time < level.caveTime) {
        level.addScore(3)
        level.addTime(1)        
      } else {
        context.forward(WinLevel)
      }
    }
    if (ticksLeftOnMove > 0) {
      ticksLeftOnMove = ticksLeftOnMove - 1
    } else {
      level.tick()
      ticksLeftOnMove = ticksToMove
    }
  }
  
  private def action(direction: Direction): PlayerAction = 
    if (removing) Remove(direction)
    else Move(direction)
    
  private def set(direction: Direction, state: Boolean): Boolean = {
    if (state) level.playerAction = Some(action(direction))
    else if (level.playerAction == Some(Move(direction)) || level.playerAction == Some(Remove(direction))) level.playerAction = None    
    
    true
  }
  
  private def setSpace(state: Boolean): Boolean = {
    removing = state
    level.playerAction match {
      case Some(Move(dir: Direction)) if (removing) => level.playerAction = Some(Remove(dir))
      case Some(Remove(dir: Direction)) if (!removing) => level.playerAction = Some(Move(dir))
      case _ =>
    }
    true
  }
    
  private def handleKey(key: Int, state: Boolean): Boolean = key match {
    case Keys.LEFT => set(Left, state)
    case Keys.RIGHT => set(Right, state)
    case Keys.UP => set(Up, state)
    case Keys.DOWN => set(Down, state)
    case Keys.SPACE => setSpace(state)
    case Keys.SHIFT_LEFT => setSpace(state)
    case Keys.SHIFT_RIGHT => setSpace(state)
    case Keys.ENTER if (state == false && level.finished && !level.playerWon) => {
      context.forward(StartLevel)
      true 
    } 
    case _ => false
  } 
    
  override def keyDown(key: Int): Boolean = handleKey(key, true)
    
  override def keyUp(key: Int): Boolean = handleKey(key, false)
  
}

