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
package cubicchunks.asm.transformer;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static cubicchunks.asm.Mappings.*;
import static org.objectweb.asm.Opcodes.*;

public class RenderGlobalGetRenderChunkOffset extends AbstractMethodTransformer {
	public RenderGlobalGetRenderChunkOffset(MethodVisitor mv) {
		super(ASM4, mv);
	}

	private boolean minHeightTransformed, maxHeightTransformed;
	@Override
	public void visitJumpInsn(int opcode, Label label) {
		if(opcode == IFLT) {
			this.loadHeight("getMinHeight");
			super.visitJumpInsn(IF_ICMPLT, label);
			this.minHeightTransformed = true;
			this.updateState();
			return;
		}
		super.visitJumpInsn(opcode, label);
	}

	@Override
	public void visitIntInsn(int opcode, int arg) {
		if(opcode == SIPUSH) {
			loadHeight("getMaxHeight");
			this.maxHeightTransformed = true;
			this.updateState();
			return;
		}
		super.visitIntInsn(opcode, arg);
	}

	private void updateState() {
		if(this.minHeightTransformed && this.maxHeightTransformed) {
			this.setSuccessful();
		}
	}

	private void loadHeight(String methodName) {
		super.visitVarInsn(ALOAD, 0);
		super.visitFieldInsn(GETFIELD, RENDER_GLOBAL, RENDER_GLOBAL_THE_WORLD, WORLD_CLIENT_FIELD_DESC);
		super.visitMethodInsn(INVOKESTATIC, WORLD_METHODS, methodName, WORLD_METHODS_GET_HEIGHT_DESC, false);
	}
}
