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
  
  override def tick() {
    if (ticksLeftOnMove > 0) {
      ticksLeftOnMove = ticksLeftOnMove - 1
    } else {
      if (!level.finished) {
      }
      level.tick()
      ticksLeftOnMove = ticksToMove
    }
  }
    
  private def set(direction: Direction, state: Boolean): Boolean = {
    if (state) level.movementDirection = Some(direction)
    else if (level.movementDirection == Some(direction)) level.movementDirection = None
    
    true
  }
    
  private def handleKey(key: Int, state: Boolean): Boolean = key match {
    case Keys.LEFT => set(Left, state)
    case Keys.RIGHT => set(Right, state)
    case Keys.UP => set(Up, state)
    case Keys.DOWN => set(Down, state)
    case Keys.ENTER if (state == false && level.finished) => context.forward(GameExit); true 
    case _ => false
  } 
    
  override def keyDown(key: Int): Boolean = handleKey(key, true)
    
  override def keyUp(key: Int): Boolean = handleKey(key, false)
  
}

