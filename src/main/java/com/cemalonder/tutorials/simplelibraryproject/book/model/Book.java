package com.cemalonder.tutorials.simplelibraryproject.book.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
public class Book {

  @Id @GeneratedValue private Long id;

  @Column @NotNull private String name;

  @Column @NotNull private String authorName;
}
