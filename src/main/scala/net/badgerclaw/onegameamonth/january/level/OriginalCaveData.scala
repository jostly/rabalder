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

import tile._
import tile.action._
import scala.annotation.tailrec

object OriginalCaveData {
  val data = List(
    Array(0x01, 0x14, 0x0A, 0x0F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0C, 0x0C, 0x0C, 0x0C, 0x0C, 0x96, 0x6E, 0x46, 0x28, 0x1E, 0x08, 0x0B, 0x09, 0xD4, 0x20, 0x00, 0x10, 0x14, 0x00, 0x3C, 0x32, 0x09, 0x00, 0x42, 0x01, 0x09, 0x1E, 0x02, 0x42, 0x09, 0x10, 0x1E, 0x02, 0x25, 0x03, 0x04, 0x04, 0x26, 0x12, 0xFF ),
    Array(0x02, 0x14, 0x14, 0x32, 0x03, 0x00, 0x01, 0x57, 0x58, 0x0A, 0x0C, 0x09, 0x0D, 0x0A, 0x96, 0x6E, 0x46, 0x46, 0x46, 0x0A, 0x04, 0x09, 0x00, 0x00, 0x00, 0x10, 0x14, 0x08, 0x3C, 0x32, 0x09, 0x02, 0x42, 0x01, 0x08, 0x26, 0x02, 0x42, 0x01, 0x0F, 0x26, 0x02, 0x42, 0x08, 0x03, 0x14, 0x04, 0x42, 0x10, 0x03, 0x14, 0x04, 0x42, 0x18, 0x03, 0x14, 0x04, 0x42, 0x20, 0x03, 0x14, 0x04, 0x40, 0x01, 0x05, 0x26, 0x02, 0x40, 0x01, 0x0B, 0x26, 0x02, 0x40, 0x01, 0x12, 0x26, 0x02, 0x40, 0x14, 0x03, 0x14, 0x04, 0x25, 0x12, 0x15, 0x04, 0x12, 0x16, 0xFF),
    Array(0x03, 0x00, 0x0F, 0x00, 0x00, 0x32, 0x36, 0x34, 0x37, 0x18, 0x17, 0x18, 0x17, 0x15, 0x96, 0x64, 0x5A, 0x50, 0x46, 0x09, 0x08, 0x09, 0x04, 0x00, 0x02, 0x10, 0x14, 0x00, 0x64, 0x32, 0x09, 0x00, 0x25, 0x03, 0x04, 0x04, 0x27, 0x14, 0xFF),
    Array(0x04, 0x14, 0x05, 0x14, 0x00, 0x6E, 0x70, 0x73, 0x77, 0x24, 0x24, 0x24, 0x24, 0x24, 0x78, 0x64, 0x50, 0x3C, 0x32, 0x04, 0x08, 0x09, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x14, 0x00, 0x00, 0x00, 0x25, 0x01, 0x03, 0x04, 0x26, 0x16, 0x81, 0x08, 0x0A, 0x04, 0x04, 0x00, 0x30, 0x0A, 0x0B, 0x81, 0x10, 0x0A, 0x04, 0x04, 0x00, 0x30, 0x12, 0x0B, 0x81, 0x18, 0x0A, 0x04, 0x04, 0x00, 0x30, 0x1A, 0x0B, 0x81, 0x20, 0x0A, 0x04, 0x04, 0x00, 0x30, 0x22, 0x0B, 0xFF),
    Array(0x11, 0x14, 0x1E, 0x00, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x06, 0x06, 0x06, 0x06, 0x06, 0x0A, 0x0A, 0x0A, 0x0A, 0x0A, 0x0E, 0x02, 0x09, 0x00, 0x00, 0x00, 0x14, 0x00, 0x00, 0xFF, 0x09, 0x00, 0x00, 0x87, 0x00, 0x02, 0x28, 0x16, 0x07, 0x87, 0x00, 0x02, 0x14, 0x0C, 0x00, 0x32, 0x0A, 0x0C, 0x10, 0x0A, 0x04, 0x01, 0x0A, 0x05, 0x25, 0x03, 0x05, 0x04, 0x12, 0x0C, 0xFF),
    
    Array(0x05, 0x14, 0x32, 0x5A, 0x00, 0x00, 0x00, 0x00, 0x00, 0x04, 0x05, 0x06, 0x07, 0x08, 0x96, 0x78, 0x5A, 0x3C, 0x1E, 0x09, 0x0A, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x25, 0x01, 0x03, 0x04, 0x27, 0x16, 0x80, 0x08, 0x0A, 0x03, 0x03, 0x00, 0x80, 0x10, 0x0A, 0x03, 0x03, 0x00, 0x80, 0x18, 0x0A, 0x03, 0x03, 0x00, 0x80, 0x20, 0x0A, 0x03, 0x03, 0x00, 0x14, 0x09, 0x0C, 0x08, 0x0A, 0x0A, 0x14, 0x11, 0x0C, 0x08, 0x12, 0x0A, 0x14, 0x19, 0x0C, 0x08, 0x1A, 0x0A, 0x14, 0x21, 0x0C, 0x08, 0x22, 0x0A, 0x80, 0x08, 0x10, 0x03, 0x03, 0x00, 0x80, 0x10, 0x10, 0x03, 0x03, 0x00, 0x80, 0x18, 0x10, 0x03, 0x03, 0x00, 0x80, 0x20, 0x10, 0x03, 0x03, 0x00, 0x14, 0x09, 0x12, 0x08, 0x0A, 0x10, 0x14, 0x11, 0x12, 0x08, 0x12, 0x10, 0x14, 0x19, 0x12, 0x08, 0x1A, 0x10, 0x14, 0x21, 0x12, 0x08, 0x22, 0x10, 0xFF),
    Array(0x06, 0x14, 0x28, 0x3C, 0x00, 0x14, 0x15, 0x16, 0x17, 0x04, 0x06, 0x07, 0x08, 0x08, 0x96, 0x78, 0x64, 0x5A, 0x50, 0x0E, 0x0A, 0x09, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x32, 0x00, 0x00, 0x00, 0x82, 0x01, 0x03, 0x0A, 0x04, 0x00, 0x82, 0x01, 0x06, 0x0A, 0x04, 0x00, 0x82, 0x01, 0x09, 0x0A, 0x04, 0x00, 0x82, 0x01, 0x0C, 0x0A, 0x04, 0x00, 0x41, 0x0A, 0x03, 0x0D, 0x04, 0x14, 0x03, 0x05, 0x08, 0x04, 0x05, 0x14, 0x03, 0x08, 0x08, 0x04, 0x08, 0x14, 0x03, 0x0B, 0x08, 0x04, 0x0B, 0x14, 0x03, 0x0E, 0x08, 0x04, 0x0E, 0x82, 0x1D, 0x03, 0x0A, 0x04, 0x00, 0x82, 0x1D, 0x06, 0x0A, 0x04, 0x00, 0x82, 0x1D, 0x09, 0x0A, 0x04, 0x00, 0x82, 0x1D, 0x0C, 0x0A, 0x04, 0x00, 0x41, 0x1D, 0x03, 0x0D, 0x04, 0x14, 0x24, 0x05, 0x08, 0x23, 0x05, 0x14, 0x24, 0x08, 0x08, 0x23, 0x08, 0x14, 0x24, 0x0B, 0x08, 0x23, 0x0B, 0x14, 0x24, 0x0E, 0x08, 0x23, 0x0E, 0x25, 0x03, 0x14, 0x04, 0x26, 0x14, 0xFF),
    Array(0x07, 0x4B, 0x0A, 0x14, 0x02, 0x07, 0x08, 0x0A, 0x09, 0x0F, 0x14, 0x19, 0x19, 0x19, 0x78, 0x78, 0x78, 0x78, 0x78, 0x09, 0x0A, 0x0D, 0x00, 0x00, 0x00, 0x10, 0x08, 0x00, 0x64, 0x28, 0x02, 0x00, 0x42, 0x01, 0x07, 0x0C, 0x02, 0x42, 0x1C, 0x05, 0x0B, 0x02, 0x7A, 0x13, 0x15, 0x02, 0x02, 0x14, 0x04, 0x06, 0x14, 0x04, 0x0E, 0x14, 0x04, 0x16, 0x14, 0x22, 0x04, 0x14, 0x22, 0x0C, 0x14, 0x22, 0x16, 0x25, 0x14, 0x03, 0x04, 0x27, 0x07, 0xFF),
    Array(0x08, 0x14, 0x0A, 0x14, 0x01, 0x03, 0x04, 0x05, 0x06, 0x0A, 0x0F, 0x14, 0x14, 0x14, 0x78, 0x6E, 0x64, 0x5A, 0x50, 0x02, 0x0E, 0x09, 0x00, 0x00, 0x00, 0x10, 0x08, 0x00, 0x5A, 0x32, 0x02, 0x00, 0x14, 0x04, 0x06, 0x14, 0x22, 0x04, 0x14, 0x22, 0x0C, 0x04, 0x00, 0x05, 0x25, 0x14, 0x03, 0x42, 0x01, 0x07, 0x0C, 0x02, 0x42, 0x01, 0x0F, 0x0C, 0x02, 0x42, 0x1C, 0x05, 0x0B, 0x02, 0x42, 0x1C, 0x0D, 0x0B, 0x02, 0x43, 0x0E, 0x11, 0x08, 0x02, 0x14, 0x0C, 0x10, 0x00, 0x0E, 0x12, 0x14, 0x13, 0x12, 0x41, 0x0E, 0x0F, 0x08, 0x02, 0xFF),
    Array(0x12, 0x14, 0x0A, 0x00, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x10, 0x10, 0x10, 0x10, 0x10, 0x0F, 0x0F, 0x0F, 0x0F, 0x0F, 0x06, 0x0F, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x87, 0x00, 0x02, 0x28, 0x16, 0x07, 0x87, 0x00, 0x02, 0x14, 0x0C, 0x01, 0x50, 0x01, 0x03, 0x09, 0x03, 0x48, 0x02, 0x03, 0x08, 0x03, 0x54, 0x01, 0x05, 0x08, 0x03, 0x50, 0x01, 0x06, 0x07, 0x03, 0x50, 0x12, 0x03, 0x09, 0x05, 0x54, 0x12, 0x05, 0x08, 0x05, 0x50, 0x12, 0x06, 0x07, 0x05, 0x25, 0x01, 0x04, 0x04, 0x12, 0x04, 0xFF),
    
    Array(0x09, 0x14, 0x05, 0x0A, 0x64, 0x89, 0x8C, 0xFB, 0x33, 0x4B, 0x4B, 0x50, 0x55, 0x5A, 0x96, 0x96, 0x82, 0x82, 0x78, 0x08, 0x04, 0x09, 0x00, 0x00, 0x10, 0x14, 0x00, 0x00, 0xF0, 0x78, 0x00, 0x00, 0x82, 0x05, 0x0A, 0x0D, 0x0D, 0x00, 0x01, 0x0C, 0x0A, 0x82, 0x19, 0x0A, 0x0D, 0x0D, 0x00, 0x01, 0x1F, 0x0A, 0x42, 0x11, 0x12, 0x09, 0x02, 0x40, 0x11, 0x13, 0x09, 0x02, 0x25, 0x07, 0x0C, 0x04, 0x08, 0x0C, 0xFF),
    Array(0x0A, 0x14, 0x19, 0x3C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0C, 0x0C, 0x0C, 0x0C, 0x0C, 0x96, 0x82, 0x78, 0x6E, 0x64, 0x06, 0x08, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x25, 0x0D, 0x03, 0x04, 0x27, 0x16, 0x54, 0x05, 0x04, 0x11, 0x03, 0x54, 0x15, 0x04, 0x11, 0x05, 0x80, 0x05, 0x0B, 0x11, 0x03, 0x08, 0xC2, 0x01, 0x04, 0x15, 0x11, 0x00, 0x0D, 0x04, 0xC2, 0x07, 0x06, 0x0D, 0x0D, 0x00, 0x0D, 0x06, 0xC2, 0x09, 0x08, 0x09, 0x09, 0x00, 0x0D, 0x08, 0xC2, 0x0B, 0x0A, 0x05, 0x05, 0x00, 0x0D, 0x0A, 0x82, 0x03, 0x06, 0x03, 0x0F, 0x08, 0x00, 0x04, 0x06, 0x54, 0x04, 0x10, 0x04, 0x04, 0xFF),
    Array(0x0B, 0x14, 0x32, 0x00, 0x00, 0x04, 0x66, 0x97, 0x64, 0x06, 0x06, 0x06, 0x06, 0x06, 0x78, 0x78, 0x96, 0x96, 0xF0, 0x0B, 0x08, 0x09, 0x00, 0x00, 0x00, 0x10, 0x08, 0x00, 0x64, 0x50, 0x02, 0x00, 0x42, 0x0A, 0x03, 0x09, 0x04, 0x42, 0x14, 0x03, 0x09, 0x04, 0x42, 0x1E, 0x03, 0x09, 0x04, 0x42, 0x09, 0x16, 0x09, 0x00, 0x42, 0x0C, 0x0F, 0x11, 0x02, 0x42, 0x05, 0x0B, 0x09, 0x02, 0x42, 0x0F, 0x0B, 0x09, 0x02, 0x42, 0x19, 0x0B, 0x09, 0x02, 0x42, 0x1C, 0x13, 0x0B, 0x01, 0x14, 0x04, 0x03, 0x14, 0x0E, 0x03, 0x14, 0x18, 0x03, 0x14, 0x22, 0x03, 0x14, 0x04, 0x16, 0x14, 0x23, 0x15, 0x25, 0x14, 0x14, 0x04, 0x26, 0x11, 0xFF),
    Array(0x0C, 0x14, 0x14, 0x00, 0x00, 0x3C, 0x02, 0x3B, 0x66, 0x13, 0x13, 0x0E, 0x10, 0x15, 0xB4, 0xAA, 0xA0, 0xA0, 0xA0, 0x0C, 0x0A, 0x09, 0x00, 0x00, 0x00, 0x10, 0x14, 0x00, 0x3C, 0x32, 0x09, 0x00, 0x42, 0x0A, 0x05, 0x12, 0x04, 0x42, 0x0E, 0x05, 0x12, 0x04, 0x42, 0x12, 0x05, 0x12, 0x04, 0x42, 0x16, 0x05, 0x12, 0x04, 0x42, 0x02, 0x06, 0x0B, 0x02, 0x42, 0x02, 0x0A, 0x0B, 0x02, 0x42, 0x02, 0x0E, 0x0F, 0x02, 0x42, 0x02, 0x12, 0x0B, 0x02, 0x81, 0x1E, 0x04, 0x04, 0x04, 0x00, 0x08, 0x20, 0x05, 0x81, 0x1E, 0x09, 0x04, 0x04, 0x00, 0x08, 0x20, 0x0A, 0x81, 0x1E, 0x0E, 0x04, 0x04, 0x00, 0x08, 0x20, 0x0F, 0x25, 0x03, 0x14, 0x04, 0x27, 0x16, 0xFF),
    Array(0x13, 0x04, 0x0A, 0x00, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0E, 0x0E, 0x0E, 0x0E, 0x0E, 0x14, 0x14, 0x14, 0x14, 0x14, 0x06, 0x08, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x87, 0x00, 0x02, 0x28, 0x16, 0x07, 0x87, 0x00, 0x02, 0x14, 0x0C, 0x00, 0x54, 0x01, 0x0C, 0x12, 0x02, 0x88, 0x0F, 0x09, 0x04, 0x04, 0x08, 0x25, 0x08, 0x03, 0x04, 0x12, 0x07, 0xFF), 
    
    Array(0x0D, 0x8C, 0x05, 0x08, 0x00, 0x01, 0x02, 0x03, 0x04, 0x32, 0x37, 0x3C, 0x46, 0x50, 0xA0, 0x9B, 0x96, 0x91, 0x8C, 0x06, 0x08, 0x0D, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x28, 0x00, 0x00, 0x00, 0x25, 0x12, 0x03, 0x04, 0x0A, 0x03, 0x3A, 0x14, 0x03, 0x42, 0x05, 0x12, 0x1E, 0x02, 0x70, 0x05, 0x13, 0x1E, 0x02, 0x50, 0x05, 0x14, 0x1E, 0x02, 0xC1, 0x05, 0x15, 0x1E, 0x02, 0xFF),
    Array(0x0E, 0x14, 0x0A, 0x14, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1E, 0x23, 0x28, 0x2A, 0x2D, 0x96, 0x91, 0x8C, 0x87, 0x82, 0x0C, 0x08, 0x09, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x81, 0x0A, 0x0A, 0x0D, 0x0D, 0x00, 0x70, 0x0B, 0x0B, 0x0C, 0x03, 0xC1, 0x0C, 0x0A, 0x03, 0x0D, 0xC1, 0x10, 0x0A, 0x03, 0x0D, 0xC1, 0x14, 0x0A, 0x03, 0x0D, 0x50, 0x16, 0x08, 0x0C, 0x02, 0x48, 0x16, 0x07, 0x0C, 0x02, 0xC1, 0x17, 0x06, 0x03, 0x04, 0xC1, 0x1B, 0x06, 0x03, 0x04, 0xC1, 0x1F, 0x06, 0x03, 0x04, 0x25, 0x03, 0x03, 0x04, 0x27, 0x14, 0xFF),
    Array(0x0F, 0x08, 0x0A, 0x14, 0x01, 0x1D, 0x1E, 0x1F, 0x20, 0x0F, 0x14, 0x14, 0x19, 0x1E, 0x78, 0x78, 0x78, 0x78, 0x8C, 0x08, 0x0E, 0x09, 0x00, 0x00, 0x00, 0x10, 0x08, 0x00, 0x64, 0x50, 0x02, 0x00, 0x42, 0x02, 0x04, 0x0A, 0x03, 0x42, 0x0F, 0x0D, 0x0A, 0x01, 0x41, 0x0C, 0x0E, 0x03, 0x02, 0x43, 0x0C, 0x0F, 0x03, 0x02, 0x04, 0x14, 0x16, 0x25, 0x14, 0x03, 0xFF),
    Array(0x10, 0x14, 0x0A, 0x14, 0x01, 0x78, 0x81, 0x7E, 0x7B, 0x0C, 0x0F, 0x0F, 0x0F, 0x0C, 0x96, 0x96, 0x96, 0x96, 0x96, 0x09, 0x0A, 0x09, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x32, 0x00, 0x00, 0x00, 0x25, 0x01, 0x03, 0x04, 0x27, 0x04, 0x81, 0x08, 0x13, 0x04, 0x04, 0x00, 0x08, 0x0A, 0x14, 0xC2, 0x07, 0x0A, 0x06, 0x08, 0x43, 0x07, 0x0A, 0x06, 0x02, 0x81, 0x10, 0x13, 0x04, 0x04, 0x00, 0x08, 0x12, 0x14, 0xC2, 0x0F, 0x0A, 0x06, 0x08, 0x43, 0x0F, 0x0A, 0x06, 0x02, 0x81, 0x18, 0x13, 0x04, 0x04, 0x00, 0x08, 0x1A, 0x14, 0x81, 0x20, 0x13, 0x04, 0x04, 0x00, 0x08, 0x22, 0x14, 0xFF),
    Array(0x14, 0x03, 0x1E, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x06, 0x06, 0x06, 0x06, 0x14, 0x14, 0x14, 0x14, 0x14, 0x06, 0x08, 0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x87, 0x00, 0x02, 0x28, 0x16, 0x07, 0x87, 0x00, 0x02, 0x14, 0x0C, 0x01, 0xD0, 0x0B, 0x03, 0x03, 0x02, 0x80, 0x0B, 0x07, 0x03, 0x06, 0x00, 0x43, 0x0B, 0x06, 0x03, 0x02, 0x43, 0x0B, 0x0A, 0x03, 0x02, 0x50, 0x08, 0x07, 0x03, 0x03, 0x25, 0x03, 0x03, 0x04, 0x09, 0x0A, 0xFF) 
  )
 
