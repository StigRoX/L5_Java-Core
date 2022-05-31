package core.directory;

public class Main {
    public static void main(String[] args) {
        String[] header = new String[]{"Col1","Col2","AnotherCol","Oh Hi Mark"};
        int rows = 9;

        AppData data = new AppData(header, rows);
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < header.length; j++){
                data.set(j, i, i * 4 + j); // Заполняем только что созданную структуру "случайными" значениями
            }
        }
        data.save("test1.csv"); // Сохраняем её в файл

        AppData loadedData = AppData.load("test1.csv"); // Читаем данные из этого файла в новую структуру
        loadedData.printOut(); // Выводим её в консоль
    }
}
