package com.example.project1.payload.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PublisherResponse {
    private Long id;

    private String publisherName;
}
