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

class PreExitSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "PreExit" should {
    "become Exit when enough diamonds have been collected" in {
      val level = mock[ReadOnlyLevel]
      when(level.diamondsTaken).thenReturn(17)
      when(level.diamondsNeeded).thenReturn(17)
      
      PreExit.act(5, 5, level) should be (Seq(Become(Exit)))
    }
    "become Exit if more than enough diamonds have been collected" in {
      val level = mock[ReadOnlyLevel]
      when(level.diamondsTaken).thenReturn(24)
      when(level.diamondsNeeded).thenReturn(23)
      
      PreExit.act(5, 5, level) should be (Seq(Become(Exit)))
    }
    "do nothing if not enough diamonds have been collected" in {
      val level = mock[ReadOnlyLevel]
      when(level.diamondsTaken).thenReturn(41)
      when(level.diamondsNeeded).thenReturn(42)
      
      PreExit.act(5, 5, level) should be (Seq.empty[Action])
    }
  }
  
}
