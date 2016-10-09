package y2016.m08.day20160824;

/**
 * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
 * @version : Ver 1.0
 * @date : 2016-08-24 PM05:15
 */
public class QuickSort {
    public static int[] quick_sort(int data[], int left, int right) {
        if(data == null || data.length == 0) {
            return data;
        }

        if (left < right) {
            int i = left, j = right, temp = data[left];
            while (i < j)
            {
                while(i < j && data[j] >= temp) { // 从右向左找第一个小于temp的数
                    j--;
                }

                if(i < j) {
                    data[i++] = data[j];
                }

                while(i < j && data[i] < temp) { // 从左向右找第一个大于等于temp的数
                    i++;
                }
                if(i < j) {
                    data[j--] = data[i];
                }
            }

            data[i] = temp;
            quick_sort(data, left, i - 1); // 递归调用
            quick_sort(data, i + 1, right);
        }
        return data;
    }

}
