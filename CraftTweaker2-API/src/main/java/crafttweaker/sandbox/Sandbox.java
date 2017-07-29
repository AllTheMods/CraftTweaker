package crafttweaker.sandbox;

import crafttweaker.CraftTweakerAPI;
import groovy.lang.*;
import groovy.util.*;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.control.*;
import org.codehaus.groovy.control.customizers.*;

import java.io.*;
import java.util.*;

/**
 * Created by Jonas on 26.05.2017.
 */

public class Sandbox{
    
    public static File scriptPath = new File("scripts");
    
    private static File[] scriptPaths = new File[] {PersonalDevPath.path, scriptPath};

    private static String[] scriptPathsAsString(File[] files){
        String[] tempArray  = new String[files.length];
        for (int i = 0; i < tempArray.length; i++){
            System.out.println("File: " + files[i]);
            if (files[i] != null) {
                tempArray[i] = files[i].getAbsolutePath();
            }
        }
        System.out.println(Arrays.toString(tempArray));
        return tempArray;
    }

    private static HashMap<ExtractedMethod, List<Script>> functionKnower = new HashMap<>();

    private static ArrayList<Script> scripts = new ArrayList<>();
    static private GroovyScriptEngine scriptEngine;
    static private Binding binding;

    //region >>> Setting up the Sandbox

