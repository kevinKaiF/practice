package y2016.m07.day20160705;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

import javax.validation.*;
import javax.validation.bootstrap.GenericBootstrap;
import javax.validation.metadata.ConstraintDescriptor;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-07-05 PM02:41
 */
public class ValidatorUtils {
    public static Validator validator = null;

    public ValidatorUtils() {
    }

    public static Configuration<?> buildValidatorConfig(ValidationProviderResolver resolver) {
        GenericBootstrap bootStrap = Validation.byDefaultProvider();
        return resolver != null?bootStrap.providerResolver(resolver).configure():bootStrap.configure();
    }

    public static ValidatorFactory buildValidatorFactory(ValidationProviderResolver resolver, MessageInterpolator messageInterpolator) {
        Configuration config = buildValidatorConfig(resolver);
        return messageInterpolator != null?config.messageInterpolator(messageInterpolator).buildValidatorFactory():config.buildValidatorFactory();
    }

    public static Validator getValidator(ValidationProviderResolver resolver, MessageInterpolator messageInterpolator) {
        return buildValidatorFactory(resolver, messageInterpolator).getValidator();
    }

    public static Validator getDefaultValidator() {
        if(validator == null) {
            Class var0 = ValidatorUtils.class;
            synchronized(ValidatorUtils.class) {
                if(validator == null) {
                    validator = getValidator((ValidationProviderResolver)null, (MessageInterpolator)null);
                }
            }
        }

        return validator;
    }

    public static Set<ConstraintViolation<Object>> validate(Object obj, Class<?>[] groups) {
        Object sets = null;
        if(obj == null) {
            sets = new HashSet();
            ((Set)sets).add(ConstraintViolationImpl.forBeanValidation("",null, "verify obj can\'t be null! ", Object.class, "", "", "", PathImpl.createPathFromString(""), (ConstraintDescriptor) null, (ElementType) null));
        }

        if(obj != null) {
            if(groups == null) {
                sets = getDefaultValidator().validate(obj, new Class[0]);
            } else {
                sets = getDefaultValidator().validate(obj, groups);
            }
        }

        return (Set)sets;
    }

    public static Set<ConstraintViolation<Object>> validateProperty(Object obj, String propertyName, Class<?>[] groups) {
        Object sets = null;
        if(obj == null) {
            sets = new HashSet();
            ((Set)sets).add(ConstraintViolationImpl.forBeanValidation("", null, "verify obj can\'t be null! ", Object.class, "", "", "", PathImpl.createPathFromString(""), (ConstraintDescriptor)null, (ElementType)null));
        }

        if(obj != null) {
            if(groups == null) {
                sets = getDefaultValidator().validateProperty(obj, propertyName, new Class[0]);
            } else {
                sets = getDefaultValidator().validateProperty(obj, propertyName, groups);
            }
        }

        return (Set)sets;
    }

    public static Set<ConstraintViolation<?>> validateValue(Class beanType, String propertyName, Object value, Class<?>[] groups) {
        Object sets = null;
        if(beanType == null) {
            sets = new HashSet();
            ((Set)sets).add(ConstraintViolationImpl.forBeanValidation("", null, "beanType  can\'t be null! ", Object.class, "", "", "", PathImpl.createPathFromString(""), (ConstraintDescriptor)null, (ElementType)null));
        }

        if(beanType != null) {
            if(groups == null) {
                sets = getDefaultValidator().validateValue(beanType, propertyName, value, new Class[0]);
            } else {
                sets = getDefaultValidator().validateValue(beanType, propertyName, value, groups);
            }
        }

        return (Set)sets;
    }

    public static Set<ConstraintViolation<?>> verifyValue(Class<?> beanType, String propertyName, Object value, Class<?>[] groups, boolean isThrow) {
        Set sets = validateValue(beanType, propertyName, value, groups);
        if(isThrow && sets != null && sets.size() > 0) {
            Iterator i$ = sets.iterator();
            if(i$.hasNext()) {
                ConstraintViolation cv = (ConstraintViolation)i$.next();
                throw new RuntimeException(cv.getPropertyPath() + "  " + cv.getMessage());
            }
        }

        return sets;
    }

