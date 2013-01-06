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
package net.badgerclaw.onegameamonth.january.level.tile

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers.{isA, anyString, eq => isEqualTo}

import net.badgerclaw.onegameamonth.january.level.ReadOnlyLevel

import action._

class ExplosionSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "An Explosion" when {
    "created" should {
      "accept stages 1 through 5" in {
        val remains = mock[Tile]
        Explosion(1, remains)
        Explosion(2, remains)
        Explosion(3, remains)
        Explosion(4, remains)
        Explosion(5, remains)
      }
      "not accept a stage less than 1" in {
        val remains = mock[Tile]
        
        evaluating { Explosion(0, remains) } should produce [AssertionError]
      }
      "not accept a stage greater than 5" in {
        val remains = mock[Tile]
        
        evaluating { Explosion(6, remains) } should produce [AssertionError]
      }
    }    
    "acting" should {
      "go from stage 1 to stage 2" in {
        val level = mock[ReadOnlyLevel]
        val remains = mock[Tile]

        Explosion(1, remains).act(3, 3, level) should be (Seq(Become(Explosion(2, remains))))      
      }
      "go from stage 2 to stage 3" in {
        val level = mock[ReadOnlyLevel]
        val remains = mock[Tile]

        Explosion(2, remains).act(3, 3, level) should be (Seq(Become(Explosion(3, remains))))      
      }
      "go from stage 3 to stage 4" in {
        val level = mock[ReadOnlyLevel]
        val remains = mock[Tile]

        Explosion(3, remains).act(3, 3, level) should be (Seq(Become(Explosion(4, remains))))      
      }
      "go from stage 4 to stage 5" in {
        val level = mock[ReadOnlyLevel]
        val remains = mock[Tile]

        Explosion(4, remains).act(3, 3, level) should be (Seq(Become(Explosion(5, remains))))      
      }
      "go from stage 5 to leaving behind specified remains" in {
        val level = mock[ReadOnlyLevel]
        val remains = mock[Tile]

        Explosion(5, remains).act(3, 3, level) should be (Seq(Become(remains)))      
      }
    }
  }

}