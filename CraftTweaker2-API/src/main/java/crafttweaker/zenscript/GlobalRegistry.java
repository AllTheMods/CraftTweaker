package crafttweaker.zenscript;

import crafttweaker.runtime.GlobalFunctions;
import stanhebben.zenscript.*;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.impl.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.*;

import java.util.*;

/**
 * @author Stan
 */
public class GlobalRegistry {
    private static final IZenErrorLogger errors = new CrTErrorLogger();
    private static final IZenCompileEnvironment environment = new GenericCompileEnvironment();
    public static final GenericRegistry INSTANCE = new GenericRegistry(environment, errors);
    
    static {
        registerGlobal("print", getStaticFunction(GlobalFunctions.class, "print", String.class));
        registerGlobal("totalActions", getStaticFunction(GlobalFunctions.class, "totalActions"));
        registerGlobal("enableDebug", getStaticFunction(GlobalFunctions.class, "enableDebug"));
        registerGlobal("isNull", getStaticFunction(GlobalFunctions.class, "isNull", Object.class));
        registerGlobal("max", getStaticFunction(Math.class, "max", int.class, int.class));
        registerGlobal("min", getStaticFunction(Math.class, "min", int.class, int.class));
        registerGlobal("pow", getStaticFunction(Math.class, "pow", double.class, double.class));
    
    
        INSTANCE.registerNativeClass(List.class);
        INSTANCE.registerAdapter(List.class, "", List.class.getMethods());
        INSTANCE.registerAdapter(Arrays.class, "", Arrays.class.getMethods());
    }
    
    private GlobalRegistry() {
    }
    
    public static void registerGlobal(String name, IZenSymbol symbol) {
        INSTANCE.registerGlobal(name, symbol);
    }
    
    public static void registerExpansion(Class<?> cls) {
        INSTANCE.registerExpansion(cls);
    }
    
    public static void registerBracketHandler(IBracketHandler handler) {
        INSTANCE.registerBracketHandler(handler);
    }
    
    public static void removeBracketHandler(IBracketHandler handler) {
        INSTANCE.removeBracketHandler(handler);
    }
    
    public static void registerNativeClass(Class<?> cls) {
        INSTANCE.registerNativeClass(cls);
    }
    
    public static IZenSymbol resolveBracket(IEnvironmentGlobal environment, List<Token> tokens) {
        return INSTANCE.resolveBracket(environment, tokens);
    }
    
    public static IZenSymbol getStaticFunction(Class cls, String name, Class... arguments) {
        return INSTANCE.getStaticFunction(cls, name, arguments);
    }
    
    public static IZenSymbol getStaticField(Class cls, String name) {
        return INSTANCE.getStaticField(cls, name);
    }
    
    public static IEnvironmentGlobal makeGlobalEnvironment(Map<String, byte[]> classes) {
        return INSTANCE.makeGlobalEnvironment(classes);
    }
    
    public static Map<String, IZenSymbol> getGlobals() {
        return INSTANCE.getGlobals();
    }
    
    public static Set<IBracketHandler> getPrioritizedBracketHandlers() {
        return INSTANCE.getBracketHandlers();
    }
    
    @Deprecated
    public static List<IBracketHandler> getBracketHandlers() {
        return new ArrayList<>(getPrioritizedBracketHandlers());
    }
    
    public static TypeRegistry getTypes() {
        return INSTANCE.getTypes();
    }
    
    public static SymbolPackage getRoot() {
        return INSTANCE.getRoot();
    }
    
    public static IZenErrorLogger getErrors() {
        return INSTANCE.getErrorLogger();
    }
    
    public static IZenCompileEnvironment getEnvironment() {
        return INSTANCE.getCompileEnvironment();
    }
    
    public static Map<String, TypeExpansion> getExpansions() {
        return INSTANCE.getExpansions();
    }
}
