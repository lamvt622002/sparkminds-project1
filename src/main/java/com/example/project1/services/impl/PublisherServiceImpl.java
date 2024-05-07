package com.example.project1.services.impl;

import com.example.project1.mapper.PublisherMapper;
import com.example.project1.payload.response.PublisherResponse;
import com.example.project1.repository.PublisherRepository;
import com.example.project1.services.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;

    private final PublisherMapper publisherMapper;
    @Override
    public List<PublisherResponse> getAllPublisher() {
        return publisherMapper.listPublisherToListPublisherResponse(publisherRepository.findAll());
    }
}
