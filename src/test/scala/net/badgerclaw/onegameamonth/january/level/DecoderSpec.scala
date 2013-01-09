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

import net.badgerclaw.onegameamonth.january.level.tile._
import net.badgerclaw.onegameamonth.january.level.tile.action._

class DecoderSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  val base = Array(
      0x01, //  0 -- index 
      0x16, //  1 -- slow growth time
      0x0A, //  2 -- initial diamond value
      0x0F, //  3 -- extra diamond value
      0x0A, //  4 -- random seed difficulty 1
      0x0B, //  5 -- random seed difficulty 2
      0x0C, //  6 -- random seed difficulty 3
      0x0D, //  7 -- random seed difficulty 4
      0x0E, //  8 -- random seed difficulty 5
      0x0C, //  9 -- diamonds needed difficulty 1 
      0x0D, // 10 -- diamonds needed difficulty 2
      0x0E, // 11 -- diamonds needed difficulty 3
      0x0F, // 12 -- diamonds needed difficulty 4
      0x0A, // 13 -- diamonds needed difficulty 5
      0x96, // 14 -- time to solve cave difficulty 1 
      0x6E, // 15 -- time to solve cave difficulty 2
      0x46, // 16 -- time to solve cave difficulty 3
      0x28, // 17 -- time to solve cave difficulty 4
      0x1E, // 18 -- time to solve cave difficulty 5
      0x08, // 19 -- background colour 1 
      0x0B, // 20 -- background colour 2
      0x09, // 21 -- foreground colour 
      0xD4, // 22 -- unused
      0x20, // 23 -- unused
      0x00, // 24 -- random object 1 
      0x10, // 25 -- random object 2
      0x14, // 26 -- random object 3
      0x30, // 27 -- random object 4
      0x3C, // 28 -- random object probability 1
      0x32, // 29 -- random object probability 2
      0x09, // 30 -- random object probability 3
      0x01) // 31 -- random object probability 4
      
      //0x42, 0x01, 0x09, 0x1E, 0x02, 0x42, 0x09, 0x10, 0x1E, 0x02, 0x25, 0x03, 0x04, 0x04, 0x26, 0x12, 0xFF )
  
  "Decoder" should {
    def setup() = {
      val builder = mock[OriginalCaveData.Builder]
      val random = mock[OriginalCaveData.Random]
      val decoder = new OriginalCaveData.Decoder(builder, random)
      (builder, decoder, random)
    }
      
    "decode tile types" in {
      val (_, decoder, _) = setup()
      
      decoder.decodeTile(0x00) should be (Space)
      decoder.decodeTile(0x01) should be (Dirt)
      decoder.decodeTile(0x02) should be (Wall)
      decoder.decodeTile(0x03) should be (MagicWall)
      decoder.decodeTile(0x07) should be (SteelWall)
      decoder.decodeTile(0x08) should be (Firefly(Left))
      decoder.decodeTile(0x10) should be (Boulder)
      decoder.decodeTile(0x14) should be (Diamond)
      decoder.decodeTile(0x30) should be (Butterfly(Down))
      decoder.decodeTile(0x38) should be (PlayerCharacter)
      decoder.decodeTile(0x3A) should be (Amoeba)
    }
    "decode line directions" in {
      val (_, decoder, _) = setup()
      
      decoder.decodeDirection(0) should be (Delta(0, -1))
      decoder.decodeDirection(1) should be (Delta(1, -1))
      decoder.decodeDirection(2) should be (Delta(1, 0))
      decoder.decodeDirection(3) should be (Delta(1, 1))
      decoder.decodeDirection(4) should be (Delta(0, 1))
      decoder.decodeDirection(5) should be (Delta(-1, 1))
      decoder.decodeDirection(6) should be (Delta(-1, 0))
      decoder.decodeDirection(7) should be (Delta(-1, -1))      
    }
    "set level parameters in the builder" in {
      val (builder, decoder, _) = setup()
      decoder.decodeBase(base)
      
      verify(builder).amoebaSlowGrowthTime_=(base(1))
      verify(builder).magicWallMillingTime_=(base(1))
      verify(builder).initialDiamondValue_=(base(2))
      verify(builder).extraDiamondValue_=(base(3))
      verify(builder).randomSeed_=(base(4))
      verify(builder).diamondsNeeded_=(base(9))
      verify(builder).caveTime_=(base(14))
      verify(builder).randomObjects_=( Array(base(0x18), base(0x19), base(0x1a), base(0x1b)) )
      verify(builder).randomObjectProb_=( Array(base(0x1c), base(0x1d), base(0x1e), base(0x1f)))
    }
    "initialize random seed" in {
      val (_, decoder, random) = setup()
      decoder.decodeBase(base)

      verify(random).seed(0, base(4))
    }
    "set proper random object depending on return from random function" in {
      val (builder, decoder, random) = setup()
      
      when(random.next).thenReturn(0xff).thenReturn(0x3b).thenReturn(0x31).thenReturn(0x08).thenReturn(0x00)
      when(builder.randomObjects).thenReturn(Array(base(0x18), base(0x19), base(0x1a), base(0x1b)))
      when(builder.randomObjectProb).thenReturn(Array(base(0x1c), base(0x1d), base(0x1e), base(0x1f)))
      
      decoder.initRandomized()
      
      verify(builder).drawSingle(0, 1, Dirt)
      verify(builder).drawSingle(1, 1, Space)
      verify(builder).drawSingle(2, 1, Boulder)
      verify(builder).drawSingle(3, 1, Diamond)
      verify(builder).drawSingle(4, 1, Butterfly(Down))
      
      verify(random, times(800)).next()
    }
    "set a steel wall around everything" in {
      val (builder, decoder, _) = setup()
      
      decoder.createBorder()
      
      verify(builder).drawRect(0, 0, 40, 22, SteelWall)
    }
    "decode a data list for single object" in {
      val (builder, decoder, _) = setup()
      
      val obj = 0x10 // Boulder
      val kind = 0
      
      decoder.decode(List((kind << 6) + obj, 3, 7))
      
      verify(builder).drawSingle(3, 5, Boulder)
    }
    "decode a data list for a line" in {
      val (builder, decoder, _) = setup()
      
      val obj = 0x14 // Diamond
      val kind = 1
      
      decoder.decode(List((kind << 6) + obj, 1, 6, 3, 2))
      
      verify(builder).drawLine(1, 4, 3, Right, Diamond)
    }    
    "decode a data list for a filled rectangle" in {
      val (builder, decoder, _) = setup()
      
      val obj = 0x07
      val kind = 2
      
      decoder.decode(List((kind << 6) + obj, 1, 6, 5, 7, 0))
      
      verify(builder).drawFilledRect(1, 4, 5, 7, SteelWall, Space)
    }    
    "decode a data list for a rectangle" in {
      val (builder, decoder, _) = setup()
      
      val obj = 0x07
      val kind = 3
      
      decoder.decode(List((kind << 6) + obj, 1, 6, 5, 7))
      
      verify(builder).drawRect(1, 4, 5, 7, SteelWall)
    }
    "decode two chained data lists" in {
      val (builder, decoder, _) = setup()
      
      val obj = 0x07
      val kind = 3
      
      decoder.decode(List((0 << 6) + 0x14, 1, 6, (1 << 6) + 0x07, 2, 7, 4, 1))
      
      verify(builder).drawSingle(1, 4, Diamond)
      verify(builder).drawLine(2, 5, 4, Delta(1, -1), SteelWall)
    }
  }
  
  "Builder" should {
    def setup() = new OriginalCaveData.Builder()
    "draw a single tile" in {
      val builder = setup()
      builder.drawSingle(3, 4, Diamond)
      val level = builder.build()
      
      level.get(3, 4) should be (Diamond)
    } 
    "draw a line" in {
      val builder = setup()
      builder.drawLine(1, 2, 3, Delta(1, 1), Boulder)
      val level = builder.build()
      
      level.get(1, 2) should be (Boulder)
      level.get(2, 3) should be (Boulder)
      level.get(3, 4) should be (Boulder)
      level.get(4, 5) should be (Space)
    }
    "draw a rectangle" in {
      val builder = setup()
      builder.drawRect(1, 2, 4, 5, Diamond)
      val level = builder.build()
      
      level.get(1, 2) should be (Diamond)
      level.get(2, 2) should be (Diamond)
      level.get(3, 2) should be (Diamond)
      level.get(4, 2) should be (Diamond)
      
      level.get(1, 3) should be (Diamond)
      level.get(2, 3) should be (Space)
      level.get(3, 3) should be (Space)
      level.get(4, 3) should be (Diamond)

      level.get(1, 4) should be (Diamond)
      level.get(2, 4) should be (Space)
      level.get(3, 4) should be (Space)
      level.get(4, 4) should be (Diamond)

      level.get(1, 5) should be (Diamond)
      level.get(2, 5) should be (Space)
      level.get(3, 5) should be (Space)
      level.get(4, 5) should be (Diamond)
      
      level.get(1, 6) should be (Diamond)
      level.get(2, 6) should be (Diamond)
      level.get(3, 6) should be (Diamond)
      level.get(4, 6) should be (Diamond)
    } 
    
    "draw a filled rectangle" in {
      val builder = setup()
      builder.drawFilledRect(1, 2, 4, 5, Diamond, Boulder)
      val level = builder.build()
      
      level.get(1, 2) should be (Diamond)
      level.get(2, 2) should be (Diamond)
      level.get(3, 2) should be (Diamond)
      level.get(4, 2) should be (Diamond)
      
      level.get(1, 3) should be (Diamond)
      level.get(2, 3) should be (Boulder)
      level.get(3, 3) should be (Boulder)
      level.get(4, 3) should be (Diamond)

      level.get(1, 4) should be (Diamond)
      level.get(2, 4) should be (Boulder)
      level.get(3, 4) should be (Boulder)
      level.get(4, 4) should be (Diamond)

      level.get(1, 5) should be (Diamond)
      level.get(2, 5) should be (Boulder)
      level.get(3, 5) should be (Boulder)
      level.get(4, 5) should be (Diamond)
      
      level.get(1, 6) should be (Diamond)
      level.get(2, 6) should be (Diamond)
      level.get(3, 6) should be (Diamond)
      level.get(4, 6) should be (Diamond)
    }         
  }

}