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

class MagicWallSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "A MagicWall" should {
    "do nothing if magic walls have not expired" in {
      val level = mock[ReadOnlyLevel]
      when(level.magicWallHasExpired).thenReturn(false)
      
      MagicWall.act(3, 3, level) should be (Seq.empty)

    }
    "become a Wall if magic walls have expired" in {
      val level = mock[ReadOnlyLevel]
      when(level.magicWallHasExpired).thenReturn(true)
      
      MagicWall.act(3, 3, level) should be (Seq(Become(Wall)))

    }
  }

}