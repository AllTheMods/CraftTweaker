package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.*;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.mc1120.enchantments.MCEnchantmentDefinition;
import stanhebben.zenscript.impl.IBracketHandler;
import net.minecraft.enchantment.Enchantment;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerEnchantments implements IBracketHandler<IEnchantmentDefinition> {
    
    private static final HashMap<String, IEnchantmentDefinition> enchantments = new HashMap<>();
    private final IJavaMethod method;
    
    public BracketHandlerEnchantments() {
        Enchantment.REGISTRY.getKeys().forEach(key -> enchantments.put(key.toString(), new MCEnchantmentDefinition(Enchantment.REGISTRY.getObject(key))));
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerEnchantments.class, "getEnchantment", String.class);
    }
    
    public static IEnchantmentDefinition getEnchantment(String id) {
        IEnchantmentDefinition result = enchantments.get(id);
        if(result == null)
            CraftTweakerAPI.logError("Could not find Enchantment for the id " + id);
        return result;
    }
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() < 3)
            return null;
        if(!(tokens.get(0).getValue().equalsIgnoreCase("enchantment") && tokens.get(1).getValue().equals(":")))
            return null;
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, tokens.subList(2, tokens.size()).stream().map(Token::getValue).collect(Collectors.joining())));
    }
    
    private static final Pattern PATTERN = Pattern.compile("enchantment:.*");
    @Override
    public Pattern getRegexPattern() {
        return PATTERN;
    }
    
    @Override
    public Class<IEnchantmentDefinition> getReferenceClass() {
        return IEnchantmentDefinition.class;
    }
    
    @Override
    public IEnchantmentDefinition resolve(List<String> strings) {
        return null;
    }
    
    @Override
    public int getPriority() {
        return 100;
    }
}
