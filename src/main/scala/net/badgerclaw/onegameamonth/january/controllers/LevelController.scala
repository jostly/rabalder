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
import net.badgerclaw.onegameamonth.january.state._

class LevelController(level: Level)(implicit context: ControllerContext) extends InputAdapter with Controller {

  private var buttons: Set[Button] = Set.empty

  private var ticksLeftOnMove = 0
  final val ticksToMove = 7
  
  private def movePlayer(dx: Int, dy: Int) {
    level.move(level.playerPosition, (dx, dy)) match {
      case Some(Diamond) => level.diamondsTaken = level.diamondsTaken + 1
      case Some(Exit) => level.finished = true
      case _ =>
    }
  }

  override def tick() {
    if (ticksLeftOnMove > 0) {
      ticksLeftOnMove = ticksLeftOnMove - 1
    } else {
      level.tick()
      if (!level.finished) {
        if (isPressed(Left)) movePlayer(-1, 0)
        else if (isPressed(Right)) movePlayer(1, 0)
        else if (isPressed(Up)) movePlayer(0, -1)
        else if (isPressed(Down)) movePlayer(0, 1)                  
      }
      ticksLeftOnMove = ticksToMove - 1
    }
  }
    
  def isPressed(button: Button): Boolean = buttons.contains(button)
    
  private def set(button: Button, state: Boolean): Boolean = {
    if (state) buttons = buttons + button
    else buttons = buttons - button
           
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

sealed abstract class Button

case object Left extends Button
case object Right extends Button
case object Up extends Button
case object Down extends Button