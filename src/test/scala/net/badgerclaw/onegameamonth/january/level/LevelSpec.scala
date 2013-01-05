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
    
    "find the location of a specific tile" in {

      val level: Level = Level(cave1)
      
      level.find(PlayerCharacter) should be ((38, 16))
      
      level.find(PreExit) should be ((3, 2))
      
    }
    
  }
  
  "Moving in a level" should {
	
    "move something in a tile, leaving Space" in {
      val level: Level = Level(cave1)
      
      level.move((38, 16), (-1, 0)) // Move tile (38, 16) one step left
      
      level.get(37, 16) should be (PlayerCharacter)
      level.get(38, 16) should be (Space)
    }
    
    "return what was in the target space if move succeeded, None otherwise" in {
      val level: Level = Level(cave1)
      
      level.move((38, 16), (-1, 0)) should be (Some(Dirt))
      level.move((38, 16), (1, 0)) should be (None)
    }
    
    "not move onto walls" in {
      val level: Level = Level(cave1)
      
      level.move((38, 16), (0, -1))
      level.get(38, 15) should be (Wall)
      level.get(38, 16) should be (PlayerCharacter)
    }
    
    "not move onto steel walls" in {
      val level: Level = Level(cave1)
      
      level.move((38, 16), (1, 0))
      level.get(39, 16) should be (SteelWall)
      level.get(38, 16) should be (PlayerCharacter)
    }
    
    "not move onto boulders" in {
      val level: Level = Level(cave1)
      
      level.move((38, 16), (0, 1))
      level.get(38, 17) should be (Boulder)
      level.get(38, 16) should be (PlayerCharacter)
    }
    "push boulders if there is a space behind it and if trying hard enough" in {
      val level: Level = Level(cave1)
      level.set(37,16)(Boulder)
      level.set(36,16)(Space)
      
      for (i <- 0 until 100 if level.get(37,16) == Boulder) {
        level.move((38, 16), (-1, 0))
      }
      
      level.get(36,16) should be (Boulder)
      level.get(37,16) should be (PlayerCharacter)
      level.get(38,16) should be (Space)      
    }
  }
  "tick()" should {
    "let a boulder fall through empty space" in {
      val level = new Level()
      level.set(5,5)(Boulder)
      
      level.tick()
      
      level.get(5,6) should be (FallingBoulder)
      level.get(5,5) should be (Space)
    }
    "let a diamond fall through empty space" in {
      val level = new Level()
      level.set(5,5)(Diamond)
      
      level.tick()
      
      level.get(5,6) should be (FallingDiamond)
      level.get(5,5) should be (Space)
    }
    "let a falling boulder come to rest on dirt" in {
      val level = new Level()
      level.set(5,5)(Boulder)
      level.set(5,7)(Dirt)
      
      level.tick()
      
      level.get(5,6) should be (FallingBoulder)
      level.get(5,5) should be (Space)
      
      level.tick()
      
      level.get(5,6) should be (Boulder)      
    }
    "let a falling boulder come to rest on another boulder (if it cannot roll)" in {
      val level = new Level()
      /*
       *    r
       *     
       *   .r.
       *    .
       */
      level.set(5,5)(Boulder)
      level.set(5,7)(Boulder)
      level.set(4,7)(Dirt)
      level.set(6,7)(Dirt)
      level.set(5,8)(Dirt)
      
      level.tick()
      
      level.get(5,6) should be (FallingBoulder)
      level.get(5,5) should be (Space)
      level.get(5,7) should be (Boulder)
      
      level.tick()
      
      level.get(5,6) should be (Boulder)
      level.get(5,7) should be (Boulder)
    }      
    "let a boulder roll to the right if there is empty space to its right and to the right and below" in {
      val level = new Level()
      level.set(5,5)(Boulder)
      level.set(5,6)(Wall)
      level.set(4,5)(Dirt)
      
      level.tick()
      
      level.get(6,5) should be (FallingBoulder)
      level.get(5,5) should be (Space)
    }
    "let a boulder roll to the left if there is empty space to its left and to the left and below" in {
      val level = new Level()
      level.set(5,5)(Boulder)
      level.set(5,6)(Wall)
      level.set(6,5)(Dirt)
      
      level.tick()
      
      level.get(4,5) should be (FallingBoulder)
      level.get(5,5) should be (Space)
    }
    "not let a boulder roll off of dirt" in {
      val level = new Level()
      level.set(5,5)(Boulder)
      level.set(5,6)(Dirt)
      level.set(6,5)(Dirt)
      
      level.tick()
      
      level.get(4,5) should be (Space)
      level.get(5,5) should be (Boulder)
      
    }
    "explode a falling boulder on top of the player" in {
      val level = new Level()
      level.set(5,5)(FallingBoulder)
      level.set(5,6)(PlayerCharacter)
      
      level.tick()

      level.get(4,5) should be (Explosion(1))
      level.get(5,5) should be (Explosion(1))
      level.get(6,5) should be (Explosion(1))
      level.get(4,6) should be (Explosion(1))
      level.get(5,6) should be (Explosion(1))
      level.get(6,6) should be (Explosion(1))
      level.get(4,7) should be (Explosion(1))
      level.get(5,7) should be (Explosion(1))
      level.get(6,7) should be (Explosion(1))
    }
    "mark the level as finished if the player dies" in {
      val level = new Level()
      level.set(5,5)(FallingBoulder)
      level.set(5,6)(PlayerCharacter)
      
      level.tick()
      
      level.finished should be (true)
      
    }
    "animate explosions" in {
      val level = new Level()
      level.set(5,5)(Explosion(1))
      
      level.tick()
      
      level.get(5,5) should be (Explosion(2))
      
      level.tick()
      
      level.get(5,5) should be (Explosion(3))      

      level.tick()
      
      level.get(5,5) should be (Explosion(4))
      
      level.tick()
      
      level.get(5,5) should be (Space)      
      
    }
  }
}

