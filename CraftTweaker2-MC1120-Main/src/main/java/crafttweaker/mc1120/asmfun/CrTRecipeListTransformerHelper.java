package crafttweaker.mc1120.asmfun;


import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;

/* http://www.programering.com/a/MzMwgzMwATM.html */
public class CrTRecipeListTransformerHelper {
    
    public static byte[] transform (String name, String transformedName, byte[] classBytes) {
        final ClassNode clazz = ASMUtils.createClassFromByteArray(classBytes);
    
        System.out.println("Transforming clazz: " + name);
        /*ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw){};
        cr.accept(cv, Opcodes.ASM5);*/
        
        MethodVisitor mw = clazz.visitMethod(Opcodes.ACC_PUBLIC, "equals", "(Ljava/lang/Object;)Z", null, null);
    
        /*
        // pushes the 'out' field (of type PrintStream) of the System class
        mw.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
        // pushes the "Hello World!" String constant
        mw.visitLdcInsn("this is equals method print!");
        // invokes the 'println' method (defined in the PrintStream class)
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V", false);*/
        mw.visitInsn(Opcodes.ICONST_1);
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(1, 2);
        mw.visitEnd();


        MethodVisitor mw2 = clazz.visitMethod(Opcodes.ACC_PUBLIC, "test", "(Ljava/lang/Object;)V", null, null);
    
        /*
        // pushes the 'out' field (of type PrintStream) of the System class
        mw.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
        // pushes the "Hello World!" String constant
        mw.visitLdcInsn("this is equals method print!");
        // invokes the 'println' method (defined in the PrintStream class)
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V", false);*/
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(1, 1);
        mw.visitEnd();
    
        /*for(MethodNode method : cw.methods) {
            System.out.println("method = " + method.name);
        }*/
    
    
        byte[] code = ASMUtils.createByteArrayFromClass(clazz, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        OutputStream fos;
        try {
            fos = new FileOutputStream("D:\\Users\\jonas\\Documents\\GitHub\\CraftTweaker\\CraftTweaker2-MC1120-Main\\src\\main\\java\\crafttweaker\\mc1120\\asmfun\\ExportedClass.class");
            fos.write(code);
            fos.close();
    
        } catch(IOException e) {
            e.printStackTrace();
        }
    
        return code;
        
        // return
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
