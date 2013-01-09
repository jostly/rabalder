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

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TextureBag(texture: Texture, width: Int, height: Int, horizontalSpacing: Int, verticalSpacing: Int) {
  
  def extract(originX: Int, originY: Int, width: Int = 1, height: Int = 1): Array[TextureRegion] = {
    val res = Array.ofDim[TextureRegion](width * height)
	for (x <- 0 until width) {
	  for (y <- 0 until height) {
        res(y*width+x) = extractSingle(originX + x, originY + y)
      }
    }    
    res
  }
  
  def extractSingle(x: Int, y: Int): TextureRegion = {
    val region = new TextureRegion(
      texture, 
      x * (width + horizontalSpacing), 
      y * (height + verticalSpacing), 
      width, 
      height)
    region.flip(false, true)
    region
  }
  
  def toStream: Stream[TextureRegion] = {
    val xmax = texture.getWidth() / (width + horizontalSpacing)
    val ymax = texture.getHeight() / (height + verticalSpacing)
    
    def streamFrom(x: Int, y: Int): Stream[TextureRegion] =
      if (y == ymax) Stream.empty
      else if (x == xmax) streamFrom(0, y+1)
      else extractSingle(x, y) #:: streamFrom(x+1, y)
    
    streamFrom(0, 0)
  }

}