package com.example.project1.services.impl;

import com.example.project1.entities.AuditTable;
import com.example.project1.entities.AuditTable_;
import com.example.project1.services.criteria.TimeCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.jhipster.service.QueryService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TimeQueryService extends QueryService<AuditTable> {
    protected Specification createTimeSpecification(TimeCriteria criteria) {
        Specification specification = Specification.where(null);
       if(criteria.getFromTime() != null && criteria.getToTime() != null){
           specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(AuditTable_.CREATED_AT).as(LocalDateTime.class), criteria.formatFromTime())));
           specification = specification.and(((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(AuditTable_.CREATED_AT).as(LocalDateTime.class), criteria.formatToTime())));
       }
       return specification;
    }
}
