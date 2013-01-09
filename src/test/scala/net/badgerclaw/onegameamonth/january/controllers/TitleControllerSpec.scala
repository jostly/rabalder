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

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers.{isA}

import com.badlogic.gdx.Input.Keys

import net.badgerclaw.onegameamonth.january.state._

class TitleControllerSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "TitleController" when {
    "when receiving a key up event" should {
      "forward to StartLevel" in {
        val context = mock[ControllerContext]
        
        val controller = new TitleController(context)
        
        controller.keyUp(Keys.SPACE)
        
        verify(context).forward(BeginPlay(1))
      }
    }
  }
  

}