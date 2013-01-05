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

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers.{isA, anyString, eq => isEqualTo}
import net.badgerclaw.onegameamonth.january.graphics.RenderContext
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.Texture
import net.badgerclaw.onegameamonth.january.ResourceFactory
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TitleViewSpec extends WordSpec with ShouldMatchers with MockitoSugar {
  val texture = mock[Texture]
  when(texture.getWidth()).thenReturn(320)
  when(texture.getHeight()).thenReturn(240)
  
  "TitleView" when {
    "created" should {
      "load its texture from the factory" in {
        val factory = mock[ResourceFactory]
        when(factory.loadTexture(anyString())).thenReturn(texture)
        
        val view = new TitleView(factory)
        
        verify(factory).loadTexture("graphics/title.png")
        
      }
    }
    "rendered" should {
      "draw its title texture" in {
        val factory = mock[ResourceFactory]
        when(factory.loadTexture(anyString())).thenReturn(texture)
        
        val view = new TitleView(factory)
        
        val context = mock[RenderContext]
        val batch = mock[SpriteBatch]
        
        when(context.batch).thenReturn(batch)
        
        view.render(context)

        verify(batch).begin()        
        verify(batch).draw(isA(classOf[TextureRegion]), isEqualTo(0f), isEqualTo(0f))
        verify(batch).end()
        
      }
    }
    "disposed" should {
      "dispose of its texture" in {
        val factory = mock[ResourceFactory]
        when(factory.loadTexture(anyString())).thenReturn(texture)
        
        val view = new TitleView(factory)
        
        view.dispose()
        
        verify(texture).dispose()
        
      }
    }
  }

}