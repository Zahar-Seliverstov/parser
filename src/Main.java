import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://www.hse.ru/education/magister/").get();
            Elements magisterPrograms = doc.select(".edu-programm__magister .b-row.edu-programm__item");
            var specialties = magisterPrograms.stream()
                    .map(program -> program.selectFirst(".link").ownText())
                    .collect(Collectors.toList());

            // Записываем названия специальностей в файл
            writeToFile(specialties, "specialtiesName.txt");

            System.out.println("Названия специальностей сохранены в файл specialtiesName.txt");
        } catch (IOException e) {
            // В случае ошибки выводим сообщение об ошибке
            System.err.println("Ошибка при загрузке страницы или записи в файл: " + e.getMessage());
        }
    }

    // Метод для записи списка строк в файл
    private static void writeToFile(Iterable<String> lines, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            lines.forEach(line -> {
                try {
                    writer.write(line + "\n");
                } catch (IOException e) {
                    throw new RuntimeException("Ошибка при записи в файл!", e);
                }
            });
        }
    }
}
