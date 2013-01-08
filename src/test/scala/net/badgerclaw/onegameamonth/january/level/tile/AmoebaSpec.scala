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

class AmoebaSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "Amoeba" when {
    "judging entire amoeba" should {
       val amoeba = Amoeba
      "become boulders if it has grown too large" in {
        val level = mock[ReadOnlyLevel]
        when(level.amoebaTooLarge).thenReturn(true)
        
        amoeba.act(3, 3, level) should be (Seq(Become(Boulder)))         
      }
      "become diamonds if it cannot grow further" in {
        val level = mock[ReadOnlyLevel]
        when(level.amoebaCanGrow).thenReturn(false)
        
        amoeba.act(3, 3, level) should be (Seq(Become(Diamond)))
      }
      "become boulders if it has both grown too large and cannot grow further" in {
        val level = mock[ReadOnlyLevel]
        when(level.amoebaTooLarge).thenReturn(true)
        when(level.amoebaCanGrow).thenReturn(false)
        
        amoeba.act(3, 3, level) should be (Seq(Become(Boulder)))         
      }
    }
    "there is Space above it" should {
      val amoeba = Amoeba
      def aLevel = {
        val level = mock[ReadOnlyLevel]
		when(level.amoebaTooLarge).thenReturn(false)
		when(level.amoebaCanGrow).thenReturn(true)
		when(level.get(3,4)).thenReturn(Wall)
		when(level.get(2,3)).thenReturn(Wall)
		when(level.get(4,3)).thenReturn(Wall)
        level 
      }
      "Become(Amoeba) to signal it can grow" in {
        val level = aLevel
        
        when(level.amoebaShouldGrow).thenReturn(false)
        when(level.get(3,2)).thenReturn(Space)
        
        amoeba.act(3, 3, level) should be (Seq(Become(Amoeba)))        
      }
      "grow if the level randomness says so" in {
        val level = aLevel
        
        when(level.amoebaShouldGrow).thenReturn(true)
        when(level.randomDirection).thenReturn(Up)
        when(level.get(3,2)).thenReturn(Space)
        
        amoeba.act(3, 3, level) should be (Seq(Move(Up), Become(Amoeba)))        
      }
      "not grow if the level randomness says so" in {
        val level = aLevel
        
        when(level.amoebaShouldGrow).thenReturn(false)
        when(level.get(3,2)).thenReturn(Space)
        
        amoeba.act(3, 3, level) should be (Seq(Become(Amoeba)))        
      }
      "grow if the level randomness gave a direction that was blocked" in {
        val level = aLevel
        
        when(level.amoebaShouldGrow).thenReturn(true)
        when(level.randomDirection).thenReturn(Right)
        when(level.get(3,2)).thenReturn(Space)
        
        amoeba.act(3, 3, level) should be (Seq(Become(Amoeba)))        
      }      
    }
    "there is Dirt to the right of it" should {
      val amoeba = Amoeba
      def aLevel = {
        val level = mock[ReadOnlyLevel]
		when(level.amoebaTooLarge).thenReturn(false)
		when(level.amoebaCanGrow).thenReturn(true)
		when(level.get(3,4)).thenReturn(Wall)
		when(level.get(2,3)).thenReturn(Wall)
		when(level.get(3,2)).thenReturn(Wall)
        level 
      }
      "Become(Amoeba) to signal it can grow" in {
        val level = aLevel
        
        when(level.amoebaShouldGrow).thenReturn(false)
        when(level.get(4,3)).thenReturn(Dirt)
        
        amoeba.act(3, 3, level) should be (Seq(Become(Amoeba)))        
      }
      "grow if the level randomness says so" in {
        val level = aLevel
        
        when(level.amoebaShouldGrow).thenReturn(true)
        when(level.randomDirection).thenReturn(Right)
        when(level.get(4,3)).thenReturn(Dirt)
        
        amoeba.act(3, 3, level) should be (Seq(Move(Right), Become(Amoeba)))        
      }
    }
    "it is completely blocked" should {
      val amoeba = Amoeba
      "do nothing" in {
        val level = mock[ReadOnlyLevel]
		when(level.amoebaTooLarge).thenReturn(false)
		when(level.amoebaCanGrow).thenReturn(true)
		when(level.get(3,4)).thenReturn(Wall)
		when(level.get(2,3)).thenReturn(Wall)
		when(level.get(3,2)).thenReturn(Wall)
		when(level.get(4,3)).thenReturn(Wall)
        
        amoeba.act(3, 3, level) should be (Seq.empty)        
      }
      
    }
    
  }

}