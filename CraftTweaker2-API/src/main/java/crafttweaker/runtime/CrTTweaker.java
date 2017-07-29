package crafttweaker.runtime;

import crafttweaker.*;
import crafttweaker.sandbox.Sandbox;

import java.util.*;


/**
 * @author Stan Hebben
 */
public class CrTTweaker implements ITweaker {
    
    private static boolean DEBUG = true;
    
    private final List<IAction> actions = new ArrayList<>();
    private IScriptProvider scriptProvider;
    
    @Override
    public void apply(IAction action) {
        CraftTweakerAPI.logInfo(action.describe());
        action.apply();
        actions.add(action);
    }
    
    @Override
    public void setScriptProvider(IScriptProvider provider) {
        scriptProvider = provider;
    }
    
    @Override
    public void load() {
        loadScript(true);
    }

    @Override
    public boolean loadScript(boolean executeScripts){
        System.out.println("Loading scripts");
    
        boolean success = Sandbox.loadScripts();
        
        Sandbox.runAllScripts();
        
        return success;
    }

    @Override
    public List<IAction> getActions() {
        return actions;
    }
    
    @Override
    public void enableDebug() {
        DEBUG = true;
    }
}