  val names = List(
    "Intro",      "Rooms",        "Maze",       "Butterflies",		"Interval 1",
    "Guards",     "Firefly dens", "Amoeba",     "Enchanted wall",	"Interval 2",
    "Greed",      "Tracks",       "Crowd",      "Walls",			"Interval 3",
    "Apocalypse", "Zigzag",       "Funnel",     "Enchanted boxes",	"Interval 4"
  )

  val descriptions = List(
    "Pick up jewels and exit before time is up",
    "Pick up jewels, but you must move boulders to get all jewels",
    "Pick up jewels. You must get every jewel to exit",
    "Drop boulders on butterflies to create jewels",
    "Interval 1",
    
    "The jewels are there for grabbing, but they are guarded by the deadly fireflies",
    "Each firefly is guarding a jewel",
    "Surround the amoeba with boulders. Pick up jewels when it suffocates",
    "Activate the enchanted wall and create as many jewels as you can",
    "Interval 2",
    
    "You have to get a lot of jewels here, lucky there are so many",
    "Get the jewels, avoid the fireflies",
    "You must move a lot of boulders around in some tight spaces",
    "Drop a boulder on a firefly at the right time to blast through walls",
    "Interval 3",
    
    "Bring the butterflies and amoeba together and watch the jewels fly",
    "Magically transform the butterflies into jewels, but don't waste any boulders",
    "There is an enchanted wall at the bottom of the rock tunnel",
    "The top of each room is an enchanted wall, but you'll have to blast your way inside",    
    "Interval 4"
  )
  
