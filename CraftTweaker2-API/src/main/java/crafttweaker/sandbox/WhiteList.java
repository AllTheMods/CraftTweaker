package crafttweaker.sandbox;

/**
 * Created by Jonas on 27.05.2017.
 */
public class WhiteList {

    /**
     * Whitelist for all the Stuff
     * Syntax: "path.to.class" --> for hole class
     *          "path.to.class#function" --> for only one function
     */
    static final String[] Patterns = new String[]{
            // allow method calls on Math
            "java.lang.Math#",
            java.util.Arrays.class.getName(),

            // because we let the user call each/times/...
            "org.codehaus.groovy.runtime.DefaultGroovyMethods#",
            "org.codehaus.groovy.runtime.StringGroovyMethods#",
            "groovy.lang.Script#println",
            "groovy.lang.Script#print",
            "atm.pony.api",
            "slimeknights.tconstruct.library",
            "net.minecraft",
            "net.minecraftforge",
            "atm.pony.GroovyScripts",
            "java.lang.Object#",
            "java.util.Random#",
            "java.util.ArrayList"
    };






}
