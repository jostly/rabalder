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
package net.badgerclaw.onegameamonth.january.views

import net.badgerclaw.onegameamonth.january.graphics.RenderContext
import net.badgerclaw.onegameamonth.january.ResourceFactory
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.BitmapFont

class TitleView(factory: ResourceFactory) extends View {
  
  val texture = factory.loadTexture("graphics/title.png")
  val textureRegion = new TextureRegion(texture, texture.getWidth(), texture.getHeight())
  textureRegion.flip(false, true)
  
  override def render(context: RenderContext) {
    context.batch.setProjectionMatrix(context.hud.combined)

    context.batch.begin()
    
    context.batch.draw(textureRegion, 0f, 0f)

    context.batch.end()    
  }
  
  override def dispose() {
    texture.dispose()
  }

}