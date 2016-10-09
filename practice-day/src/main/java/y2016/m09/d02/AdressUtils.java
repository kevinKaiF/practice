/**
 * @Title: AdressUtils.java
 * @Package cn.bidlink.pig.udf.util.ipconverse
 * @Description: TODO
 * @author liubaoxin
 * @date 2014-5-26 下午3:00:54
 * @version V1.0
 */
package y2016.m09.d02;


/**
 * @ClassName: AdressUtils
 * @Description: TODO
 * @author liubaoxin
 * @date 2014-5-26 下午3:00:54 
 *
 */
public class AdressUtils {

    //	/**
//	 * 从纯真IP地址库中提取出省份名词、国家名称
//	* @Title: partitionCity
//	* @Description: TODO
//	* @param @param ipFile
//	* @param @param cityFile
//	* @param @param countryFile
//	* @return void 
//	* @throws
//	 */
//	public static void partitionCity(String,String cityFile,String countryFile){
//		IpSeeker ipConverser=new IpSeeker("/pigShell/qqwry.dat","/pigShell");
//		try {
//		 
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
    public static String[] parseCountryCity(String country) {
        String[] arr = new String[3];
        arr[0] = "中国";
        int index = country.indexOf("省");
        String city = null;
        if (index > 0) { //判断省
            arr[1] = country.substring(0, index + 1);
            //判断市
            // city = country.substring(index+1);
            arr[2] = parseCity(country, index);

        } else if (country.startsWith("宁夏") || country.startsWith("西藏") || country.startsWith("内蒙古") || country.startsWith("新疆") || country.startsWith("广西")) {
            if (country.startsWith("内蒙古")) {
                index = 2;
            } else {
                index = 1;
            }
            arr[1] = country.substring(0, index + 1);
            //判断市
//			 city = country.substring(index+1);
            arr[2] = parseCity(country, index);
        } else if (country.indexOf("市") > 0) {
            arr[1] = country.substring(0, country.indexOf("市") + 1);
            arr[2] = arr[1];
        } else {
            //不满足以上条件，为国外IP
            arr[0] = country;
            arr[1] = "其他";
            arr[2] = "其他";
        }
        return arr;
    }

    /**
     * 提取所属市字符串
     * @Title: parseCity
     * @Description: TODO
     * @param @return
     * @return String
     * @throws
     */
    public static String parseCity(String city, int index) {
        index = index + 1;
        if (index >= city.length()) {
            return "其他";
        }
        city = city.substring(index);
        index = city.indexOf("市");
        if (index > 0) {
            city = city.substring(0, index + 1);
        } else if (city.indexOf("地区") > 0) {
            index = city.indexOf("地区");
            city = city.substring(0, index + 2);
        } else {
            city = "其他";
        }
        return city;
    }
}