  def level(i: Int): (String, String, Level) = 
    (names(i), descriptions(i), decode(data(i)))
  
  def decode(data: Array[Int]): Level = {
    val random = new RandomImpl()
    val builder = new Builder()
    val decoder = new Decoder(builder, random)
    
    decoder.decodeBase(data)
    decoder.initRandomized()
    decoder.createBorder()
    decoder.decode(data.drop(32).toList)
    
    builder.build
  }
  
  class Decoder(builder: Builder, random: Random) {
    val width = 40
    val height = 22    
    
    def decodeBase(data: Array[Int]): Decoder = {
      builder.amoebaSlowGrowthTime = data(1)
      builder.magicWallMillingTime = data(1)
      builder.initialDiamondValue  = data(2)
      builder.extraDiamondValue    = data(3)
      builder.randomSeed           = data(4)
      builder.diamondsNeeded       = data(9)
      builder.caveTime             = data(14)
      builder.randomObjects        = Array(data(24), data(25), data(26), data(27))
      builder.randomObjectProb     = Array(data(28), data(29), data(30), data(31))
      random.seed(0, data(4))
      this
    }
    
    def initRandomized(): Decoder = {
      for (y <- 1 until height-1) {
        for (x <- 0 until width) {
          var tile: Tile = Dirt
          val r = random.next()
          
          for (i <- 0 until builder.randomObjects.length) {
        	if (r < builder.randomObjectProb(i)) {
        	  tile = decodeTile(builder.randomObjects(i))
        	}            
          }
          builder.drawSingle(x, y, tile)
        }
      }
      this
    }
    
