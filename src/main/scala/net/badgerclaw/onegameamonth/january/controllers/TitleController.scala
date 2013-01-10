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
import net.badgerclaw.onegameamonth.january.state._
import com.badlogic.gdx.Input.Keys
import net.badgerclaw.onegameamonth.january.level.OriginalCaveData

class TitleController(context: ControllerContext) extends InputAdapter with Controller {
  
  var selected: Int = 1

  override def keyUp(key: Int): Boolean = {
    key match {
      case Keys.SPACE => context.forward(BeginPlay(selected))
      case Keys.ENTER => context.forward(BeginPlay(selected))
      case Keys.DOWN if (selected < OriginalCaveData.data.length) => selected += 1
      case Keys.UP if (selected > 1) => selected -= 1
      case _ => 
    }
    
    true
  }
  
}