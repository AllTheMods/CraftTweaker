/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openzen.zencode.symbolic.type.casting;

import stanhebben.zenscript.type.ZenType;
import org.openzen.zencode.util.MethodOutput;
import org.openzen.zencode.runtime.IAny;
import org.openzen.zencode.symbolic.TypeRegistry;

/**
 *
 * @author Stan
 */
public class CastingAnyFloat implements ICastingRule {
	private final TypeRegistry types;
	
	public CastingAnyFloat(TypeRegistry types) {
		this.types = types;
	}
	
	@Override
	public void compile(MethodOutput output) {
		output.invokeInterface(IAny.class, "asFloat", float.class);
	}

	@Override
	public ZenType getInputType() {
		return types.ANY;
	}

	@Override
	public ZenType getResultingType() {
		return types.FLOAT;
	}
}
