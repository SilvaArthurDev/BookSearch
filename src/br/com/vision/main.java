package br.com.vision;
import br.com.model.Book;
import java.io.IOException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o título do livro para realizar a busca: ");
        String title = input.nextLine();

        Book book = new Book();

        book.getBookInfo(title);
    }
}