    def createBorder(): Decoder = {
      builder.drawRect(0, 0, width, height, SteelWall)
      this
    }
    
    def decode(data: List[Int]): Decoder = {
      @tailrec
      def decodeLoop(d: List[Int]) {
        d match {
          case 255 :: Nil =>
          case obj :: data => decodeLoop(decodeOne(obj, data))
          case Nil =>
        }
      }
      
      decodeLoop(data)
      
      this
    }
    
    private def decodeOne(obj: Int, data: List[Int]): List[Int] = {
      val kind = (obj & 0xC0) >> 6
      val tile = decodeTile(obj & 0x3F)
      (kind, data) match {
        case (0, x :: y :: tail) => builder.drawSingle(x, y - 2, tile); tail
        case (1, x :: y :: length :: dir :: tail) => builder.drawLine(x, y - 2, length, decodeDirection(dir), tile); tail
        case (2, x :: y :: width :: height :: fill :: tail) => builder.drawFilledRect(x, y - 2, width, height, tile, decodeTile(fill)); tail
        case (3, x :: y :: width :: height :: tail) => builder.drawRect(x, y - 2, width, height, tile); tail
        case _ => throw new UnsupportedOperationException("Can't decode kind=" + kind + ", tile=" + tile + ", data=" + data)
      }
    }
    
