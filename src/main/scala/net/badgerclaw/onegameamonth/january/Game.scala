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
package net.badgerclaw.onegameamonth.january

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL10
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader
import com.badlogic.gdx.math.Vector2
import net.badgerclaw.onegameamonth.january.controllers._
import net.badgerclaw.onegameamonth.january.graphics._
import net.badgerclaw.onegameamonth.january.level._
import net.badgerclaw.onegameamonth.january.views._
import net.badgerclaw.onegameamonth.january.state._
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.InputAdapter

class Game extends ApplicationListener with RenderContext with ControllerContext {
  lazy val music = Gdx.audio.newMusic(Gdx.files.internal("music/DST-GameOn.mp3"))
  lazy val camera = new OrthographicCamera()
  lazy val hud = new OrthographicCamera()
  lazy val batch = new SpriteBatch()
  
  var time: Float = 0
  final val tickTime = 1f / 60f

  var levels = OriginalCaveData.data
    
  val resourceFactory: ResourceFactory = new ResourceFactory() {
    override def loadTexture(filename: String) = new Texture(Gdx.files.internal(filename))
    override def loadSound(filename: String) = Gdx.audio.newSound(Gdx.files.internal(filename))
  } 
  
  var view: View = DefaultView
  var controller: Controller = DefaultController
  
  var score: Int = 0
  var currentLevel: Level = _
  
  def create() {
    music.setVolume(0.4f)
    music.setLooping(true)
	music.play()
    camera.setToOrtho(true, 320, 240)
    hud.setToOrtho(true, 320, 240)
    forward(Title)
  }

  def tick() {
    controller.tick()
  }
	
  def render() {
    
    time += Gdx.graphics.getDeltaTime()
    while (time > tickTime) {
      tick()
      time -= tickTime
    }
    
	Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
    
    camera.update()    
    batch.setProjectionMatrix(camera.combined)
    
    view.render(this)
    
  }
	
  def resize(width: Int, height: Int) { }
	
  def pause() { 
    music.stop()
  }
	
  def resume() { 
    music.play()
  }
	
  def dispose() {
    view.dispose()
    
    batch.dispose() 
    
    music.stop()
    music.dispose()
  }
  
  private def setViewController(view: View, controller: Controller) {
    if (view != this.view) this.view.dispose()
    this.view = view
    
    this.controller = controller
    Gdx.input.setInputProcessor(controller)
  }
  
  def forward(state: State) = state match {
    case Title => setViewController(new TitleView(resourceFactory) , new TitleController(this))
    case StartLevel => levels match {
      case x :: xs => {
        currentLevel = OriginalCaveData.decode(x)
        currentLevel.score = score
        setViewController(new LevelView(resourceFactory, currentLevel), new LevelController(currentLevel)(this))
        music.setVolume(0.1f)
      }
      case _ => forward(GameExit)
    }
    case WinLevel => {
      score = currentLevel.score
      levels = levels.tail
      forward(StartLevel)
    }
    case GameExit => {
      Gdx.app.exit()
    }
  }  
}

object DefaultView extends View {
  override def dispose() { }
  
  override def render(context: RenderContext) { }
}

object DefaultController extends InputAdapter with Controller {
  override def tick() { }
}