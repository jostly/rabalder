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

import org.mockito.Matchers.isA
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import com.badlogic.gdx.Input.Keys
import net.badgerclaw.onegameamonth.january.state._
import net.badgerclaw.onegameamonth.january.level.OriginalCaveData

class TitleControllerSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "TitleController" when {
    "receiving a key up event for SPACE or ENTER" should {
      "forward to BeginPlay for the selected level" in {
        val context = mock[ControllerContext]
        
        val controller = new TitleController(context)
        controller.selected = 17
        
        controller.keyUp(Keys.SPACE)
        
        verify(context).forward(BeginPlay(17))
        
        controller.selected = 12
        
        controller.keyUp(Keys.ENTER)
        
        verify(context).forward(BeginPlay(12))
        
      }
    }
    "receiving key up for DOWN" should {
      "select next level in line" in {
        val context = mock[ControllerContext]
        
        val controller = new TitleController(context)
        
        controller.keyUp(Keys.DOWN)
        
        controller.selected should be (2)        
      }
      "never select a level more than the maximum level" in {
        val context = mock[ControllerContext]
        val maxLevel = OriginalCaveData.data.length
        
        val controller = new TitleController(context)
        controller.selected = maxLevel
        
        controller.keyUp(Keys.DOWN)
        
        controller.selected should be (maxLevel)        
      }
    }
    "receiving key up for UP" should {
      "select previous level in line" in {
        val context = mock[ControllerContext]
        
        val controller = new TitleController(context)
        controller.selected = 2
        
        controller.keyUp(Keys.UP)
        
        controller.selected should be (1)        
      }
      "never select a level less than 1" in {
        val context = mock[ControllerContext]
        
        val controller = new TitleController(context)
        controller.selected = 1
        
        controller.keyUp(Keys.UP)
        
        controller.selected should be (1)
      }
    }    
  }
  

}