/*
 *  This file is part of Cubic Chunks Mod, licensed under the MIT License (MIT).
 *
 *  Copyright (c) 2015 contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package cubicchunks.asm;

import cubicchunks.CubicChunkSystem;
import cubicchunks.CubicChunks;
import cubicchunks.util.AddressTools;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

/**
 * This class has methods to get information about works.
 * Should be used from asm transformed code.
 */
public final class WorldMethods {
	private static CubicChunkSystem cc;

	public static int getMinHeight(World world) {
		Integer h = cc.getMinBlockY(world);
		if(h != null) {
			return h;
		}
		return 0;
	}

	public static int getMaxHeight(World world) {
		Integer h = cc.getMaxBlockY(world);
		if(h != null) {
			return h;
		}
		return 256;
	}

	public static boolean isTallWorld(World world) {
		return cc.isTallWorld(world);
	}

	public static int getMaxHeight(WorldType world) {
		return world == CubicChunks.CC_WORLD_TYPE ? AddressTools.MaxY * 16 : 256;
	}

	public static void registerChunkSystem(CubicChunkSystem cc) {
		WorldMethods.cc = cc;
	}

	public static Boolean isAreaLoaded(World world, int minx, int miny, int minz, int maxx, int maxy, int maxz, boolean allowEmpty) {
		return cc.checkBlockRangeIsInWorld(world, minx, miny, minz, maxx, maxy, maxz, allowEmpty);
	}
}
