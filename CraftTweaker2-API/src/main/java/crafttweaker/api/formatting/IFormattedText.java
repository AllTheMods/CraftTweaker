package crafttweaker.api.formatting;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.formatting.IFormattedText")
@ZenRegister
public interface IFormattedText {
    
    @ZenOperator(OperatorType.ADD)
    IFormattedText add(IFormattedText other);
    
    @ZenOperator(OperatorType.CAT)
    IFormattedText cat(IFormattedText other);
}
