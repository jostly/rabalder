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

class PlayerCharacterSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "PlayerCharacter" when {
    "moving left" should {
      "move onto Space" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(Space)
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq(Move(Left)))
      }
      "not move onto Wall" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(Wall)
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "move onto Dirt" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(Dirt)
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq(Remove(Left), Move(Left)))        
      }
      "move onto a Diamond" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(Diamond)
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq(Remove(Left), Move(Left)))                
      }
      "move onto a FallingDiamond" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(FallingDiamond)
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq(Remove(Left), Move(Left)))                
      }      
      "not move onto SteelWall" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(SteelWall)
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "not move onto Explosion" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(mock[ExplosionTile])
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "not move onto Butterfly" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(mock[ButterflyTile])
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "not move onto Firefly" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(mock[FireflyTile])
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "not move onto Amoeba" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(2,3)).thenReturn(Amoeba)
        when(level.movementDirection).thenReturn(Some(Left))

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
    }
    "moving right" should {
      "move onto Space" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(4,3)).thenReturn(Space)
        when(level.movementDirection).thenReturn(Some(Right))

        PlayerCharacter.act(3, 3, level) should be (Seq(Move(Right)))
      }
      "not move onto Wall" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(4,3)).thenReturn(Wall)
        when(level.movementDirection).thenReturn(Some(Right))

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "move onto Dirt" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(4,3)).thenReturn(Dirt)
        when(level.movementDirection).thenReturn(Some(Right))

        PlayerCharacter.act(3, 3, level) should be (Seq(Remove(Right), Move(Right)))        
      }
    }    
    "moving up" should {
      "move onto Space" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,2)).thenReturn(Space)
        when(level.movementDirection).thenReturn(Some(Up))

        PlayerCharacter.act(3, 3, level) should be (Seq(Move(Up)))
      }
      "not move onto Wall" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,2)).thenReturn(Wall)
        when(level.movementDirection).thenReturn(Some(Up))

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "move onto Dirt" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,2)).thenReturn(Dirt)
        when(level.movementDirection).thenReturn(Some(Up))

        PlayerCharacter.act(3, 3, level) should be (Seq(Remove(Up), Move(Up)))        
      }
    }    
    "moving down" should {
      "move onto Space" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Space)
        when(level.movementDirection).thenReturn(Some(Down))

        PlayerCharacter.act(3, 3, level) should be (Seq(Move(Down)))
      }
      "not move onto Wall" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Wall)
        when(level.movementDirection).thenReturn(Some(Down))

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "move onto Dirt" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,4)).thenReturn(Dirt)
        when(level.movementDirection).thenReturn(Some(Down))

        PlayerCharacter.act(3, 3, level) should be (Seq(Remove(Down), Move(Down)))        
      }
    }
    "not moving" should {
      "do nothing" in {
        val level = mock[ReadOnlyLevel]
        when(level.movementDirection).thenReturn(None)

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)                
      }
    }
    "pushing left" should {
      "push a boulder with Space behind it with 1/8 chance" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(1,3)).thenReturn(Space)
        when(level.get(2,3)).thenReturn(Boulder)
        when(level.movementDirection).thenReturn(Some(Left))
        when(level.randomFloat).thenReturn(0.125f)

        PlayerCharacter.act(3, 3, level) should be (Seq(Push(Left), Move(Left)))        
      }
      "not push a boulder with Space behind it if not within 1/8 chance" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(1,3)).thenReturn(Space)
        when(level.get(2,3)).thenReturn(Boulder)
        when(level.movementDirection).thenReturn(Some(Left))
        when(level.randomFloat).thenReturn(0.1251f)

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "not push a boulder with Dirt behind it" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(1,3)).thenReturn(Dirt)
        when(level.get(2,3)).thenReturn(Boulder)
        when(level.movementDirection).thenReturn(Some(Left))
        when(level.randomFloat).thenReturn(0.125f)

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
    }
    "pushing right" should {
      "push a boulder with Space behind it with 1/8 chance" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(5,3)).thenReturn(Space)
        when(level.get(4,3)).thenReturn(Boulder)
        when(level.movementDirection).thenReturn(Some(Right))
        when(level.randomFloat).thenReturn(0.125f)

        PlayerCharacter.act(3, 3, level) should be (Seq(Push(Right), Move(Right)))        
      }
      "not push a boulder with Space behind it if not within 1/8 chance" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(5,3)).thenReturn(Space)
        when(level.get(4,3)).thenReturn(Boulder)
        when(level.movementDirection).thenReturn(Some(Right))
        when(level.randomFloat).thenReturn(0.1251f)

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
      "not push a boulder with Dirt behind it" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(5,3)).thenReturn(Dirt)
        when(level.get(4,3)).thenReturn(Boulder)
        when(level.movementDirection).thenReturn(Some(Right))
        when(level.randomFloat).thenReturn(0.125f)

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }      
    }
    "pushing up" should {
      "never work" in {
        val level = mock[ReadOnlyLevel]
        when(level.get(3,1)).thenReturn(Space)
        when(level.get(3,2)).thenReturn(Boulder)
        when(level.movementDirection).thenReturn(Some(Up))
        when(level.randomFloat).thenReturn(0f)

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
    }
    "pushing down" should {
      "never work" in { 
        // This could technically occur since a player above a boulder above space 
        // would be scanned before the boulder becomes falling
        val level = mock[ReadOnlyLevel]
        when(level.get(3,5)).thenReturn(Space)
        when(level.get(3,4)).thenReturn(Boulder)
        when(level.movementDirection).thenReturn(Some(Down))
        when(level.randomFloat).thenReturn(0f)

        PlayerCharacter.act(3, 3, level) should be (Seq.empty)        
      }
    }
  }
  
  // TODO! Moving onto exit

}