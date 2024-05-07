package com.example.project1.mapper;

import com.example.project1.entities.Publisher;
import com.example.project1.payload.response.PublisherResponse;

import java.util.List;

public interface PublisherMapper {
    List<PublisherResponse> listPublisherToListPublisherResponse(List<Publisher> publishers);
}
