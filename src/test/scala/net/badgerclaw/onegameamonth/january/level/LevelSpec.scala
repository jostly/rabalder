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
package net.badgerclaw.onegameamonth.january.level

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers.{isA, anyInt}
import tile._
import tile.action._

class LevelSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  val cave1 = """
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
W...... ..d.r .....r.r....... ....r....W
W.rPr...... .........rd..r.... ..... ..W
W.......... ..r.....r.r..r........r....W
Wr.rr.........r......r..r....r...r.....W
Wr. r......... r..r........r......r.rr.W
W... ..r........r.....r. r........r.rr.W
Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwww...r..r.W
W. ...r..d. ..r.r..........d.rd...... .W
W..d.....r..... ........rr r..r....r...W
W...r..r.r..............r .r..r........W
W.r.....r........rrr.......r.. .d....r.W
W.d.. ..r.  .....r.rd..d....r...r..d. .W
W. r..............r r..r........d.....rW
W........wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwW
W r.........r...d....r.....r...r......wW
W r......... r..r........r......r.rr..XW
W. ..r........r.....r.  ....d...r.rr..rW
W....rd..r........r......r.rd......r...W
W... ..r. ..r.rr.........r.rd...... ..rW
W.d.... ..... ......... .r..r....r...r.W
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"""

  
  "A level" should {
    "be of size 40x22" in {
      val level = new Level()
      level.width should be(40)
      level.height should be (22)
    }
    "be initialised with empty spaces" in {
      val level = new Level()
      level.get(0,0) should be (Space)
      level.get(39,21) should be (Space)
    }
    "initialise from a text map" in {
        
      val level: Level = Level(cave1)
      
      level.get(0,0) should be (SteelWall)
      level.get(2,7) should be (Wall)
      level.get(1,1) should be (Dirt)
      level.get(2,2) should be (Boulder)
      level.get(3,2) should be (PreExit)
      level.get(7,1) should be (Space)
      level.get(10,1) should be (Diamond)
      level.get(38,16) should be (PlayerCharacter)
        
    }    
  }

  "tick()" should {
    trait anActionTile extends SpaceTile with ActionTile
    trait playerCharacterTile extends PlayerCharacterTile with ActionTile
    
    "call the act method on ActionTiles" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(5, 5, level)).thenReturn(List())
      
      level.set(5,5)(tile)
      
      level.tick()
      
      verify(tile).act(5, 5, level)      
    }
    "respond to Move(Left) action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Move(Left)))
      
      level.set(2,2)(tile)
      
      level.tick()
      
      level.get(1, 2) should be (tile)
      level.get(2, 2) should be (Space)
    }
    "respond to Move(Right) action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Move(Right)))
      
      level.set(2,2)(tile)
      
      level.tick()
      
      level.get(3, 2) should be (tile)
      level.get(2, 2) should be (Space)
    }
    "respond to Move(Up) action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Move(Up)))
      
      level.set(2,2)(tile)
      
      level.tick()
      
      level.get(2, 1) should be (tile)
      level.get(2, 2) should be (Space)
    }
    "respond to Move(Down) action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Move(Down)))
      
      level.set(2,2)(tile)
      
      level.tick()
      
      level.get(2, 3) should be (tile)
      level.get(2, 2) should be (Space)
    }
    "respond to the Become action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Become(FallingBoulder)))
      
      level.set(2,2)(tile)
      
      level.tick()
      
      level.get(2, 2) should be (FallingBoulder)
    }
    "respond to the Explode action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(1, 1, level)).thenReturn(List(Explode(Delta(2,2), Space))) // Explosion centered on (3, 3)
      
      level.set(1, 1)(tile)
      
      level.tick()
      
      level.get(2, 2) should be (Explosion(1, Space))
      level.get(3, 2) should be (Explosion(1, Space))
      level.get(4, 2) should be (Explosion(1, Space))
      level.get(2, 3) should be (Explosion(1, Space))
      level.get(3, 3) should be (Explosion(1, Space))
      level.get(4, 3) should be (Explosion(1, Space))
      level.get(2, 4) should be (Explosion(1, Space))
      level.get(3, 4) should be (Explosion(1, Space))
      level.get(4, 4) should be (Explosion(1, Space))
    }
    "only explode tiles that can be destroyed, leaving others undisturbed" in {
      val level = new Level()
      val tile = mock[anActionTile]
      val unbreakable = mock[anActionTile]
      when(tile.act(1, 1, level)).thenReturn(List(Explode(Delta(2,2), Space))) // Explosion centered on (3, 3)
      when(unbreakable.canBeDestroyed).thenReturn(false)
      when(unbreakable.act(4, 4, level)).thenReturn(List())
      
      level.set(1, 1)(tile)
      level.set(4, 4)(unbreakable)
      
      level.tick()
      
      level.get(2, 2) should be (Explosion(1, Space))
      level.get(3, 2) should be (Explosion(1, Space))
      level.get(4, 2) should be (Explosion(1, Space))
      level.get(2, 3) should be (Explosion(1, Space))
      level.get(3, 3) should be (Explosion(1, Space))
      level.get(4, 3) should be (Explosion(1, Space))
      level.get(2, 4) should be (Explosion(1, Space))
      level.get(3, 4) should be (Explosion(1, Space))
      level.get(4, 4) should be (unbreakable)
      
      verify(unbreakable).act(4, 4, level)
    }    
    "respond to Become and Move in sequence" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Become(FallingBoulder), Move(Down)))
          
      level.set(2,2)(tile)

      level.tick()
      
      level.get(2, 3) should be (FallingBoulder)
      level.get(2, 2) should be (Space)
      
    }
    "respond to Remove(Left) action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Remove(Left)))
      
      level.set(2,2)(tile)
      level.set(1,2)(Dirt)
      
      level.tick()
      
      level.get(2, 2) should be (tile)
      level.get(1, 2) should be (Space)      
    }
    "respond to Remove(Right) action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Remove(Right)))
      
      level.set(2,2)(tile)
      level.set(3,2)(Dirt)
      
      level.tick()
      
      level.get(2, 2) should be (tile)
      level.get(3, 2) should be (Space)      
    }
    "respond to Remove(Up) action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Remove(Up)))
      
      level.set(2,2)(tile)
      level.set(2,1)(Dirt)
      
      level.tick()
      
      level.get(2, 2) should be (tile)
      level.get(2, 1) should be (Space)      
    }    
    "respond to Remove(Down) action" in {
      val level = new Level()
      val tile = mock[anActionTile]
      when(tile.act(2, 2, level)).thenReturn(List(Remove(Down)))
      
      level.set(2,2)(tile)
      level.set(2,3)(Dirt)
      
      level.tick()
      
      level.get(2, 2) should be (tile)
      level.get(2, 3) should be (Space)      
    }
    "increase diamond count if removing a diamond" in {
      val level = new Level()
      val tile = mock[playerCharacterTile]
      when(tile.act(2, 2, level)).thenReturn(List(Remove(Down)))
      
      level.set(2,2)(tile)
      level.set(2,3)(Diamond)
      
      level.tick()
      
      level.get(2, 2) should be (tile)
      level.get(2, 3) should be (Space)
      
      level.diamondsTaken should be (1)
    }
    "increase diamond count if removing a falling diamond" in {
      val level = new Level()
      val tile = mock[playerCharacterTile]
      when(tile.act(2, 2, level)).thenReturn(List(Remove(Down)))
      
      level.set(2,2)(tile)
      level.set(2,3)(FallingDiamond)
      
      level.tick()
      
      level.get(2, 2) should be (tile)
      level.get(2, 3) should be (Space)
      
      level.diamondsTaken should be (1)
    }
    
    "mark the level as finished if the player dies" in {
      val level = new Level()
      level.set(5,5)(FallingBoulder)
      level.set(5,6)(PlayerCharacter)
      
      level.tick()
      
      level.finished should be (true)
      
    }
    "mark the level as finished if time runs out" in {
      val level = new Level()
      level.caveTime = 0
      
      level.tick()
      
      level.finished should be (true)
    }
    "mark the level as finished if the player moves onto the exit" in {
      
      val level = new Level()
      val tile = mock[playerCharacterTile]
      when(tile.act(2, 2, level)).thenReturn(List(Become(PlayerCharacterExited), Move(Down)))
      
      level.set(2,2)(tile)
      level.set(2,3)(Exit)
      
      level.tick()
      
      level.finished should be (true)
      
    }
  }
}

