package com.mehmet.BookStore.service;

import com.mehmet.BookStore.entity.Book;
import com.mehmet.BookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    public void save(Book b) {
        bookRepository.save(b);
    }

    public List<Book> getAllBook(){ //Bu kod bloğu, bookRepository aracılığıyla tüm kitapları (Book nesnelerini) veritabanından çeken bir hizmet (service) metodunu temsil ediyor. Bu metodun işlevi, mevcut tüm kitapları bir liste olarak almak ve bu listeyi döndürmektir.

        return bookRepository.findAll(); // return bookRepository.findAll();: Bu satır, bookRepository adlı bir veritabanı erişim arayüzünü kullanarak tüm kitapları veritabanından alır. findAll() metodu, veritabanındaki tüm kayıtları getirir ve bu örnekte tüm kitapları içeren bir liste olarak döndürür.
    }

    public Book getBookById(int id) {

        return bookRepository.findById(id).get();
    }
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }
}
