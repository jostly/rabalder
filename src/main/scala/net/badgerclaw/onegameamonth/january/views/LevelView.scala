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

import com.badlogic.gdx.graphics.g2d.TextureRegion
import net.badgerclaw.onegameamonth.january.ResourceFactory
import net.badgerclaw.onegameamonth.january.graphics._
import net.badgerclaw.onegameamonth.january.level._
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.graphics.GL11
import com.badlogic.gdx.Gdx

class LevelView(factory: ResourceFactory, level: Level) extends View {

  val tileTextures = factory.loadTexture("graphics/tiles.png")
  
  val moveSound = factory.loadSound("audio/dig.wav")
  val explodeSound = factory.loadSound("audio/explode.wav")
  val diamondSound = factory.loadSound("audio/diamond_3.wav")
  val boulderFallSound = factory.loadSound("audio/dig_2.wav")
  val diamondFallSound = factory.loadSound("audio/diamond_4.wav")
  val exitOpenSound = factory.loadSound("audio/hirr.wav")
    
  val tilesBag = new TextureBag(tileTextures, 16, 16, 8, 8)
  val tiles = Map[Tile, Array[TextureRegion]](
    Explosion(1) -> tilesBag.extract(3, 2),
    Explosion(2) -> tilesBag.extract(4, 2),
    Explosion(3) -> tilesBag.extract(5, 2),
    Dirt -> tilesBag.extract(3, 1),
    Wall -> tilesBag.extract(1, 0),
    SteelWall -> tilesBag.extract(7, 0),
    Boulder -> tilesBag.extract(2, 1),
    Diamond -> tilesBag.extract(0, 5, width = 8, height = 1),
    FallingBoulder -> tilesBag.extract(2, 1),
    FallingDiamond -> tilesBag.extract(0, 5),
    PlayerCharacter -> tilesBag.extract(8, 0),
    PreExit -> tilesBag.extract(7, 0),
    Exit -> tilesBag.extract(1, 2))
    
  val font = new BitmapFont(true)
  
  val scoreBoxTexture = factory.loadTexture("graphics/scorebox.png")
    
  private final val maxCameraSpeed = 1.5f
  
  private lazy val startTime = System.currentTimeMillis()
  
  override def render(context: RenderContext) {
    val frame = ((System.currentTimeMillis() - startTime) / (1000/10)).toInt
    
    val playerPos = level.playerPosition
    
    val cameraTarget = new Vector2(playerPos._1 * 16f, playerPos._2 * 16f + 8)
    val minX = 160
    val minY = 104
    val maxX = level.width * 16 - 160
    val maxY = level.height * 16 - 120
    
    if (cameraTarget.x < minX)
      cameraTarget.x = minX
    if (cameraTarget.x > maxX)
      cameraTarget.x = maxX
      
    if (cameraTarget.y < minY)
      cameraTarget.y = minY
    if (cameraTarget.y > maxY)
      cameraTarget.y = maxY
    
    val cameraDelta = cameraTarget.sub(context.camera.position.x, context.camera.position.y)
    
    if (frame > 0) {
      if (cameraDelta.len() > maxCameraSpeed)
        cameraDelta.nor().mul(maxCameraSpeed)
    }

    context.camera.translate(cameraDelta.x, cameraDelta.y, 0)

    context.camera.update()
    
    context.batch.setProjectionMatrix(context.camera.combined)
    
    context.batch.begin()
    
    val xRangeMin = math.max(math.floor((context.camera.position.x - minX) / 16), 0).toInt
    val xRangeMax = math.min(xRangeMin + 21, 40).toInt
    val yRangeMin = math.max(math.floor((context.camera.position.y - minY) / 16), 0).toInt
    val yRangeMax = math.min(yRangeMin + 16, 22).toInt
    
    for (x <- xRangeMin until xRangeMax) {
      for (y <- yRangeMin until yRangeMax) {
        
        tiles.get(level.get(x, y)) match {
          case Some(region) => context.batch.draw(region(frame % region.length), x*16, y*16)
          case None =>
        }
        
      }
    }
    
    context.batch.setProjectionMatrix(context.hud.combined)
    
    context.batch.draw(scoreBoxTexture, 0, 0, 320, 16)
        
    val message = if (level.finished) {
      (if (level.playerExists) "Congratulations, you won!" else "Too bad, you died!") +
        " Press ENTER to exit"
    } else {
      val timeLeft = level.caveTime - (frame / 25)
      level.diamondsTaken.formatted("%02d") + "/" + 
        level.diamondsNeeded.formatted("%02d") + "   " +
        timeLeft.formatted("%03d")
    }
    
    font.draw(context.batch, message, 2, 2) 
        
    context.batch.end()

    playSounds()
  }
  
  private def playSounds() {
    level.pollEvents.toSet[Event].foreach(_ match {
      case Move(PlayerCharacter, _) => moveSound.play()
      case Remove(Diamond, PlayerCharacter) => diamondSound.play()
      case Explode(_, _) => explodeSound.play()
      case Hit(FallingDiamond, _) => diamondFallSound.play()
      case Hit(FallingBoulder, _) => boulderFallSound.play(0.6f)
      case Transform(PreExit, Exit) => exitOpenSound.play()
      case _ => 
    })    
  }
  
  override def dispose() {
    exitOpenSound.dispose()
    boulderFallSound.dispose()
    diamondFallSound.dispose()
    diamondSound.dispose()
    moveSound.dispose()
    explodeSound.dispose()
    scoreBoxTexture.dispose()
    font.dispose()
    tileTextures.dispose()
  }  

}