package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import crafttweaker.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("float")
@ZenRegister
public class ExpandFloat {

    @ZenCaster
    public static IData asData(float value) {
        return new DataFloat(value);
    }
}
