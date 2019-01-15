package com.cemalonder.tutorials.simplelibraryproject.book.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class BookDto {

  @ApiModelProperty(required = false, hidden = true)
  private Long bookId;

  @NotNull private String name;

  @NotNull private String authorName;
}
