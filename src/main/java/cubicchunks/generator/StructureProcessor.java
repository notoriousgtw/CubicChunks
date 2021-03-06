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
package cubicchunks.generator;

import net.minecraft.world.World;
import cubicchunks.generator.structures.CubicCaveGenerator;
import cubicchunks.generator.structures.CubicRavineGenerator;
import cubicchunks.generator.structures.CubicStructureGenerator;
import cubicchunks.util.processor.CubeProcessor;
import cubicchunks.world.ICubeCache;
import cubicchunks.world.cube.Cube;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;

@SuppressWarnings("unused")
public class StructureProcessor extends CubeProcessor {
	
	private CubicCaveGenerator caveGenerator;
	private MapGenStronghold strongholdGenerator;
	private MapGenVillage villageGenerator;
	private MapGenMineshaft mineshaftGenerator;
//	private MapGenScatteredFeature scatteredFeatureGenerator;
	private CubicStructureGenerator ravineGenerator;
	
	private World worldObj;
	
	public StructureProcessor(String name, ICubeCache provider, int batchSize) {
		super(name, provider, batchSize);
		
		this.caveGenerator = new CubicCaveGenerator();
		this.strongholdGenerator = new MapGenStronghold();
		this.villageGenerator = new MapGenVillage();
		this.mineshaftGenerator = new MapGenMineshaft();
//		this.scatteredFeatureGenerator = new TempleGenerator();
		this.ravineGenerator = new CubicRavineGenerator();
	}
	
	@Override
	public boolean calculate(Cube cube) {
		
		if(!this.canGenerate(cube)) {
			return false;
		}
		this.worldObj = cube.getWorld();
		
		// generate world features
		if(!cube.isEmpty()) {	
			this.caveGenerator.generate(this.worldObj, cube);
			this.ravineGenerator.generate(this.worldObj, cube);
		}
		/*
		UNDONE: enable feature generation 
		if( m_mapFeaturesEnabled ) { 
			m_mineshaftGenerator.func_151539_a( null, m_world, cubeX, cubeZ, m_blocks ); 
			m_villageGenerator.func_151539_a( null, m_world, cubeX, cubeZ, m_blocks ); 
			m_strongholdGenerator.func_151539_a( null, m_world, cubeX, cubeZ, m_blocks ); 
			m_scatteredFeatureGenerator.func_151539_a( null, m_world, cubeX, cubeZ, m_blocks ); 
		}
		 */
		
		return true;
	}

	private boolean canGenerate(Cube cube) {
		//BiomeProcessor requires that we make sure that we don't generate structures 
		//when biome blocks aren't placed in cube below
		int cubeX = cube.getX();
		int cubeY = cube.getY() - 1;
		int cubeZ = cube.getZ();
		boolean exists = this.cache.cubeExists(cubeX, cubeY, cubeZ);
		if (!exists) {
			return false;
		}
		Cube below = this.cache.getCube(cubeX, cubeY, cubeZ);
		return !below.getGeneratorStage().isLessThan(GeneratorStage.STRUCTURES);
	}
}
