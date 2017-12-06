package y2017.m11.d17;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/11/17
 */
public class ValidateUtil {
    private static final List<Field> notNullKeyList;
    private static final List<Field> nullKeyList;

    static {
        List<Field> notNullFields = new ArrayList<>();
        List<Field> nullFields = new ArrayList<>();
        Field[] declaredFields = CompanyInfo.class.getDeclaredFields();
        for (Field field : declaredFields) {
            NotNull notNullAnnotation = field.getAnnotation(NotNull.class);
            Null nullAnnotation = field.getAnnotation(Null.class);
            if (notNullAnnotation != null) {
                notNullFields.add(field);
            }

            if (nullAnnotation != null) {
                nullFields.add(field);
            }
        }

        notNullKeyList = Collections.unmodifiableList(notNullFields);
        nullKeyList = Collections.unmodifiableList(nullFields);
    }

    public static int validate(CompanyInfo companyInfo) {
        if (companyInfo == null) {
            return 0;
        } else {
            for (Field field : notNullKeyList) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(companyInfo);
                    if (value == null) {
                        return 20;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
            }

            int notNullCount = 0;
            for (Field field : nullKeyList) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(companyInfo);
                    if (value != null) {
                        notNullCount++;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } finally {
                    field.setAccessible(false);
                }
            }

            if (notNullCount == 0) {
                return 80;
            } else if (notNullCount < 3) {
                return 85;
            } else if (notNullCount < 5) {
                return 95;
            } else {
                return 100;
            }
        }
    }
}
