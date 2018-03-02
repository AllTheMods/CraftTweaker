package crafttweaker.zenscript;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.runtime.GlobalFunctions;
import stanhebben.zenscript.*;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.compiler.*;
import stanhebben.zenscript.impl.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.*;
import stanhebben.zenscript.type.ZenTypeNative;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.ToIntFunction;
import java.util.logging.*;

/**
 * @author Stan
 */
public class GlobalRegistry {
    private static final IZenErrorLogger errors = new CrTErrorLogger();
    private static final IZenCompileEnvironment environment = new CrTCompileEnvironment();
    public static final GenericRegistry INSTANCE = new GenericRegistry(environment, errors);
    
    private static final Map<String, IZenSymbol> globals = new HashMap<>();
    private static final Set<IBracketHandler> bracketHandlers = new TreeSet<>(Comparator.comparingInt((ToIntFunction<IBracketHandler>) IBracketHandler::getPriority).thenComparing(o -> o.getClass().getName()));
    private static final TypeRegistry types = new TypeRegistry();
    private static final SymbolPackage root = new SymbolPackage("<root>");
    
    private static final Map<String, TypeExpansion> expansions = new HashMap<>();
    
    static {
        registerGlobal("print", getStaticFunction(GlobalFunctions.class, "print", String.class));
        registerGlobal("totalActions", getStaticFunction(GlobalFunctions.class, "totalActions"));
        registerGlobal("enableDebug", getStaticFunction(GlobalFunctions.class, "enableDebug"));
        registerGlobal("isNull", getStaticFunction(GlobalFunctions.class, "isNull", Object.class));
        registerGlobal("max", getStaticFunction(Math.class, "max", int.class, int.class));
        registerGlobal("min", getStaticFunction(Math.class, "min", int.class, int.class));
        registerGlobal("pow", getStaticFunction(Math.class, "pow", double.class, double.class));
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
        int prio = 10;
        if(handler.getClass().getAnnotation(BracketHandler.class) != null) {
            prio = handler.getClass().getAnnotation(BracketHandler.class).priority();
        } else {
            CraftTweakerAPI.logInfo(handler.getClass().getName() + " is missing a BracketHandler annotation, setting the priority to " + prio);
        }
        bracketHandlers.add(handler);
    }
    
    public static void removeBracketHandler(IBracketHandler handler) {
        IBracketHandler prioPair = null;
        for(IBracketHandler pair : bracketHandlers) {
            if(pair.equals(handler)) {
                prioPair = pair;
            }
        }
        
        bracketHandlers.remove(prioPair);
    }
    
    public static void registerNativeClass(Class<?> cls) {
        try {
            ZenTypeNative type = new ZenTypeNative(cls);
            type.complete(types);
            root.put(type.getName(), new SymbolType(type), errors);
        } catch(Throwable ex) {
            ex.printStackTrace();
        }
    }
    
    public static IZenSymbol resolveBracket(IEnvironmentGlobal environment, List<Token> tokens) {
        /*int tokensSize = tokens.size();
        if (tokensSize <= 0) return null;
    
        String s = tokens.get(0).getValue();
        for(int i = 1; i < tokensSize; i++) {
            s = s.concat(tokens.get(i).getValue());
        }*/
        
        
        for(IBracketHandler pair : bracketHandlers) {
            /*if (!pair.getRegexPattern().matcher(s).matches()) continue;*/
            
            IZenSymbol symbol = pair.resolve(environment, tokens);
            if(symbol != null) {
                return symbol;
            }
        }
        
        return null;
    }
    
    public static IZenSymbol getStaticFunction(Class cls, String name, Class... arguments) {
        IJavaMethod method = JavaMethod.get(types, cls, name, arguments);
        return new SymbolJavaStaticMethod(method);
    }
    
    public static IZenSymbol getStaticField(Class cls, String name) {
        try {
            Field field = cls.getDeclaredField(name);
            return new SymbolJavaStaticField(cls, field, types);
        } catch(NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(GlobalRegistry.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static IEnvironmentGlobal makeGlobalEnvironment(Map<String, byte[]> classes) {
        return new CrTGlobalEnvironment(classes);
    }
    
    public static Map<String, IZenSymbol> getGlobals() {
        return globals;
    }
    
    public static Set<IBracketHandler> getPrioritizedBracketHandlers() {
        return bracketHandlers;
    }
    
    @Deprecated
    public static List<IBracketHandler> getBracketHandlers() {
        return new ArrayList<>(getPrioritizedBracketHandlers());
    }
    
    public static TypeRegistry getTypes() {
        return types;
    }
    
    public static SymbolPackage getRoot() {
        return root;
    }
    
    public static IZenErrorLogger getErrors() {
        return errors;
    }
    
    public static IZenCompileEnvironment getEnvironment() {
        return environment;
    }
    
    public static Map<String, TypeExpansion> getExpansions() {
        return expansions;
    }
}
