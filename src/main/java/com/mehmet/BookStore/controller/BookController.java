package com.mehmet.BookStore.controller;

import com.mehmet.BookStore.entity.Book;
import com.mehmet.BookStore.entity.MyBookList;
import com.mehmet.BookStore.service.BookService;
import com.mehmet.BookStore.service.MyBookListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private MyBookListService myBookListService;

    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/book_register")
    public String book_register(){
        return "book_register";
    }
    @PostMapping("/save")
    public String addBook(@ModelAttribute Book b){ //  Bu metot, HTTP isteği sırasında gönderilen verileri kullanarak bir Book nesnesi oluşturur. @ModelAttribute anotasyonu, HTTP isteği sırasında gelen verilerin Book sınıfının özelliklerine atanmasını sağlar.
        bookService.save(b); // bookService.save(b);: Bu satır, bookService adlı bir hizmet sınıfının save() metodunu çağırarak kitap nesnesini veritabanına kaydeder. bookService servisi, Book verilerini işlemek ve veritabanına kaydetmek için kullanılan bir hizmet sınıfını temsil eder.
        return "redirect:/available_books";
    }

    @GetMapping("/available_books") // Bu kod bloğu, bir HTTP GET isteği üzerine çağrıldığında çalışacak bir Controller metodunu temsil ediyor. Bu metodun işlevi, mevcut tüm kitapları (Book nesnelerini) almak ve "available_books" adlı bir görünüm (view) sayfasına bu kitapları iletmektir.
    public ModelAndView getAllBook(){
        List<Book>list= bookService.getAllBook(); //  Bu satır, bookService adlı bir hizmet sınıfının getAllBook() metodunu çağırarak tüm kitapları bir liste olarak alır.
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("available_books");
//        modelAndView.addObject("book", list);
        return new ModelAndView("available_books","book",list); //available_books" adlı bir görünüm (view) sayfasına tüm kitapları göndermek için kullanılır. new ModelAndView() yapısıyla bir ModelAndView nesnesi oluşturulur ve bu nesne, "available_books" adlı görünüm sayfasının adını ve "book" adında bir model nesnesini içerir. Model nesnesi, "book" adıyla "list" adlı List<Book> nesnesini taşır.
    }

    @RequestMapping("/deleteAvailableBook/{id}")
    public String deleteAvailableBook(@PathVariable("id") int id){
        bookService.deleteById(id);
        return "redirect:/available_books";
    }

    @RequestMapping("/seeBookDetails/{id}")
    public String seeBookDetails(@PathVariable("id") int id, Model model){
        Book book = bookService.getBookById(id);
        model.addAttribute("book",book);
        return "book_details";
    }

    @GetMapping("/my_books")
    public String getMyBooks(Model model){ // neden model sınıfını kullandık?

        List<MyBookList>list=myBookListService.getAllMyBooks();
        model.addAttribute("book",list);
        return "my_books";
    }

    @RequestMapping("/myList/{id}")
    public String getMyList(@PathVariable("id") int id){
        Book b = bookService.getBookById(id);
        MyBookList myBookList = new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
        myBookListService.saveMyBooks(myBookList);
        return "redirect:/my_books";
    }


    @RequestMapping("/edit_book/{id}")
    public String editBook(@PathVariable("id") int id, Model model){
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);

        return "edit_book";
    }

}
