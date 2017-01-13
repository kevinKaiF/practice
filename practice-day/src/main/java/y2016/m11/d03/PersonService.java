package y2016.m11.d03;

import y2016.m09.d14.Person;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2016/11/3
 */
public interface PersonService {

    Person findByCondition(Person condition, Long companyId);

}
