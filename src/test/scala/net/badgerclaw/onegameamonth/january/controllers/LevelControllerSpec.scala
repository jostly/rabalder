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
import org.mockito.Matchers.{isA}
import net.badgerclaw.onegameamonth.january.level._
import net.badgerclaw.onegameamonth.january.state._

class LevelControllerSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  val cave1 = """
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
W...... ..d.r .....r.r....... ....r....W
W.rXr...... .........rd..r.... ..... ..W
W.......... ..r.....r.r..r........r....W
Wr.rr.........r......r..r....r...r.....W
Wr. r......... r..r........r......r.rr.W
W... ..r........r.....r. r........r.rr.W
W.................................r..r.W
W. ...r..d. ...............d.rd...... .W
W..d.....r.....Pd.......rr r..r....r...W
W...r..r.r..............r .r..r........W
W.r.....r........rrr.......r.. .d....r.W
W.d.. ..r.  .....r.rd..d....r...r..d. .W
W. r..............r r..r........d.....rW
W........wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwW
W r.........r...d....r.....r...r.......W
W r......... r..r........r......r.rr...W
W. ..r........r.....r.  ....d...r.rr...W
W....rd..r........r......r.rd......r...W
W... ..r. ..r.rr.........r.rd...... ..rW
W.d.... ..... ......... .r..r....r...r.W
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"""
  
  implicit val defaultContext = mock[ControllerContext]

    
  "LevelController" should {
    "have no buttons set with no keys pressed" in {
      val level = mock[Level]
      val controller = new LevelController(level)
        
      controller.isPressed(Left)  should be (false)
      controller.isPressed(Right) should be (false)
      controller.isPressed(Up)    should be (false)
      controller.isPressed(Down)  should be (false)
    }
    "handle left press and release" in {
      val level = mock[Level]
      val controller = new LevelController(level)
      
      controller.keyDown(Keys.LEFT)
        
      controller.isPressed(Left)  should be (true)
      controller.isPressed(Right) should be (false)
      controller.isPressed(Up)    should be (false)
      controller.isPressed(Down)  should be (false)
      
      controller.keyUp(Keys.LEFT)
      
      controller.isPressed(Left)  should be (false)
      controller.isPressed(Right) should be (false)
      controller.isPressed(Up)    should be (false)
      controller.isPressed(Down)  should be (false)
    }
    "handle right press and release" in {
      val level = mock[Level]
      val controller = new LevelController(level)
      
      controller.keyDown(Keys.RIGHT)
        
      controller.isPressed(Left)  should be (false)
      controller.isPressed(Right) should be (true)
      controller.isPressed(Up)    should be (false)
      controller.isPressed(Down)  should be (false)
      
      controller.keyUp(Keys.RIGHT)
      
      controller.isPressed(Left)  should be (false)
      controller.isPressed(Right) should be (false)
      controller.isPressed(Up)    should be (false)
      controller.isPressed(Down)  should be (false)
    }
    "handle up press and release" in {
      val level = mock[Level]
      val controller = new LevelController(level)
      
      controller.keyDown(Keys.UP)
        
      controller.isPressed(Left)  should be (false)
      controller.isPressed(Right) should be (false)
      controller.isPressed(Up)    should be (true)
      controller.isPressed(Down)  should be (false)
      
      controller.keyUp(Keys.UP)
      
      controller.isPressed(Left)  should be (false)
      controller.isPressed(Right) should be (false)
      controller.isPressed(Up)    should be (false)
      controller.isPressed(Down)  should be (false)
    }
    "handle down press and release" in {
      val level = mock[Level]
      val controller = new LevelController(level)
      
      controller.keyDown(Keys.DOWN)
        
      controller.isPressed(Left)  should be (false)
      controller.isPressed(Right) should be (false)
      controller.isPressed(Up)    should be (false)
      controller.isPressed(Down)  should be (true)
      
      controller.keyUp(Keys.DOWN)
      
      controller.isPressed(Left)  should be (false)
      controller.isPressed(Right) should be (false)
      controller.isPressed(Up)    should be (false)
      controller.isPressed(Down)  should be (false)
    }
  }

  "tick()" when {
    "player is not moving" should {
      "move left if Left is pressed" in {
        val level = mock[Level]
        when(level.playerPosition).thenReturn((17, 11))
        val controller = new LevelController(level)
        controller.keyDown(Keys.LEFT)
        
        controller.tick()
        
        verify(level).move((17, 11), (-1, 0))
      }
    }
    "player is already moving" should {
      "not move immediatedly when Left is pressed" in {
        val level = mock[Level]
        when(level.playerPosition).thenReturn((15, 9)).thenReturn((14, 9))
        val controller = new LevelController(level)
        controller.keyDown(Keys.LEFT)
        
        
        controller.tick()

        verify(level).move((15, 9), (-1, 0))
        
        for (x <- 0 until (controller.ticksToMove-1))
          controller.tick()

        verify(level, never()).move((14, 9), (-1, 0))
                  
        controller.tick()
        
        verify(level).move((14, 9), (-1, 0))
      }      
    }
    "moving onto a diamond" should {
      "increase the diamond count" in {
        val level = mock[Level]
        when(level.playerPosition).thenReturn((15, 9))
        when(level.move((15, 9), (1, 0))).thenReturn(Some(Diamond))
        
        val controller = new LevelController(level)
        controller.keyDown(Keys.RIGHT)

        controller.tick()
        
        verify(level).diamondsTaken_=(1)
        
      }
    }
    "moving onto the exit" should {
      "mark the level as finished" in {
        val level = mock[Level]
        when(level.playerPosition).thenReturn((15, 9))
        when(level.move((15, 9), (0, 1))).thenReturn(Some(Exit))
        
        val controller = new LevelController(level)
        controller.keyDown(Keys.DOWN)
        
        controller.tick()
        
        verify(level).finished_=(true)
      }
    }
  }
  
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