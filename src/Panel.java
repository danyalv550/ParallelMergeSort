import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;

public class Panel extends JPanel {
    public int width = 1400;
    public int height = 1000;
    JButton generate;
    JButton mergeSort;
    JButton forkJoin;
    JButton executorService;
    JButton clear;
    JLabel timerMerge;
    JLabel timerFork;
    JLabel timerExecutor;
    JTextArea original;
    JTextArea sorted;
    JScrollPane originalScrollPane;
    JScrollPane sortedScrollPane;
    JTextField number;
    Font buttonFont = new Font("Arial", Font.BOLD, 22);
    Color color = new Color(152, 152, 152);
    Generator generator = new Generator(this);
    ForkJoinPool pool = ForkJoinPool.commonPool();
    ExecutorService executor = Executors.newWorkStealingPool();


    public Panel(){
        setBackground(Color.DARK_GRAY);
        setSize(width, height);
        setLayout(null);

        //Buttons
        generate = new JButton("Generar");
        generate.setBounds(1180,30,150,80);
        generate.setFont(buttonFont);
        generate.addActionListener(e -> {
            // Código que se ejecutará cuando se haga clic en el botón
            try {
                generator.start();
                int arrayNumber = Integer.parseInt(number.getText());
                generator.setNumber(arrayNumber);
                generator.generate();
                //generator.print();
                original.setText(generator.getText());
            } catch (NumberFormatException ex) {
                // El valor en el campo no es un número entero válido
                JOptionPane.showMessageDialog(this, "El valor no es un número entero válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(generate);

        mergeSort = new JButton("Merge Sort");
        mergeSort.setBounds(30,740,300,50);
        mergeSort.setFont(buttonFont);
        mergeSort.addActionListener(e -> {
            // Código que se ejecutará cuando se haga clic en el botón
            int[] array = Arrays.copyOf(generator.getArray(), generator.getArray().length);
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int value : array) {
                arrayList.add(value);
            }
            MergeSort mergeSort = new MergeSort(arrayList);
            long startTime =  System.nanoTime();
            mergeSort.sort(0, array.length - 1);
            long endTime =  System.nanoTime();
            long time = (endTime - startTime)/1000;
            //MergeSort.print(arrayList);
            sorted.setText(MergeSort.getText());
            timerMerge.setText(time +" micro segundos");
        });
        add(mergeSort);

        forkJoin = new JButton("Fork Join");
        forkJoin.setBounds(430,740,300,50);
        forkJoin.setFont(buttonFont);
        forkJoin.addActionListener(e -> {
            // Código que se ejecutará cuando se haga clic en el botón
            int[] array = Arrays.copyOf(generator.getArray(), generator.getArray().length);
            //ForkJoin forkJoin = new ForkJoin(array, 0, array.length - 1);
            long startTime =  System.nanoTime();
            for (int i = 0; i < 1; i++){
                pool.invoke(new ForkJoin(array, 0, array.length - 1));
            }
            long endTime =  System.nanoTime();
            long time = (endTime - startTime)/1000;
            //ForkJoin.print(array);
            sorted.setText(ForkJoin.getText());
            timerFork.setText(time +" micro segundos");
        });
        add(forkJoin);

        executorService = new JButton("Executor Service");
        executorService.setBounds(830,740,300,50);
        executorService.setFont(buttonFont);
        executorService.addActionListener(e -> {
            int[] array = Arrays.copyOf(generator.getArray(), generator.getArray().length);
            ExecuteServ executeServ = new ExecuteServ(executor,array,0, array.length - 1,0);
            long startTime, endTime;
            Future<?> execute = executor.submit(executeServ);
            try {
                startTime =  System.nanoTime();
                execute.get();
                endTime =  System.nanoTime();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (ExecutionException ex) {
                throw new RuntimeException(ex);
            }
            long time = (endTime - startTime)/1000;
            executor.shutdown();
            //executeServ.print(array);
            sorted.setText(executeServ.getText());
            timerExecutor.setText(time +" micro segundos");
            executor = Executors.newWorkStealingPool();
        });
        add(executorService);

        clear = new JButton("Limpiar");
        clear.setBounds(1180,740,150,50);
        clear.setFont(buttonFont);
        clear.addActionListener(e -> {
            // Código que se ejecutará cuando se haga clic en el botón
            timerMerge.setText("00:00:00");
            timerFork.setText("00:00:00");
            timerExecutor.setText("00:00:00");

            number.setText("");

            original.setText("");
            sorted.setText("");
        });
        add(clear);
        //Labels
        timerMerge = new JLabel("00:00:00");
        timerMerge.setBounds(30,820,300,50);
        timerMerge.setFont(buttonFont);
        timerMerge.setBackground(color);
        timerMerge.setForeground(Color.WHITE);
        add(timerMerge);

        timerFork = new JLabel("00:00:00");
        timerFork.setBounds(430,820,300,50);
        timerFork.setFont(buttonFont);
        timerFork.setBackground(color);
        timerFork.setForeground(Color.WHITE);
        add(timerFork);

        timerExecutor = new JLabel("00:00:00");
        timerExecutor.setBounds(830,820,300,50);
        timerExecutor.setFont(buttonFont);
        timerExecutor.setBackground(color);
        timerExecutor.setForeground(Color.WHITE);
        add(timerExecutor);
        //TextArea & ScrollPane
        original = new JTextArea();
        original.setBounds(30,30,1100,320);
        //original.setFont(font);
        original.setBackground(color);
        original.setLineWrap(true);
        original.setWrapStyleWord(true);
        originalScrollPane = new JScrollPane(original);
        originalScrollPane.setBounds(30,30,1100,320);
        add(originalScrollPane);


        sorted = new JTextArea();
        sorted.setBounds(30,380,1300,320);
        //sorted.setFont(font);
        sorted.setBackground(color);
        sorted.setLineWrap(true);
        sorted.setWrapStyleWord(true);
        sortedScrollPane = new JScrollPane(sorted);
        sortedScrollPane.setBounds(30,380,1300,320);
        add(sortedScrollPane);
        //TextField
        number = new JTextField();
        number.setBounds(1180,140,150,80);
        number.setFont(buttonFont);
        number.setBackground(color);
        add(number);
    }
}
