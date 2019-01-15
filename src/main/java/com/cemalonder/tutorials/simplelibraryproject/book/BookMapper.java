package com.cemalonder.tutorials.simplelibraryproject.book;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;

import com.cemalonder.tutorials.simplelibraryproject.book.model.Book;
import com.cemalonder.tutorials.simplelibraryproject.book.model.BookDto;
import com.cemalonder.tutorials.simplelibraryproject.configuration.MapperConfig;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
  @Mappings({@Mapping(target = "bookId", source = "id")})
  BookDto map(Book book);

  @Mappings({
    @Mapping(target = "name", source = "name"),
    @Mapping(target = "authorName", source = "authorName")
  })
  Book map(BookDto bookDto);

  void assignToBook(BookDto bookDto, @MappingTarget Book book);

  @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
  List<BookDto> map(List<Book> books);
}
