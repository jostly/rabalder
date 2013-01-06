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

class ButterflySpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "Butterfly" when {
    "facing down" should {
      val fly = Butterfly(Down)
      "turn to their right and move there if possible" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Become(Butterfly(Left)), Move(Left)))
      }
      "failing that, go straight if possible" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(Dirt)
        when(level.get(3,4)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Move(Down)))
      }
      "failing that, turn to its left" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(Dirt)
        when(level.get(3,4)).thenReturn(Dirt)
        
        fly.act(3, 3, level) should be (Seq(Become(Butterfly(Right))))
      }
    }
    "facing left" should {
      val fly = Butterfly(Left)
      "turn to their right and move there if possible" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,2)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Become(Butterfly(Up)), Move(Up)))
      }
      "failing that, go straight if possible" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,2)).thenReturn(Dirt)
        when(level.get(2,3)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Move(Left)))
      }
      "failing that, turn to its left" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,2)).thenReturn(Dirt)
        when(level.get(2,3)).thenReturn(Dirt)
        
        fly.act(3, 3, level) should be (Seq(Become(Butterfly(Down))))
      }
    }
    "facing up" should {
      val fly = Butterfly(Up)
      "turn to their right and move there if possible" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(4,3)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Become(Butterfly(Right)), Move(Right)))
      }
      "failing that, go straight if possible" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(4,3)).thenReturn(Dirt)
        when(level.get(3,2)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Move(Up)))
      }
      "failing that, turn to its left" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(4,3)).thenReturn(Dirt)
        when(level.get(3,2)).thenReturn(Dirt)
        
        fly.act(3, 3, level) should be (Seq(Become(Butterfly(Left))))
      }
    }
    "facing right" should {
      val fly = Butterfly(Right)
      "turn to their right and move there if possible" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Become(Butterfly(Down)), Move(Down)))
      }
      "failing that, go straight if possible" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Dirt)
        when(level.get(4,3)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Move(Right)))
      }
      "failing that, turn to its left" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Dirt)
        when(level.get(4,3)).thenReturn(Dirt)
        
        fly.act(3, 3, level) should be (Seq(Become(Butterfly(Up))))
      }
    }
    "above a player character" should {
      "explode even if it could move instead" in {
        val fly = Butterfly(Up)
        val level = mock[ReadOnlyLevel]
        
        when(level.get(3,4)).thenReturn(PlayerCharacter)
        when(level.get(4,3)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Explode(Delta(0,0), Diamond)))
      }
    }
    "to the right of a player character" should {
      "explode even if it could move instead" in {
        val fly = Butterfly(Up)
        val level = mock[ReadOnlyLevel]
        
        when(level.get(2,3)).thenReturn(PlayerCharacter)
        when(level.get(4,3)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Explode(Delta(0,0), Diamond)))
      }
    }
    "below of a player character" should {
      "explode even if it could move instead" in {
        val fly = Butterfly(Up)
        val level = mock[ReadOnlyLevel]
        
        when(level.get(3,2)).thenReturn(PlayerCharacter)
        when(level.get(4,3)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Explode(Delta(0,0), Diamond)))
      }
    }
    "to the left of a player character" should {
      "explode even if it could move instead" in {
        val fly = Butterfly(Down)
        val level = mock[ReadOnlyLevel]
        
        when(level.get(4,3)).thenReturn(PlayerCharacter)
        when(level.get(2,3)).thenReturn(Space)
        
        fly.act(3, 3, level) should be (Seq(Explode(Delta(0,0), Diamond)))
      }
    }
  } 

}