    def decodeTile(code: Int): Tile = code match {
      case 0x00   => Space
      case 0x01 => Dirt
      case 0x02 => Wall
      case 0x03 => MagicWall
      case 0x04 => PreExit
      case 0x07 => SteelWall
      case 0x08 => Firefly(Left)
      case 0x10 => Boulder
      case 0x14 => Diamond
      case 0x30 => Butterfly(Down)
      case 0x25 => PlayerCharacter
      case 0x38 => PlayerCharacter
      case 0x3A => Amoeba
      case _ => throw new UnsupportedOperationException("Can't decode 0x" + code.toHexString + " to a tile")
    }
    
    def decodeDirection(dir: Int): Offset = {
      assert(dir >= 0 && dir <= 7, "parameter must be between 0 and 7")
      
      dir match {
        case 0 => Delta(0, -1)
        case 1 => Delta(1, -1)
        case 2 => Delta(1, 0)
        case 3 => Delta(1, 1)
        case 4 => Delta(0, 1)
        case 5 => Delta(-1, 1)
        case 6 => Delta(-1, 0)
        case 7 => Delta(-1, -1)
      }
    }
  }
  
  
  trait Random {
    def next(): Int
    def seed(a: Int, b: Int)
  }
  
  class RandomImpl extends Random {    
    private var seed0 = 0
    private var seed1 = 0
    
