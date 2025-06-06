package br.com.model;

import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private List<String> authors;
    private String description;
    private String thumbnail;

    public void getBookInfo(String title) throws IOException, InterruptedException {


        Gson gson = new Gson();
        List<Book> items = searchBooks(getURL(title));
        List<String> jsonObjects = new ArrayList<>();

       /*for (int i = 0; i < items.size(); i++) {
            jsonObjects.add(gson.toJson(items.get(i)));
        }
        for (int i = 0; i < jsonObjects.size(); i++) {
            System.out.println(jsonObjects.get(i));
        }*/

    }
    private String getURL(String title) throws IOException{

        title = URLEncoder.encode(title,"UTF-8");
        String key = "AIzaSyBBjXhrRerzqa0LzF2HvZ0ue0nYUKEYAVc";
        String url = "https://www.googleapis.com/books/v1/volumes?q="+title+"&key="+key;

        return url;
    }

    private List<Book> searchBooks(String url) throws IOException, InterruptedException{

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray items = jsonObject.getAsJsonArray("items");
        List<Book> bookList = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            Gson gson = new Gson();
            BookRecord bookRecord = gson.fromJson(items.get(i).getAsJsonObject(), BookRecord.class);
            Book book = bookRecord.book();

            JsonObject volumeInfo = items.get(i).getAsJsonObject().getAsJsonObject("volumeInfo");
            JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
            if(imageLinks != null) book.thumbnail = imageLinks.get("thumbnail").getAsString();
            bookList.add(verifyBook(book));
            System.out.println(book);
        }
        return bookList;
    }

    private Book verifyBook(Book book){

        List<String> author = new ArrayList<>();
        author.add("no author");
        if(book.title == null) book.title = "no title";
        if(book.authors == null) book.authors = author;
        if(book.description == null) book.description = "no description";
        if(book.thumbnail == null) book.thumbnail = "no image";
        return book;
    }

    @Override
    public String toString(){
        String info = "";
        System.out.println("Título: "+title);
        System.out.println("Autor(es): "+authors+
                    "\nDescrição: "+description);
        System.out.println("Thumbnail link: "+thumbnail);
        return info;
    }
}