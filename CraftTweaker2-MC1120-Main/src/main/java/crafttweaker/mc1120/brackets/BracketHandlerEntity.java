package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.*;
import crafttweaker.api.entity.IEntityDefinition;

import java.util.*;

/**
 * @author Jared
 */
@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerEntity implements IBracketHandler {
    
    private static final Map<String, IEntityDefinition> entityNames = new HashMap<>();
    
    /*private final IJavaMethod method;
    
    public BracketHandlerEntity() {
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerEntity.class, "getEntity", String.class);
    }*/
    
    public static void rebuildEntityRegistry() {
        entityNames.clear();
        CraftTweakerAPI.game.getEntities().forEach(ent -> entityNames.put(ent.getName().toLowerCase(), ent));
    }
    
    public static IEntityDefinition getEntity(String name) {
        return entityNames.get(name.toLowerCase());
    }
    
    /*
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() > 2) {
            if(tokens.get(0).getValue().equals("entity") && tokens.get(1).getValue().equals(":")) {
                return find(environment, tokens, 2, tokens.size());
            }
        }
        
        return null;
    }
    
    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        for(int i = startIndex; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, valueBuilder.toString()));
    }*/
    
}
