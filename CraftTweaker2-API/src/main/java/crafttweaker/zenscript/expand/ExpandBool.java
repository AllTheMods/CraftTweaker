package crafttweaker.zenscript.expand;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.*;
import crafttweaker.annotations.*;

/**
 * @author Stanneke
 */
@ZenExpansion("bool")
@ZenRegister
public class ExpandBool {
    
    @ZenCaster
    public static IData asData(boolean value) {
        return new DataBool(value);
    }
}
