package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;


public class Converter implements TextGraphicsConverter {
    public int maxWidth;
    public int maxHeight;
    public double maxRatio;
    Schema schema = new Schema();

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {

        double ratio = 0;
        int newWidth = 0;
        int newHeight = 0;

        String str1 = "";

        BufferedImage img = ImageIO.read(new URL(url));

        if (img.getWidth() > maxWidth || img.getHeight() > maxHeight) {

            if (img.getWidth() > img.getHeight()) {

                ratio = (double) img.getWidth() / img.getHeight();

                if (ratio > maxRatio) {
                    throw new BadImageSizeException(ratio, maxRatio);
                }
                int valueHeight = (int) (maxWidth / ratio);

                newWidth = maxWidth;
                newHeight = valueHeight;

            } else if (img.getHeight() > img.getWidth()) {

                ratio = (double) img.getHeight() / img.getWidth();

                if (ratio > maxRatio) {
                    throw new BadImageSizeException(ratio, maxRatio);
                }

                int valueWidth = (int) (maxHeight / ratio);

                newWidth = valueWidth;
                newHeight = maxHeight;
            }

        } else {
            newWidth = img.getWidth();
            newHeight = img.getHeight();
        }

        // Теперь нам нужно попросить картинку изменить свои размеры на новые.
        // Последний параметр означает, что мы просим картинку плавно сузиться
        // на новые размеры. В результате мы получаем ссылку на новую картинку, которая
        // представляет собой суженную старую.
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        // Теперь сделаем её чёрно-белой. Для этого поступим так:
        // Создадим новую пустую картинку нужных размеров, заранее указав последним
        // параметром чёрно-белую цветовую палитру:
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        // Попросим у этой картинки инструмент для рисования на ней:
        Graphics2D graphics = bwImg.createGraphics();
        // А этому инструменту скажем, чтобы он скопировал содержимое из нашей суженной картинки:
        graphics.drawImage(scaledImage, 0, 0, null);

        WritableRaster bwRaster = bwImg.getRaster();

        // Он хорош тем, что у него мы можем спросить пиксель на нужных
        // нам координатах, указав номер столбца (w) и строки (h)
        // int color = bwRaster.getPixel(w, h, new int[3])[0];
        // Выглядит странно? Согласен. Сам возвращаемый методом пиксель — это
        // массив из трёх интов, обычно это интенсивность красного, зелёного и синего.
        // Но у нашей чёрно-белой картинки цветов нет, и нас интересует
        // только первое значение в массиве. Ещё мы параметром передаём интовый массив на три ячейки.
        // Дело в том, что этот метод не хочет создавать его сам и просит
        // вас сделать это, а сам метод лишь заполнит его и вернёт.
        // Потому что создавать массивы каждый раз слишком медленно. Вы можете создать
        // массив один раз, сохранить в переменную и передавать один
        // и тот же массив в метод, ускорив тем самым программу.

        // Вам осталось пробежаться двойным циклом по всем столбцам (ширина)
        // и строкам (высота) изображения, на каждой внутренней итерации
        // получить степень белого пикселя (int color выше) и по ней
        // получить соответствующий символ c. Логикой превращения цвета
        // в символ будет заниматься другой объект, который мы рассмотрим ниже

        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                //??? //запоминаем символ c, например, в двумерном массиве или как-то ещё на ваше усмотрение
                str1 += c;
                str1 += c;
            }
            str1 += "\n";
        }
        // Осталось собрать все символы в один большой текст.
        // Для того, чтобы изображение не было слишком узким, рекомендую
        // каждый пиксель превращать в два повторяющихся символа, полученных
        // от схемы.
        return str1;
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
    }
}
