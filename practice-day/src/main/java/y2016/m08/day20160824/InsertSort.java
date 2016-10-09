package y2016.m08.day20160824;

import java.util.Arrays;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-08-24 PM05:13
 */
public class InsertSort {
    public static int[] sort(int[] data) {
        if(data == null || data.length < 2) {

        }

        for (int i = 1; i < data.length; i++) {
            int temp = data[i];
            int position = i; // 记录当前元素位置，最终交换的位置
            for(int j = i - 1; j >= 0; j--) {
                if(data[j] > temp) {
                    data[j + 1] = data[j]; // 后移
                    position--;
                } else {
                    break; // 说明j之前都比temp小
                }
            }

            data[position] = temp;
        }
        return data;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sort(new int[]{10, 1, 19, 4, 2, 7, 3, 6, 12})));;
    }
}
