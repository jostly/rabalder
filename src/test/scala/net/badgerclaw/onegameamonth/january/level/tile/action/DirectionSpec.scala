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
package net.badgerclaw.onegameamonth.january.level.tile.action

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec

class DirectionSpec extends WordSpec with ShouldMatchers {
  
  "Direction" when {
    "turning to the right" should {
      "go Up -> Right -> Down -> Left -> Up" in {
        Up.turnRight should be (Right)
        Right.turnRight should be (Down)
        Down.turnRight should be (Left)
        Left.turnRight should be (Up)
      }
    }
    "turning to the left" should {
      "go Up -> Left -> Down -> Right -> Up" in {
        Up.turnLeft should be (Left)
        Left.turnLeft should be (Down)        
        Down.turnLeft should be (Right)
        Right.turnLeft should be (Up)
      }
    }
  }

}