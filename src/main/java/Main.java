import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void getInfoFromPage(String url, FileWriter fileWriter) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements allInfo = doc.select(".search-item");
        for (Element element : allInfo) {
            Element href = element.select("a").get(0);
            fileWriter.append("https://komission.vtb.ru" + href.attr("href") + "\n");
            fileWriter.append(element.text() + ";\n\n");
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File("info.html");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        //получение количества страниц
        Document doc = Jsoup.connect("https://komission.vtb.ru/search?type=residential&subType=all&page=1").get();
        Elements counts = doc.select(".el-pager");
        Element count = counts.select(".number").last();
        String pages = count.text();
        //System.out.println(pages);


        for (int i = 1; i <= Integer.parseInt(pages); i++) {
            String url = "https://komission.vtb.ru/search?type=residential&subType=all&page=" + i;
            getInfoFromPage(url, fileWriter);
        }
        fileWriter.flush();
        fileWriter.close();
    }


}