    public static void initSandbox() {

        CompilerConfiguration conf = new CompilerConfiguration();
        ImportCustomizer customizer = new ImportCustomizer();
            customizer.addStaticStars("java.lang.Math");
            customizer.addStaticStars("org.codehaus.groovy.ast.expr");
            customizer.addStarImports("java.lang.Object");


        SecureASTCustomizer secureAST = new SecureASTCustomizer();

        secureAST.addExpressionCheckers((SecureASTCustomizer.ExpressionChecker) expression -> {

            String text = ExpressionWrapper.getPropertiesString(expression);

            // Explicit blacklist for classes in packages that are auto imported by every groovy class
            // and unfortunately ignored by the secureAST whitelists
            if (text.contains("java.io")
                    || text.contains(".&") //method pointers
                    || text.contains("java.net")
                    || text.contains("goovy.lang.GroovyShell")
                    || text.contains("groovy.util.GroovyScriptEngine")
                    || text.contains("java.lang.System")
                    || text.contains("groovy.util.Eval")
                    || text.contains("java.lang.Runtime")
                    || text.contains("java.lang.SecurityManager")
                    || text.contains("java.lang.ClassLoader")
                    || text.contains("java.lang.ClassValue")
                    || text.contains("java.lang.Process")
                    || text.contains("java.lang.reflect")
                    || text.contains("java.lang.ref")
                    || text.contains("java.lang.instrument")
                    || text.contains("java.lang.management")
                    || text.contains("MetaClassRegistry")
                    || text.contains("GroovySystem")
                    || text.contains("groovy.lang.Grab")
                    || text.contains("groovy.lang.Grapes")
                    || text.contains("GroovyClassLoader")
                    || text.contains("java.lang.invoke")) return false;

            // To blacklist evaluate() calls, but not println() ones
            if (text.contains("evaluate(") || text.contains("invokeMethod(")) return false;

            if (expression instanceof CastExpression) {
                if (expression.getText().contains("java.lang.Class")) return false;
            }

            return !expression.getText().contains("newInstance") && !expression.getText().contains("forName");
        });

        List<String> starImports = new ArrayList<>();

        starImports.add("atm.pony.api.*");
        starImports.add("atm.pony.apiimpl.vanilla.*");
        starImports.add("atm.pony.api.OredictUtils");
        starImports.add("atm.pony.api.wrappers.*");
        starImports.add("atm.pony.apiimpl.vanilla.CraftingRecipeHandler");
        starImports.add("net.minecraftforge.event.entity.player");
        starImports.add("net.minecraftforge.event.world.*");
        starImports.add("net.minecraft.init.Blocks");
        starImports.add("java.lang.Object");
        starImports.add("java.util.Arrays");

        starImports.add("net.minecraft.*");
        starImports.add("net.minecraft.util.*");
        starImports.add("net.minecraftforge.*");
        starImports.add("net.minecraftforge.event.world.BlockEvent$BreakEvent.*");
        starImports.add("net.minecraft.item.ItemStack.*");
        starImports.add("atm.pony.api.NBTUtils.*");
        starImports.add("atm.pony.api.ingredients.*");
        starImports.add("java.lang.String.*");
        starImports.add("atm.pony.apiimpl.vanilla.BrewingRecipeHandler.*");
        starImports.add("atm.pony.apiimpl.vanilla.FurnaceRecipeHandler.*");




        List<String> singleImports = new ArrayList<>();

        singleImports.add("net.minecraft.init.Blocks");
        singleImports.add("net.minecraft.init.Items");
        singleImports.add("net.minecraft.item.ItemStack");
        singleImports.add("java.lang.Object");
        singleImports.add("groovy.lang.Script");
        singleImports.add("java.lang.Array");
        singleImports.add("java.util.Arrays");
        singleImports.add("java.lang.String");


        // secureAST.setIndirectImportCheckEnabled(true);
        // secureAST.setStarImportsWhitelist(starImports);
        // secureAST.setStaticStarImportsWhitelist(starImports);
        // secureAST.setStaticImportsWhitelist(singleImports);
        // secureAST.setImportsWhitelist(singleImports);

        //TODO: improve secure AST transformer
        // conf.addCompilationCustomizers(secureAST);

        binding = new Binding();

        try {
            scriptEngine = new GroovyScriptEngine(scriptPathsAsString(scriptPaths));
            scriptEngine.setConfig(conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LaunchWrapper.init();
    }
    //endregion


    public static boolean loadScripts() {
        scripts.clear();
        boolean success = true;

        try {

            for (File path : scriptPaths) {
                System.out.println("Searching for scripts in: " + path.getAbsolutePath());

                // To prevent empty folders from crashing
                if (path.listFiles() != null) {

                    for (File f : path.listFiles()) {
                        System.out.println("Found file: " + f.getName());

                        if (FilenameUtils.isExtension(f.getName(), "groovy")) {
                            System.out.println("matching groovy for file " + f.getName());
                            try {
                                scripts.add(scriptEngine.createScript(f.getName(), binding));
                            } catch (ResourceException e) {
                                System.out.println("Error while reading the file");
                                success = false;
                                e.printStackTrace();
                            } catch (ScriptException e) {
                                success = false;
                                e.printStackTrace();
                            } catch (GroovyBugError e) {
                                System.out.println(f.getName() + " couldn't get compiled because some function is not allowed: " + e.getMessage());
                                success = false;
                                e.printStackTrace();
                            } catch (MultipleCompilationErrorsException e) {
                                success = false;
                                System.out.println(e.getMessage());
                            } catch (NullPointerException e) {
                                success = false;
                                System.out.println("Why is there a nullpointer");
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            CraftTweakerAPI.getLogger().logWarning(e.toString());
        }

        extractMethods(scripts);
        return success;
    }

    // Adds every method to the the knower, He knows what script has which method
    // to not call each script each time without the method existing
    private static void extractMethods(List<Script> scripts) {
        functionKnower.clear();

        for (Script script : scripts) {
            List<MetaMethod> metaMethods = script.getMetaClass().getMethods();
            // adds the method names to the knower
            for (MetaMethod metaMethod : metaMethods) {
                ExtractedMethod extractedMethod = new ExtractedMethod(metaMethod.getName(), metaMethod.getParameterTypes().length);

                if (functionKnower.containsKey(extractedMethod)) {
                    functionKnower.get(extractedMethod).add(script);
                } else {// adds a new list when the key doesn't exist yet
                    List<Script> scriptList = new ArrayList<>();
                    scriptList.add(script);
                    functionKnower.put(extractedMethod, scriptList);
                }
            }
        }
    }

    public static void runAllScripts() {
        ExtractedMethod extractedMethod = new ExtractedMethod("main", 1);

        if (functionKnower.containsKey(extractedMethod)) {
            for (Script script : functionKnower.get(extractedMethod)) {

                try {
                    LaunchWrapper.run(script);

                } catch (MultipleCompilationErrorsException e) {
                    System.out.println("Successful sandboxing: " + e.getMessage());
                } catch (GroovyBugError e) {
                    System.out.println("The function the probably not allowed!" + e.getMessage());
                } catch (Exception e) {
                    System.out.println("<" + script.toString() + "> couldn't run lowest script in File due to " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

    }


    public static void runFunctionAll(String name, Object... args) {
        ExtractedMethod extractedMethod = new ExtractedMethod(name, args.length);

        if (functionKnower.containsKey(extractedMethod)) {

            for (Script script : functionKnower.get(extractedMethod)) {
                try {
                    LaunchWrapper.invokeMethod(script, name, args);

                } catch (MultipleCompilationErrorsException e) {
                    System.out.println("Successful sandboxing: " + e.getMessage());
                } catch (GroovyBugError e) {
                    System.out.println(script.toString() + " couldn't run function [" + name + "] due to \n" + e.getMessage());
                } catch (Exception e) {
                    System.out.println(script.toString() + " couldn't run function [" + name + "] due to \n" + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            // System.out.println("No script has the function [" + name + "]")
        }
    }

    private static class ExtractedMethod {
        String methodName;
        int argumentCount;

        ExtractedMethod(String methodName, int argumentCount) {
            this.argumentCount = argumentCount;
            this.methodName = methodName;
        }

        @Override
        public String toString() { return methodName + "<" + argumentCount + ">"; }

        @Override
        public int hashCode() { return Objects.hash(methodName, argumentCount); }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ExtractedMethod && (((ExtractedMethod) obj).argumentCount == argumentCount && Objects.equals(((ExtractedMethod) obj).methodName, methodName));
        }
    }
}