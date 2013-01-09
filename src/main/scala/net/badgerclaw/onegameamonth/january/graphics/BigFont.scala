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
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch

object BigFont {
  
  lazy val fontTextures = new Texture(Gdx.files.internal("graphics/font.png"))
  lazy val charMap = "0123456789:;/()*ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ".zip(new TextureBag(fontTextures, 16, 16, 0, 0).toStream).toMap

  def draw(batch: SpriteBatch, message: String, x: Int, y: Int) {
    for (i <- 0 until message.length) {
      charMap.get(message(i)) match {
        case Some(region) => {
          batch.draw(region, x+i*16, y)
        }
        case _ =>
      }
    }
  }

  def dispose() {
    fontTextures.dispose()
  }

}