    def seed(a: Int, b: Int) {
      seed0 = a
      seed1 = b
    }
    
    def next() = {
      assert((seed0 >= 0) && (seed0 <= 0xFF), "expected seed 0 to be between 0 and 0xFF")
      assert((seed1 >= 0) && (seed1 <= 0xFF), "expected seed 1 to be between 0 and 0xFF")

      val tmp1 = (seed0 & 0x0001) * 0x0080
      val tmp2 = (seed1 >> 1) & 0x007F

      var result   = seed1 + (seed1 & 0x0001) * 0x0080
      var carry    = if (result > 0x00FF) 1 else 0
      result   = result & 0x00FF
      result   = result + carry + 0x13
      carry    = if (result > 0x00FF) 1 else 0
      seed1 = result & 0x00FF
      result   = seed0 + carry + tmp1
      carry    = if (result > 0x00FF) 1 else 0
      result   = result & 0x00FF
      result   = result + carry + tmp2
      seed0 = result & 0x00FF

      assert((seed0 >= 0) && (seed0 <= 0xFF), "expected seed 0 to STILL be between 0 and 0xFF")
      assert((seed1 >= 0) && (seed1 <= 0xFF), "expected seed 0 to STILL be between 0 and 0xFF")      
      
      seed0
    }
    
  }
  
