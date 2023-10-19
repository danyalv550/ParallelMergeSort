import java.util.Arrays;
import java.util.Random;

public class Generator {
    private int number;
    private int[] array;
    private String text ="";
    private Panel panel;

    public Generator(Panel panel) {
        this.panel = panel;
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public void generate(){
        array = new int[number];
        for (int i = 0; i < number; i++){
            array[i] = (int)(Math.random() * 100 + 1);
        }
    }

    public void print(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < number; i++){
            stringBuilder.append(array[i]).append(" ");
        }
        text = stringBuilder.toString();
    }

    public void start() {
        int original = number;
        this.setNumber(100000); // Establece el número de elementos
        this.generate(); // Genera el array
        for (int i = 0; i < 50 ; i++){
            int[] array = Arrays.copyOf(this.getArray(), this.getArray().length);
            panel.pool.invoke(new ForkJoin(array, 0, array.length - 1));
        }
        this.setNumber(original);
    }
}