    public static Set<ConstraintViolation<Object>> verifyProperty(Object obj, String propertyName, Class<?>[] groups, boolean isThrow) {
        Set sets = validateProperty(obj, propertyName, groups);
        if(isThrow && sets != null && sets.size() > 0) {
            Iterator i$ = sets.iterator();
            if(i$.hasNext()) {
                ConstraintViolation cv = (ConstraintViolation)i$.next();
                throw new RuntimeException(cv.getPropertyPath() + "  " + cv.getMessage());
            }
        }

        return sets;
    }

    public static Set<ConstraintViolation<Object>> verifyAllProperty(Object obj, String[] includeFields, String[] excludeFields, Class<?>[] groups, boolean isThrow) {
        HashSet sets = null;
        if(obj == null) {
            sets = new HashSet();
            sets.add(ConstraintViolationImpl.forBeanValidation("", null, "verify obj can\'t be null! ", Object.class, "", "", "", PathImpl.createPathFromString(""), (ConstraintDescriptor)null, (ElementType)null));
        }

        if(includeFields != null) {
            HashSet var13 = new HashSet();
            String[] var14 = includeFields;
            int var16 = includeFields.length;

            for(int var19 = 0; var19 < var16; ++var19) {
                String var21 = var14[var19];
                var13.addAll(validateProperty(obj, var21, groups));
            }

            if(isThrow && var13 != null && var13.size() > 0) {
                Iterator var15 = var13.iterator();
                if(var15.hasNext()) {
                    ConstraintViolation var17 = (ConstraintViolation)var15.next();
                    throw new RuntimeException(var17.getPropertyPath() + "  " + var17.getMessage());
                }
            }

            return var13;
        } else {
            Class cls = obj.getClass();
            Field[] fields = cls.getDeclaredFields();
            HashSet setsRes = new HashSet();
            if(fields != null) {
                Field[] i$ = fields;
                int cv = fields.length;

                for(int i$1 = 0; i$1 < cv; ++i$1) {
                    Field field = i$[i$1];
                    if(!"serialVersionUID".equalsIgnoreCase(field.getName()) && (excludeFields == null || !ArrayUtils.contains(excludeFields, field.getName()))) {
                        setsRes.addAll(validateProperty(obj, field.getName(), groups));
                    }
                }
            }

            if(isThrow && setsRes != null && setsRes.size() > 0) {
                Iterator var18 = setsRes.iterator();
                if(var18.hasNext()) {
                    ConstraintViolation var20 = (ConstraintViolation)var18.next();
                    throw new RuntimeException(var20.getPropertyPath() + "  " + var20.getMessage());
                }
            }

            return setsRes;
        }
    }

    public static Set<ConstraintViolation<Object>> verify(Object obj, Class<?>[] groups, boolean isThrow) {
        Set sets = validate(obj, groups);
        if(isThrow && sets != null && sets.size() > 0) {
            Iterator i$ = sets.iterator();
            if(i$.hasNext()) {
                ConstraintViolation cv = (ConstraintViolation)i$.next();
                throw new RuntimeException(cv.getPropertyPath() + "  " + cv.getMessage());
            }
        }

        return sets;
    }

    public static Set<ConstraintViolation<Object>> verifyAll(Object obj, Class<?>[] groups, boolean isAllThrow) {
        Set sets = validate(obj, groups);
        if(isAllThrow && sets != null && sets.size() > 0) {
            StringBuffer buf = new StringBuffer();
            buf.append("\r\n");
            Iterator i$ = sets.iterator();

            while(i$.hasNext()) {
                ConstraintViolation cv = (ConstraintViolation)i$.next();
                buf.append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append("\r\n");
            }

            throw new RuntimeException(buf.toString());
        } else {
            return sets;
        }
    }
}
