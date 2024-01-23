import image.Converter;
import image.TextGraphicsConverter;

public class Main {
    public static void main(String[] args) throws Exception {

        //TextGraphicsConverter converter = null; // Создайте тут объект вашего класса конвертера
        TextGraphicsConverter converter = new Converter();
        converter.setMaxRatio(10);
        converter.setMaxWidth(222);
        converter.setMaxHeight(224);

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
        // String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
//        String url = "https://eda.ru/img/eda/c464x302/s1.eda.ru/StaticContent/Photos/120304025019/120328131655/p_O.jpg";
//        String imgTxt = converter.convert(url);
//        System.out.println(imgTxt);
    }
}
