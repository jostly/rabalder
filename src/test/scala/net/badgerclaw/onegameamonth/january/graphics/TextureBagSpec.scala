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
package net.badgerclaw.onegameamonth.january.graphics

import org.scalatest.matchers.ShouldMatchers

import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import com.badlogic.gdx.graphics.Texture
import org.mockito.Mockito._

class TextureBagSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  
  "TextureBag" should {
    "initialize from a Texture, region size in pixels and spacing" in {
      val tex = mock[Texture]
      
      val tb = new TextureBag(tex, 16, 16, 8, 8)
    }
    "extract a set of TextureRegion from an origin and a size (in region units)" in {
      val tex = mock[Texture]
      when(tex.getWidth()).thenReturn(512)
      when(tex.getHeight()).thenReturn(512)
      
      val tb = new TextureBag(tex, 16, 24, 4, 6)
      
      val regions = tb.extract(0, 0, 4, 3)
      
      regions.length should be (4 * 3)
      
      regions.foreach(cell => {
        cell should not be (null)
        cell.getTexture() should be (tex)
        cell.getRegionWidth() should be (16)
        cell.getRegionHeight() should be (24)
      })
      
      regions(0).getRegionX() should be (0)
      regions(0).getRegionY() should be (24)
      
      regions(5).getRegionX() should be (20)
      regions(5).getRegionY() should be (54)
      
      regions(10).getRegionX() should be (40)
      regions(10).getRegionY() should be (84)
    }
    "extract a single texture region from an origin in region units" in {
      
      val tex = mock[Texture]
      when(tex.getWidth()).thenReturn(512)
      when(tex.getHeight()).thenReturn(512)
      
      val tb = new TextureBag(tex, 16, 24, 4, 6)
      
      val region = tb.extract(2,3)
      
      region(0) should not be (null)
      
      region(0).getRegionX() should be (40)
      region(0).getRegionY() should be (114)
      region(0).getRegionWidth() should be (16)
      region(0).getRegionHeight() should be (24)
      
    }
  }
  
  "TextureBagStream" should {
    "be created from a TextureBag" in {
      val tex = mock[Texture]
      when(tex.getWidth()).thenReturn(32)
      when(tex.getHeight()).thenReturn(32)
      
      var stream = new TextureBag(tex, 8, 8, 8, 8).toStream
      
      stream.head.getRegionX() should be (0)
      stream.head.getRegionY() should be (8)
      
      stream = stream.tail

      stream.head.getRegionX() should be (16)
      stream.head.getRegionY() should be (8)

      stream = stream.tail

      stream.head.getRegionX() should be (0)
      stream.head.getRegionY() should be (24)

      stream = stream.tail

      stream.head.getRegionX() should be (16)
      stream.head.getRegionY() should be (24)
      
      stream.tail should be (Stream.empty)
    }
  }


}