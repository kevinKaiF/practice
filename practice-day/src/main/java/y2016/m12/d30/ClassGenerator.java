package y2016.m12.d30;

import javassist.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/12/30
 */
public class ClassGenerator {
    interface DC {
    } // dynamic class tag interface

    private static final AtomicLong CLASS_NAME_COUNTER = new AtomicLong(0);

    private static final String SIMPLE_NAME_TAG = "<init>";

    private static final Map<ClassLoader, ClassPool> POOL_MAP = new ConcurrentHashMap<>();

    private ClassPool mPool;

    private ClassGenerator(ClassPool classPool) {
        this.mPool = classPool;
    }

    private Set<String> mInterfaces;

    private List<String> mFields;

    private List<String> mConstructors;

    private List<String> mMethods;

    private Map<String, Constructor<?>> mCopyConstructors;

    private Map<String, Method> mCopyMethods;

    private boolean mDefaultConstructor;

    private String mClassName;

    private String mSuperClass;

    private CtClass mCtc;

    public static ClassGenerator newInstance() {
        return new ClassGenerator(getClassPool(Thread.currentThread().getContextClassLoader()));
    }

    public static ClassGenerator newInstance(ClassLoader classLoader) {
        return new ClassGenerator(getClassPool(classLoader));
    }

    private static ClassPool getClassPool(ClassLoader classLoader) {
        if (classLoader == null) {
            return ClassPool.getDefault();
        }

        ClassPool classPool = POOL_MAP.get(classLoader);
        if (classPool == null) {
            classPool = new ClassPool(true);
            classPool.appendClassPath(new LoaderClassPath(classLoader));
            POOL_MAP.put(classLoader, classPool);
        }

        return classPool;
    }

    public ClassGenerator addInterface(Class<?> cl) {
        return addInterface(cl.getName());
    }

    private ClassGenerator addInterface(String name) {
        if (mInterfaces == null) {
            mInterfaces = new HashSet<>();
        }

        mInterfaces.add(name);
        return this;
    }

    public ClassGenerator addConstructor(Constructor<?> c) {
        String desc = ReflectUtils.getDesc(c);
        addConstructor("desc:" + desc);

        if (mCopyConstructors == null) {
            mCopyConstructors = new ConcurrentHashMap<>(4);
        }
        mCopyConstructors.put(desc, c);
        return this;
    }

    private ClassGenerator addConstructor(String constructorStr) {
        if (mConstructors == null) {
            mConstructors = new LinkedList<>();
        }

        mConstructors.add(constructorStr);
        return this;
    }


    public ClassGenerator addMethod(String name, int modifiers, Class<?> returnType, Class<?>[] parameterTypes, Class<?>[] exceptionTypes, String body) {
        StringBuilder sb = new StringBuilder();
        // append method name
        sb.append(modifier(modifiers))
                .append(' ')
                .append(ReflectUtils.getName(returnType))
                .append(' ')
                .append(name);

        // append method parameters
        sb.append("(");
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(ReflectUtils.getName(parameterTypes[i]));
            sb.append(" arg").append(i);
        }
        sb.append(")");

        // append method exception
        if (exceptionTypes != null && exceptionTypes.length > 0) {
            sb.append(" throws ");
            for (int i = 0; i < exceptionTypes.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(ReflectUtils.getName(exceptionTypes[i]));
            }
        }

