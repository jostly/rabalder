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

class Game extends ApplicationListener with RenderContext with ControllerContext {
  lazy val music = Gdx.audio.newMusic(Gdx.files.internal("music/DST-GameOn.mp3"))
  lazy val camera = new OrthographicCamera()
  lazy val hud = new OrthographicCamera()
  lazy val batch = new SpriteBatch()
  
  var time: Float = 0
  final val tickTime = 1f / 60f
  
  val cave1 = """20;10;15;12;150
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
W...... ..d.r .....r.r....... ....r....W
W.rXr...... .........rd..r.... ..... ..W
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
W r.........r...d....r.....r...r.......W
W r......... r..r........r......r.rr..PW
W. ..r........r.....r.  ....d...r.rr...W
W....rd..r........r......r.rd......r...W
W... ..r. ..r.rr.........r.rd...... ..rW
W.d.... ..... ......... .r..r....r...r.W
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"""  
    
  val cave2 = """20;20;50;10;150
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
W.r..r..w.r...d.w... .r.wr......w..rr..W
W.......w......rwrr. ...w ..d...w....r.W
W                                      W
Wd......w.r....rw.r. .. w..r..d.w..r.r.W
W.......w.r....rw.r. r..w.....r.w... ..W
Wwwwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwwwW
W....rr.w..r....w... ..rw....r..w.....rW
W.......w.. ....w... ...w....r. w.....rW
W                                      W
Wr..r...w....r..w..r ...w......dwr.....W
Wr....r.w..r..r.w... . rw.......wr...r.W
W.r.....w...r...w... . rw.......w r..r.W
Wwwwwwwwwwwwwwwwwwww wwwwwwwwwwwwwwwwwwW
Wr.  q..w....r.rw... ...w.rd..r.w......W
W.....r.wr......w..d ...w ..r...w.r.rr.W
W                                      W
Wd.. .r.wr....r.w.r. ..rw.r.r...w......W
W.....r.wr..d...w... r..w..r....w...rr W
W.d... rw..r....w.Xd r..w. .....w...rr W
W.r.... w.. ..r.w.P. ...w....r.rw.... .W
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"""    
    
    val cave3 = """0;15;0;24;150
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
Wr.ww.wrr.w...rwr..r....w...r.....rw.d.W
W..Xw.d.r.w...www..w.r....r..r.r...w.wrW
W....w..rd..r....w.....r.wwr.......w.wwW
Wd.w..wrwr..r....w...r......r.rr......wW
Wr.w...w..r.ww..r.wwd.......r.rr......wW
Wrr..r....w...r......r.rr......r..dww..W
W..r.ww..r.rr...w....r.rr......w..r.w.rW
W..w...d......d.r..wwr..r.w.wr..wr..d.rW
Wr.r....w.ww..d.r..wwr..r..d.w...w..r.wW
W.r.ww.....rrwr..d.w.wr..wr...wr..d.r..W
Ww.ww......rrwr..r.w.ww...w..r.ww..r.wwW
W.w.r.r.w...wwr..r....w...r.....ww.r.wwW
W.w.r.r.w.d.w.wr..wr....r..r.rr....w...W
Ww..wrwr..r....w...d...w.rw......w.ww.dW
Ww...wwr..w.d...wr..r.r...r.wr......w..W
Ww.d....r.ww..r.wwr.......r.wr......w..W
W..r....w...r......r.rr......w..r.w...wW
Wr.ww..r.ww...w....r.rr......w..rd..r..P
Ww...r......r.rd......r...ww..wr..d.w..W
Wrr...w.....r.rd......w..r.wd.d.rw.r...W
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"""   
      
  val cave4 = """20;5;20;36;120
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
WX.....r....................r........r.W
W.....r..............r.................W
W........r..r..........................W
Wr.....................................W
W...................r..................W
W.r.....................r.........r....W
W..r.....r...........r..r.............rW
W......r......r.....................r..W
W.......  B ..r.  B ....  B ....  B ...W
W.......    ..r.    ....    ....    r..W
W......................................W
W...r..............................r...W
W...r.....r............................W
W......r...........r..................rW
W...........r.......r..................W
W..r..............r....................W
W.....................r.........r......W
W................................r..r..W
W....r......r.rr..................r....W
W...........r.rr.........r..r.r.......PW
WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW"""    
    
  val level = Level(cave4)  
  
  val resourceFactory: ResourceFactory = new ResourceFactory() {
    override def loadTexture(filename: String) = new Texture(Gdx.files.internal(filename))
    override def loadSound(filename: String) = Gdx.audio.newSound(Gdx.files.internal(filename))
  } 
  
  lazy val titleView = new TitleView(resourceFactory)  
  lazy val titleController = new TitleController(this)
  
  lazy val levelView = new LevelView(resourceFactory, level)
  lazy val levelController = new LevelController(level)(this)
    
  var view: View = _
  var controller: Controller = _
  
  
  def create() {
    music.setVolume(0.5f)
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
    titleView.dispose()
    levelView.dispose()

    batch.dispose() 
    
    music.stop()
    music.dispose()
  }
  
  private def setViewController(view: View, controller: Controller) {
    this.view = view
    this.controller = controller
    Gdx.input.setInputProcessor(controller)
  }
  
  def forward(state: State) = state match {
    case Title => setViewController(titleView, titleController)
    case StartLevel() => setViewController(levelView, levelController); music.setVolume(0.2f)
    case GameExit => Gdx.app.exit()
  }
  
}
