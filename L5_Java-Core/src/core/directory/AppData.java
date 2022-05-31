package core.directory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AppData {
    private String[] header;
    private int[][] data;

    public AppData(String[] header, int rows) { // Инициализируем структуру только заголовком и числом столбцов
        this.header = header;
        this.data = new int[header.length][rows];
    }

    public void set(int column, int row, int value) { // Метод установки отдельного элемента
        this.data[column][row] = value;
    }
    public int get(int column, int row) { // Получаем отдельный элемент
        return this.data[column][row];
    }
    public void setRow(int index, int[] row) { // Устанавливаем строку целиком
        for(int i = 0; i < row.length; i++) {
            this.data[i][index] = row[index];
        }
    }

    public void save(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new
                FileWriter(filename))) {
            writer.write(String.join(";", this.header) + "\n"); // Выводим в файл заголовки, объединив их в одну строку и разделив точкой с запятой

            for(int row = 0; row < this.data[0].length; row++){
                String[] items = new String[this.header.length]; // Временный массив для строки элементов
                for(int col = 0; col < this.header.length; col++){
                    items[col] = Integer.toString(this.data[col][row]); // Добавляем в него элементы из каждого столбца конкретной строки
                }
                writer.write(String.join(";", items) + "\n"); // Также выводим, объединив в одну строку
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printOut() {
        System.out.println(String.join("; ", this.header)); // Всё то же самое, что для сохранения, но для вывода в консоль
        for(int row = 0; row < this.data[0].length; row++){
            String[] items = new String[this.header.length];
            for(int col = 0; col < this.header.length; col++){
                items[col] = Integer.toString(this.data[col][row]);
            }
            System.out.println(String.join("\t", items)); // Используем символ табуляции для чуть более красивого вывода строк
        }

    }

    public static AppData load(String filename) {
        List<String[]> items = new ArrayList<>(); // Создаем список списков элементов, в который будем писать вывод из файла
        AppData data;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) { // Читаем строки, пока не дошли до конца
                String[] values = line.split(";"); // Создаем массив строк, разделив строку файла на отдельные элементы по точке с запятой.
                items.add(values); // Добавляем его к списку
            }
            int columns = items.get(0).length;  // Считаем количество столбцов
            int rows = items.size() - 1;        // и строк
            String[] header = items.get(0);
            data = new AppData(header, rows);   // Инициализируем структуру
            for(int row = 1; row < items.size(); row++) { // Читаем список строк с первой строки, а не с нулевой, так как на нулевой у нас заголовок.
                for(int col = 0; col < columns; col++) {
                    String item = items.get(row)[col]; // Получаем отдельный элемент из списка
                    data.set(col, row-1, Integer.parseInt(item)); // Преобразуем в число и помещаем в структуру
                }
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