  class Builder {
    
    private val level = new Level()
    
    var amoebaSlowGrowthTime = 0
    var magicWallMillingTime = 0
    var initialDiamondValue  = 0
    var extraDiamondValue    = 0
    var randomSeed           = 0
    var diamondsNeeded       = 0
    var caveTime             = 0
    var randomObjects        = Array[Int]()
    var randomObjectProb     = Array[Int]()
    
    private def setParams() {
      level.slowGrowth = amoebaSlowGrowthTime
      level.diamondsWorth = initialDiamondValue
      level.extraDiamondsWorth = extraDiamondValue
      level.caveTime = caveTime
      level.diamondsNeeded = diamondsNeeded
    }
    
    def build() = {
      setParams()
      level
    }
    
    def drawRect(x: Int, y: Int, width: Int, height: Int, tile: Tile) {
      for(dx <- 0 until width) {
        drawSingle(x+dx, y, tile)
        drawSingle(x+dx, y+height-1, tile)
      }
      for (dy <- 1 until height-1) {
        drawSingle(x, y+dy, tile)
        drawSingle(x+width-1, y+dy, tile)
      }
    }
    
    def drawSingle(x: Int, y: Int, tile: Tile) {
      assert(x >= 0 && x < 40, "x must be [0, 40[, was: " + x)
      assert(y >= 0 && y < 22, "y must be [0, 22[, was: " + y)
      level.set(x, y)(tile)
    }
    
    def drawLine(x: Int, y: Int, length: Int, direction: Offset, tile: Tile) {
      var lx = x
      var ly = y
      for (d <- 0 until length) {
        drawSingle(lx, ly, tile)
        lx += direction.dx
        ly += direction.dy
      }
    }
    
    def drawFilledRect(x: Int, y: Int, width: Int, height: Int, edge: Tile, fill: Tile) {
      drawRect(x, y, width, height, edge)
      for (dx <- 1 until width-1) {
        for (dy <- 1 until height-1) {
          drawSingle(x + dx, y + dy, fill)
        }
      }
    }
  }
}