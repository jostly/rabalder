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

class DiamondSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "A Diamond" when {
    "resting on a dirt tile" should {
      "not act" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq.empty[Tile])
      }
    }
    "resting on empty space" should {
      "start to fall" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Space)

        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Down)))
      }
    }    
    "resting on a boulder" should {
      "not act if there are no spaces to the left nor right" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Boulder)
        when(level.get(2,3)).thenReturn(Dirt)
        when(level.get(4,3)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq.empty[Tile])
      }
      "roll to the left if there is empty space to the left and diagonally left and below" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Boulder)
        when(level.get(2,3)).thenReturn(Space)
        when(level.get(2,4)).thenReturn(Space)
        when(level.get(4,3)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Left)))
      }
      "roll to the right if there is empty space to the right and diagonally right and below" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Boulder)
        when(level.get(4,3)).thenReturn(Space)
        when(level.get(4,4)).thenReturn(Space)
        when(level.get(2,3)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Right)))
      }
      "roll to the left if surrounded by empty space" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Boulder)
        when(level.get(2,3)).thenReturn(Space)
        when(level.get(2,4)).thenReturn(Space)
        when(level.get(4,3)).thenReturn(Space)
        when(level.get(4,4)).thenReturn(Space)
        
        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Left)))
      }
    }
    "resting on a wall" should {
      "not act if there are no spaces to the left nor right" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Wall)
        when(level.get(2,3)).thenReturn(Dirt)
        when(level.get(4,3)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq.empty[Tile])
      }
      "roll to the left if there is empty space to the left and diagonally left and below" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Wall)
        when(level.get(2,3)).thenReturn(Space)
        when(level.get(2,4)).thenReturn(Space)
        when(level.get(4,3)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Left)))
      }
      "roll to the right if there is empty space to the right and diagonally right and below" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Wall)
        when(level.get(4,3)).thenReturn(Space)
        when(level.get(4,4)).thenReturn(Space)
        when(level.get(2,3)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Right)))
      }
      "roll to the left if surrounded by empty space" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Wall)
        when(level.get(2,3)).thenReturn(Space)
        when(level.get(2,4)).thenReturn(Space)
        when(level.get(4,3)).thenReturn(Space)
        when(level.get(4,4)).thenReturn(Space)
        
        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Left)))
      }
    }
    "resting on a diamond" should {
      "not act if there are no spaces to the left nor right" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Diamond)
        when(level.get(2,3)).thenReturn(Dirt)
        when(level.get(4,3)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq.empty[Tile])
      }
      "roll to the left if there is empty space to the left and diagonally left and below" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Diamond)
        when(level.get(2,3)).thenReturn(Space)
        when(level.get(2,4)).thenReturn(Space)
        when(level.get(4,3)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Left)))
      }
      "roll to the right if there is empty space to the right and diagonally right and below" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Diamond)
        when(level.get(4,3)).thenReturn(Space)
        when(level.get(4,4)).thenReturn(Space)
        when(level.get(2,3)).thenReturn(Dirt)
        
        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Right)))
      }
      "roll to the left if surrounded by empty space" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Diamond)
        when(level.get(2,3)).thenReturn(Space)
        when(level.get(2,4)).thenReturn(Space)
        when(level.get(4,3)).thenReturn(Space)
        when(level.get(4,4)).thenReturn(Space)
        
        Diamond.act(3, 3, level) should be (Seq(Become(FallingDiamond), Move(Left)))
      }
    }    
  }
  
}