        // append method body
        sb.append("{").append(body).append("}");
        return addMethod(sb.toString());
    }

    private String modifier(int modifiers) {
        if (java.lang.reflect.Modifier.isPublic(modifiers)) {
            return "public";
        }

        if (java.lang.reflect.Modifier.isPrivate(modifiers)) {
            return "private";
        }

        if (java.lang.reflect.Modifier.isProtected(modifiers)) {
            return "protected";
        }
        return "";
    }

    public ClassGenerator setClassName(String name) {
        mClassName = name;
        return this;
    }

    public ClassGenerator addField(String s) {
        if (mFields == null) {
            mFields = new ArrayList<>();
        }

        mFields.add(s);
        return this;
    }

    public ClassGenerator addConstructor(int modifiers, Class<?>[] parameterTypes, Class<?>[] exceptionTypes, String constructorBody) {
        StringBuilder sb = new StringBuilder();
        // append constructor name
        sb.append(modifier(modifiers))
                .append(' ')
                .append(SIMPLE_NAME_TAG);

        // append constructor parameters
        sb.append("(");
        if (parameterTypes != null) {
            for (int i = 0; i < parameterTypes.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(ReflectUtils.getName(parameterTypes[i]));
            }
        }
        sb.append(")");

        // append
        if (exceptionTypes != null && exceptionTypes.length > 0) {
            sb.append(" throws ");
            for (int i = 0; i < exceptionTypes.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(ReflectUtils.getName(exceptionTypes[i]));
            }
        }

        // append constructor body
        sb.append("{").append(constructorBody).append("}");
        return addConstructor(sb.toString());
    }

    public ClassGenerator addDefaultConstructor() {
        mDefaultConstructor = true;
        return this;
    }

    public Class<?> toClass() {
        return toClass(getClass().getClassLoader(), getClass().getProtectionDomain());
    }

    private Class<?> toClass(ClassLoader classLoader, ProtectionDomain protectionDomain) {
        if (mCtc != null) {
            mCtc.detach();
        }

        long id = CLASS_NAME_COUNTER.getAndIncrement();
        try {
            CtClass ctClass = mSuperClass == null ? null : mPool.get(mSuperClass);
            if (mClassName == null) {
                mClassName = (mSuperClass == null || javassist.Modifier.isPublic(ctClass.getModifiers()) ? ClassGenerator.class.getName() : mSuperClass + "$sc") + id;
            }

            mCtc = mPool.makeClass(mClassName);
            if (mSuperClass != null) {
                mCtc.setSuperclass(ctClass);
            }
            mCtc.addInterface(mPool.get(DC.class.getName()));

            if (mInterfaces != null) {
                for (String mInterface : mInterfaces) {
                    mCtc.addInterface(mPool.get(mInterface));
                }
            }

            if (mFields != null) {
                for (String mField : mFields) {
                    mCtc.addField(CtField.make(mField, mCtc));
                }
            }

            if (mMethods != null) {
                for (String mMethod : mMethods) {
                    if (mMethod.charAt(0) == ':') {
                        mCtc.addMethod(CtNewMethod.copy(getCtMethod(mCopyMethods.get(mMethod.substring(1))), mMethod.substring(1, mMethod.indexOf("(")), mCtc, null));
                    } else {
                        mCtc.addMethod(CtMethod.make(mMethod, mCtc));
                    }
                }
            }

            if (mDefaultConstructor) {
                mCtc.addConstructor(CtNewConstructor.defaultConstructor(mCtc));
            }

            if (mConstructors != null) {
                for (String mConstructor : mConstructors) {
                    if (mConstructor.charAt(0) == ':') {
                        mCtc.addConstructor(CtNewConstructor.copy(getCtConstructor(mCopyConstructors.get(mConstructor.substring(1))), mCtc, null));
                    } else {
                        String[] classNames = mCtc.getSimpleName().split("\\$+"); // inner class
                        mCtc.addConstructor(CtNewConstructor.make(mConstructor.replaceFirst(SIMPLE_NAME_TAG, classNames[classNames.length - 1]), mCtc));
                    }
                }
            }

            return mCtc.toClass(classLoader, protectionDomain);
        } catch (RuntimeException e) {
            throw e;
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (CannotCompileException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private CtConstructor getCtConstructor(Constructor<?> constructor) throws NotFoundException {
        return getCtClass(constructor.getDeclaringClass()).getConstructor(ReflectUtils.getDesc(constructor));
    }


    private CtMethod getCtMethod(Method method) throws NotFoundException {
        return getCtClass(method.getDeclaringClass()).getMethod(method.getName(), ReflectUtils.getDesc(method));
    }

    private CtClass getCtClass(Class<?> clazz) throws NotFoundException {
        return mPool.get(clazz.getName());
    }


    public ClassGenerator setSuperClass(Class<?> clazz) {
        mSuperClass = clazz.getName();
        return this;
    }

    public ClassGenerator setSuperClass(String superClass) {
        mSuperClass = superClass;
        return this;
    }

    public ClassGenerator addMethod(String methodStr) {
        if (mMethods == null) {
            mMethods = new ArrayList<>();
        }

        mMethods.add(methodStr);
        return this;
    }

    public ClassGenerator addMethod(String name, Method method) {
        String desc = name + ReflectUtils.getDesc(method);
        addMethod("desc:" + desc);

        if (mCopyMethods == null) {
            mCopyMethods = new ConcurrentHashMap<>(8);
        }

        mCopyMethods.put(desc, method);
        return this;
    }

    public void release() {
        if (mCtc != null) {
            mCtc.detach();
        }

        if (mInterfaces != null) {
            mInterfaces.clear();
        }

        if (mFields != null) {
            mFields.clear();
        }

        if (mMethods != null) {
            mMethods.clear();
        }

        if (mConstructors != null) {
            mConstructors.clear();
        }

        if (mCopyConstructors != null) {
            mCopyConstructors.clear();
        }

        if (mCopyMethods != null) {
            mCopyMethods.clear();
        }
    }
}
