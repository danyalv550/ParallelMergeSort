import java.util.ArrayList;

public class MergeSort {
    private ArrayList<Integer> array;
    private static String text = "";

    public static String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MergeSort(ArrayList<Integer> array) {
        this.array = array;
    }

    public void merge(int left, int middle, int right) {
        ArrayList<Integer> leftList = new ArrayList<>(array.subList(left, middle + 1));
        ArrayList<Integer> rightList = new ArrayList<>(array.subList(middle + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i) <= rightList.get(j)) {
                array.set(k, leftList.get(i));
                i++;
            } else {
                array.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < leftList.size()) {
            array.set(k, leftList.get(i));
            i++;
            k++;
        }

        while (j < rightList.size()) {
            array.set(k, rightList.get(j));
            j++;
            k++;
        }
    }

    public void sort(int left, int right) {
        if (left < right) {
            int middle = left + (right - left) / 2;
            sort(left, middle);
            sort(middle + 1, right);
            merge(left, middle, right);
        }
    }

    public static void print(ArrayList<Integer> array) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = array.size();
        for (int i = 0; i < n; ++i){
            stringBuilder.append(array.get(i)).append(" ");
        }
        text = stringBuilder.toString();
    }
}
