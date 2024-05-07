package com.example.project1.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateRequest {
    @NotNull(message = "Title should not be null")
    private String title;

    @NotNull(message = "Language should not be null")
    private Long languageId;

    @NotNull(message = "Category should not be null")
    private Long categoryId;

    @NotNull(message = "Publisher should not be null")
    private Long publisherId;

    @NotNull(message = "Author should not be null")
    private Long AuthorId;

    @NotNull(message = "Price should not be null")
    @Min(value = 0, message = "Price must be more than 0")
    @Max(value = 999999, message = "Price muse be less then 999999")
    private Double price;

    @NotNull(message = "num of page should not be null")
    @Min(value = 0, message = "num of page must be more than 0")
    @Max(value = 999999, message = "num of page muse be less then 999")
    private Integer numOfPages;

    @NotNull(message = "description should not be null")
    private String description;

    @NotNull(message = "quantity should not be null")
    @Min(value = 0, message = "quantity must be more than 0")
    @Max(value = 999999, message = "quantity muse be less then 999")
    private Integer quantity;

    private MultipartFile image;

}
