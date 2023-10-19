import java.util.concurrent.RecursiveAction;

public class ForkJoin extends RecursiveAction {
    private int[] array;
    private int left;
    private int right;
    private static String text = "";

    public static String getText() {
        return text;
    }

    public ForkJoin(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }


    @Override
    protected void compute() {
        if (left < right) {
            int middle = left + (right - left) / 2;

            ForkJoin leftTask = new ForkJoin(array, left, middle);
            ForkJoin rightTask = new ForkJoin(array, middle + 1, right);

            invokeAll(leftTask, rightTask);

            merge(array, left, middle, right);
        }
    }

    public static void merge(int[] array, int left, int middle, int right) {
        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        int[] Left = new int[leftSize];
        int[] Right = new int[rightSize];

        for (int i = 0; i < leftSize; ++i){
            Left[i] = array[left + i];
        }
        for (int j = 0; j < rightSize; ++j){
            Right[j] = array[middle + 1 + j];
        }

        int i = 0, j = 0;

        int k = left;
        while (i < leftSize && j < rightSize) {
            if (Left[i] <= Right[j]) {
                array[k] = Left[i];
                i++;
            }
            else {
                array[k] = Right[j];
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            array[k] = Left[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            array[k] = Right[j];
            j++;
            k++;
        }
    }


    public static void print(int[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = array.length;
        for (int i = 0; i < n; ++i){
            stringBuilder.append(array[i]).append(" ");
        }
        text = stringBuilder.toString();
    }
}
