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

import com.badlogic.gdx.Input.Keys
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers.{isA, any}
import net.badgerclaw.onegameamonth.january.level._
import net.badgerclaw.onegameamonth.january.level.tile._
import net.badgerclaw.onegameamonth.january.level.tile.action._
import net.badgerclaw.onegameamonth.january.state._

class LevelControllerSpec extends WordSpec with ShouldMatchers with MockitoSugar {

  implicit val defaultContext = mock[ControllerContext]
    
  "LevelController" when {
    "left is pressed" should {      
      "set left movement in the level" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.LEFT)
        
        verify(level).playerAction_=(Some(Move(Left)))
      }
    }
    "right is pressed" should {      
      "set right movement in the level" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.RIGHT)
        
        verify(level).playerAction_=(Some(Move(Right)))
      }
    }
    "up is pressed" should {      
      "set up movement in the level" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.UP)
        
        verify(level).playerAction_=(Some(Move(Up)))
      }
    }
    "down is pressed" should {      
      "set down movement in the level" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.DOWN)
        
        verify(level).playerAction_=(Some(Move(Down)))
      }
    }    
    "left is released" should {
      val key = Keys.LEFT
      val dir = Left
      
      "remove movement in the level if movement was in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.playerAction).thenReturn(Some(Move(dir)))
        
        controller.keyUp(key)
        
        verify(level).playerAction_=(None)        
      }
      "not change movement in the level if movement was not in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.playerAction).thenReturn(Some(Move(dir.turnLeft)))
        
        controller.keyUp(key)
        
        verify(level, never()).playerAction_=(any())        
      }
    }
    "right is released" should {
      val key = Keys.RIGHT
      val dir = Right
      
      "remove movement in the level if movement was in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.playerAction).thenReturn(Some(Move(dir)))
        
        controller.keyUp(key)
        
        verify(level).playerAction_=(None)        
      }
      "not change movement in the level if movement was not in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.playerAction).thenReturn(Some(Move(dir.turnLeft)))
        
        controller.keyUp(key)
        
        verify(level, never()).playerAction_=(any())        
      }
    }
    "up is released" should {
      val key = Keys.UP
      val dir = Up
      
      "remove movement in the level if movement was in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.playerAction).thenReturn(Some(Move(dir)))
        
        controller.keyUp(key)
        
        verify(level).playerAction_=(None)        
      }
      "not change movement in the level if movement was not in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.playerAction).thenReturn(Some(Move(dir.turnLeft)))
        
        controller.keyUp(key)
        
        verify(level, never()).playerAction_=(any())        
      }
    }
    "down is released" should {
      val key = Keys.DOWN
      val dir = Down
      
      "remove movement in the level if movement was in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.playerAction).thenReturn(Some(Move(dir)))
        
        controller.keyUp(key)
        
        verify(level).playerAction_=(None)        
      }
      "not change movement in the level if movement was not in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.playerAction).thenReturn(Some(Move(dir.turnLeft)))
        
        controller.keyUp(key)
        
        verify(level, never()).playerAction_=(any())        
      }
    }
    "space is pressed" should {
      "do nothing if nothing else is pressed" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.SPACE)
        
        verify(level, never()).playerAction_=(any())        
      }
      "change movement to removing" in {
        val level = mock[Level]
        val controller = new LevelController(level)

        when(level.playerAction).thenReturn(Some(Move(Left)))

        controller.keyDown(Keys.SPACE)
        
        verify(level).playerAction_=(Some(Remove(Left)))        
      }
    }
    "space is pressed, then left" should {
      "set player action to Remove(Left)" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.SPACE)
        controller.keyDown(Keys.LEFT)
        
        verify(level).playerAction_=(Some(Remove(Left)))                
      }
    }
    "space is pressed, then left, then right" should {
      "set player action to Remove(Left)" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.SPACE)
        controller.keyDown(Keys.LEFT)
        
        verify(level).playerAction_=(Some(Remove(Left)))
        when(level.playerAction).thenReturn(Some(Remove(Left)))
        
        controller.keyDown(Keys.RIGHT)

        verify(level).playerAction_=(Some(Remove(Right)))
        
      }
    }
    "space is pressed, then left, then space is released" should {
      "set player action to Remove(Left)" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.SPACE)
        controller.keyDown(Keys.LEFT)
        
        verify(level).playerAction_=(Some(Remove(Left)))
        when(level.playerAction).thenReturn(Some(Remove(Left)))
        
        controller.keyUp(Keys.SPACE)

        verify(level).playerAction_=(Some(Move(Left)))
        
      }
    }
    "the level is finished and the player won" should {
      "decrease time remaining by 1 and increase score by 3 every tick" in {
        val context = mock[ControllerContext]
        val level = mock[Level]
        when(level.playerWon).thenReturn(true)
        when(level.time).thenReturn(100)
        when(level.caveTime).thenReturn(105)
        
        val controller = new LevelController(level)(context)
        
        controller.tick()
        
        verify(level).addScore(3)
        verify(level).addTime(1)
      }
      "forward to WinLevel when time is up" in {
        val context = mock[ControllerContext]
        val level = mock[Level]
        when(level.playerWon).thenReturn(true)
        when(level.time).thenReturn(80)
        when(level.caveTime).thenReturn(80)
        
        val controller = new LevelController(level)(context)
        
        controller.tick()
        
        verify(context).forward(WinLevel)
      }
    }
    "ESC is pressed" should {
      "decrease time remaining by 1 per tick" in {
        val context = mock[ControllerContext]
        val level = mock[Level]
        when(level.playerWon).thenReturn(false)
        when(level.finished).thenReturn(false)

        val controller = new LevelController(level)(context)
        controller.keyDown(Keys.ESCAPE)
        
        controller.tick()
        
        verify(level).addTime(1)
      }
    }
    "ESC is not pressed" should {
      "NOT decrease time remaining by 1 per tick" in {
        val context = mock[ControllerContext]
        val level = mock[Level]
        when(level.playerWon).thenReturn(false)
        when(level.finished).thenReturn(false)

        val controller = new LevelController(level)(context)
        
        controller.tick()
        
        verify(level, never()).addTime(1)
      }
    }
    
  }
  
}