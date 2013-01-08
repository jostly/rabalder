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
        
        verify(level).movementDirection_=(Some(Left))
      }
    }
    "right is pressed" should {      
      "set right movement in the level" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.RIGHT)
        
        verify(level).movementDirection_=(Some(Right))
      }
    }
    "up is pressed" should {      
      "set up movement in the level" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.UP)
        
        verify(level).movementDirection_=(Some(Up))
      }
    }
    "down is pressed" should {      
      "set down movement in the level" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        controller.keyDown(Keys.DOWN)
        
        verify(level).movementDirection_=(Some(Down))
      }
    }    
    "left is released" should {
      val key = Keys.LEFT
      val dir = Left
      
      "remove movement in the level if movement was in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.movementDirection).thenReturn(Some(dir))
        
        controller.keyUp(key)
        
        verify(level).movementDirection_=(None)        
      }
      "not change movement in the level if movement was not in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.movementDirection).thenReturn(Some(dir.turnLeft))
        
        controller.keyUp(key)
        
        verify(level, never()).movementDirection_=(any())        
      }
    }
    "right is released" should {
      val key = Keys.RIGHT
      val dir = Right
      
      "remove movement in the level if movement was in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.movementDirection).thenReturn(Some(dir))
        
        controller.keyUp(key)
        
        verify(level).movementDirection_=(None)        
      }
      "not change movement in the level if movement was not in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.movementDirection).thenReturn(Some(dir.turnLeft))
        
        controller.keyUp(key)
        
        verify(level, never()).movementDirection_=(any())        
      }
    }
    "up is released" should {
      val key = Keys.UP
      val dir = Up
      
      "remove movement in the level if movement was in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.movementDirection).thenReturn(Some(dir))
        
        controller.keyUp(key)
        
        verify(level).movementDirection_=(None)        
      }
      "not change movement in the level if movement was not in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.movementDirection).thenReturn(Some(dir.turnLeft))
        
        controller.keyUp(key)
        
        verify(level, never()).movementDirection_=(any())        
      }
    }
    "down is released" should {
      val key = Keys.DOWN
      val dir = Down
      
      "remove movement in the level if movement was in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.movementDirection).thenReturn(Some(dir))
        
        controller.keyUp(key)
        
        verify(level).movementDirection_=(None)        
      }
      "not change movement in the level if movement was not in that direction" in {
        val level = mock[Level]
        val controller = new LevelController(level)
        
        when(level.movementDirection).thenReturn(Some(dir.turnLeft))
        
        controller.keyUp(key)
        
        verify(level, never()).movementDirection_=(any())        
      }
    }
    
  }

//  "tick()" when {
//    "player is not moving" should {
//      "move left if Left is pressed" in {
//        val level = mock[Level]
//        when(level.playerPosition).thenReturn((17, 11))
//        val controller = new LevelController(level)
//        controller.keyDown(Keys.LEFT)
//        
//        controller.tick()
//        
//        verify(level).move((17, 11), (-1, 0))
//      }
//    }
//    "player is already moving" should {
//      "not move immediatedly when Left is pressed" in {
//        val level = mock[Level]
//        when(level.playerPosition).thenReturn((15, 9)).thenReturn((14, 9))
//        val controller = new LevelController(level)
//        controller.keyDown(Keys.LEFT)
//        
//        
//        controller.tick()
//
//        verify(level).move((15, 9), (-1, 0))
//        
//        for (x <- 0 until (controller.ticksToMove-1))
//          controller.tick()
//
//        verify(level, never()).move((14, 9), (-1, 0))
//                  
//        controller.tick()
//        
//        verify(level).move((14, 9), (-1, 0))
//      }      
//    }
//    "moving onto a diamond" should {
//      "increase the diamond count" in {
//        val level = mock[Level]
//        when(level.playerPosition).thenReturn((15, 9))
//        when(level.move((15, 9), (1, 0))).thenReturn(Some(Diamond))
//        
//        val controller = new LevelController(level)
//        controller.keyDown(Keys.RIGHT)
//
//        controller.tick()
//        
//        verify(level).diamondsTaken_=(1)
//        
//      }
//    }
//    "moving onto the exit" should {
//      "mark the level as finished" in {
//        val level = mock[Level]
//        when(level.playerPosition).thenReturn((15, 9))
//        when(level.move((15, 9), (0, 1))).thenReturn(Some(Exit))
//        
//        val controller = new LevelController(level)
//        controller.keyDown(Keys.DOWN)
//        
//        controller.tick()
//        
//        verify(level).finished_=(true)
//      }
//    }
//  }
//  
  "Pressing ENTER" when {
    "the level is marked as finished" should {
      "forward to state GameExit" in {
        val context = mock[ControllerContext]
        val level = mock[Level]
        when(level.finished).thenReturn(true)

        val controller = new LevelController(level)(context)

        controller.keyUp(Keys.ENTER)
        
        verify(context).forward(GameExit)
      }
    }    
  }  
  
}