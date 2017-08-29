package y2017.m04.d17;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @description :
 * @date : 2017/4/17
 */
public  class DemoMethod implements Convert<Integer, String> {
    public void add(String... args) {

    }

    @Override
    public String convert(Integer integer) {
        return String.valueOf(integer);
    }
}
