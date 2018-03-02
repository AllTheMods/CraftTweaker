package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.*;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.impl.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerCreativeTab implements IBracketHandler<ICreativeTab> {
    
    private final IJavaMethod method;
    
    public BracketHandlerCreativeTab() {
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerCreativeTab.class, "getTabFromString", String.class);
    }
    
    public static ICreativeTab getTabFromString(String name) {
        ICreativeTab tab = CraftTweakerMC.creativeTabs.get(name);
        if (tab == null) CraftTweakerAPI.logError("Couldn't find creative tab with name " + name);
        return tab;
    }
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() < 3)
            return null;
        if(!(tokens.get(0).getValue().equalsIgnoreCase("creativetab") && tokens.get(1).getValue().equals(":")))
            return null;
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, tokens.subList(2, tokens.size()).stream().map(Token::getValue).collect(Collectors.joining())));
    }
    
    private static final Pattern PATTERN = Pattern.compile("creativetab:.*");
    @Override
    public Pattern getRegexPattern() {
        return PATTERN;
    }
    
    @Override
    public Class<ICreativeTab> getReferenceClass() {
        return ICreativeTab.class;
    }
    
    @Override
    public ICreativeTab resolve(List<String> strings) {
        return null;
    }
    
    @Override
    public int getPriority() {
        return 100;
    }
}
