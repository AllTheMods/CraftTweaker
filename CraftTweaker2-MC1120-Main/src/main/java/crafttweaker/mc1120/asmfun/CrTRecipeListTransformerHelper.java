package crafttweaker.mc1120.asmfun;


import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
/* http://www.programering.com/a/MzMwgzMwATM.html */
public class CrTRecipeListTransformerHelper {
    
    public static byte[] transform (String name, String transformedName, byte[] classBytes) {
        final ClassNode clazz = ASMUtils.createClassFromByteArray(classBytes);
    
        System.out.println("Transforming clazz: " + name);
    
        MethodVisitor mw = clazz.visitMethod(Opcodes.ACC_PUBLIC, "equals", "(Ljava/lang/Object;)Z", null, null);
    
    
        // pushes the 'out' field (of type PrintStream) of the System class
        mw.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
        // pushes the "Hello World!" String constant
        mw.visitLdcInsn("this is equals method print!");
        // invokes the 'println' method (defined in the PrintStream class)
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V", false);
        mw.visitInsn(Opcodes.ICONST_1);
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(0, 0);
        mw.visitEnd();
    
        for(MethodNode method : clazz.methods) {
            System.out.println("method = " + method.name);
        }
        
        return ASMUtils.createByteArrayFromClass(clazz, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
