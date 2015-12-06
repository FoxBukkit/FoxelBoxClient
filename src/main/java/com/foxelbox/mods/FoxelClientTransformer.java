package com.foxelbox.mods;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class FoxelClientTransformer implements IClassTransformer {
    public static final String CLASS_NAME_NetHandlerPlayClient = "net.minecraft.client.network.NetHandlerPlayClient";

    public static final String CLASS_NAME_R_PlayerControllerMP = PlayerControllerMP.class.getName().replace('.', '/');
    public static final String CLASS_NAME_D_PlayerControllerMP = "net/minecraft/client/multiplayer/PlayerControllerMP";

    public static final String CLASS_NAME_PCFB = PlayerControllerFB.class.getName().replace('.', '/');

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if(name.equals(CLASS_NAME_NetHandlerPlayClient) || transformedName.equals(CLASS_NAME_NetHandlerPlayClient)) {
            ClassReader classReader = new ClassReader(bytes);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassVisitorReplaceCall replacePlayerMethodMPCtor = new ClassVisitorReplaceCall(classWriter);
            classReader.accept(replacePlayerMethodMPCtor, 0);
            return classWriter.toByteArray();
        }
        return bytes;
    }

    private static class ClassVisitorReplaceCall extends ClassVisitor {
        public ClassVisitorReplaceCall(ClassVisitor classVisitor) {
            super(Opcodes.ASM5, classVisitor);
        }

        private class MethodCallReplacerVisitor extends MethodVisitor {
            public MethodCallReplacerVisitor(int api, MethodVisitor methodVisitor) {
                super(api, methodVisitor);
            }

            private boolean checkMethodInsn(int opcode, String clazz, String name, String desc, boolean itf) {
                if(opcode != Opcodes.INVOKESPECIAL) {
                    return true;
                }
                if(!clazz.equals(CLASS_NAME_R_PlayerControllerMP) && !clazz.equals(CLASS_NAME_D_PlayerControllerMP)) {
                    return true;
                }
                if(!name.equals("<init>")) {
                    return true;
                }
                super.visitMethodInsn(opcode, CLASS_NAME_PCFB, name, desc, itf);
                return false;
            }

            private boolean checkTypeInsn(int opcode, String clazz) {
                if(opcode != Opcodes.NEW) {
                    return true;
                }
                if(!clazz.equals(CLASS_NAME_R_PlayerControllerMP) && !clazz.equals(CLASS_NAME_D_PlayerControllerMP)) {
                    return true;
                }

                System.out.println("Patching new PlayerControllerMP call: " + clazz + " => " + CLASS_NAME_PCFB);
                super.visitTypeInsn(opcode, CLASS_NAME_PCFB);
                return false;
            }

            @Override
            public void visitMethodInsn(int opcode, String type, String name, String desc) {
                if (checkMethodInsn(opcode, type, name, desc, false)) {
                    super.visitMethodInsn(opcode, type, name, desc);
                }
            }

            @Override
            public void visitMethodInsn(int opcode, String type, String name, String desc, boolean itf) {
                if (checkMethodInsn(opcode, type, name, desc, itf)) {
                    super.visitMethodInsn(opcode, type, name, desc, itf);
                }
            }

            @Override
            public void visitTypeInsn(int opcode, String type) {
                if (checkTypeInsn(opcode, type)) {
                    super.visitTypeInsn(opcode, type);
                }
            }
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new MethodCallReplacerVisitor(api, super.visitMethod(access, name, desc, signature, exceptions));
        }
    }
}
