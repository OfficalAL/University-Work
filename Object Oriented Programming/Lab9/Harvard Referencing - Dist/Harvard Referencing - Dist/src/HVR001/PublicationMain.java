package HVR001;

import java.util.ArrayList;
import java.util.List;

public class PublicationMain {
    public static void main(String[] args) {
        // Example usage and testing
        List<Author> authors = new ArrayList<>();
        authors.add(new IndividualAuthor("Knuth", "D."));
        authors.add(new InstitutionalAuthor("Oracle"));

        Book book = new Book(authors, "The Art of Computer Programming", 1968, "Addison-Wesley");
        JournalArticle article = new JournalArticle(authors, "A Journal Article", 2020, "Journal of Testing", 10, 2, 100, 110);
        WebPage webpage = new WebPage(authors, "A Web Page", 2021, "https://example.com", "19 Nov 2025");

        System.out.println(book.harvardReference());
        System.out.println(article.harvardReference());
        System.out.println(webpage.harvardReference());
    }
}
