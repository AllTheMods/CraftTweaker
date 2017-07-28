package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import crafttweaker.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("byte")
@ZenRegister
public class ExpandByte {

    @ZenCaster
    public static IData asData(byte value) {
        return new DataByte(value);
    }
}
