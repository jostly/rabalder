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
package net.badgerclaw.onegameamonth.january.state

import net.badgerclaw.onegameamonth.january.level.OriginalCaveData

sealed abstract class State

case object Title extends State

case class BeginPlay(level: Int) extends State {
  assert(level > 0, "level must be at least 1")
  assert(level <= OriginalCaveData.data.length, "level must not be higher than " + OriginalCaveData.data.length)
}

case object StartLevel extends State

case object WinLevel extends State

case object GameExit extends State