import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class ExecuteServ implements Runnable {
    private int[] array;
    private int left,right;
    private String text = "";
    private int count;
    private int threshold = 3; // Umbral para clasificar secuencialmente
    private ExecutorService executor;
    public String getText() {
        return text;
    }

    public ExecuteServ(ExecutorService executor, int[] array, int left, int right, int count) {
        this.executor = executor;
        this.array = array;
        this.left = left;
        this.right = right;
        this.count = count;
    }

    @Override
    public void run() {
        int middle = (left + right) / 2;
        if(/*right - left > */count < threshold){
            Future<?> leftTask = executor.submit(new ExecuteServ(executor, array, left, middle, count + 1));
            Future<?> rightTask = executor.submit(new ExecuteServ(executor, array, middle + 1, right, count + 1));
            try {
                leftTask.get();
                rightTask.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }else{
            sort(left, middle);
            sort(middle + 1, right);
        }
        merge(left, middle, right - 1);
    }

    /*@Override
    public Void call() throws Exception {
        int middle = left + (right - left) / 2;
        if (left < right) {
            if (count > threshold) {
                sort(left, middle);
                sort(middle + 1, right);
            } else {
                //Future<?> leftFuture = executor.submit(() -> sort( left, middle));
                //Future<?> rightFuture = executor.submit(() ->sort( middle + 1, right));

                Collection<Callable<Void>> tasks = new ArrayList<>();
                tasks.add(new ExecuteServ(executor,array,left, middle,count+1));
                tasks.add(new ExecuteServ(executor,array,middle + 1, right,count+1));
                //tasks.add(() -> { sort(middle + 1, right); return null; });

                try {
                    executor.invokeAll(tasks);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                /*try {
                    leftFuture.get();
                    rightFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            merge(left,middle, right);
        }
        return null;
    }*/

    /*@Override
    public void run() {
        if (left < right) {
            if (right - left <= threshold) {
                sort(left, right);
            } else {
                int middle = left + (right - left) / 2;

                Future<?> leftTask = executor.submit(() -> sort( left, middle));
                Future<?> rightTask = executor.submit(() ->sort( middle + 1, right));

                try {
                    leftTask.get();
                    rightTask.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }

                merge(left, middle, right);
            }
        }
    }*/

    public void sort(int left, int right) {
        if (left < right) {

            int middle = left + (right - left) / 2;

            sort(left, middle);
            sort(middle + 1, right);

            merge(left, middle, right);
        }
    }

    public void merge(int left, int middle, int right)
    {
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

    public void print(int[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = array.length;
        for (int i = 0; i < n; ++i){
            stringBuilder.append(array[i]).append(" ");
        }
        text = stringBuilder.toString();
    }


    int getThreshold(int arraySize) {
        return arraySize / 3; // Modifica este cálculo según tu necesidad específica
    }
}
