package com.pragma.powerup.infrastructure.out.mongo.repository;

import com.pragma.powerup.infrastructure.out.mongo.document.TraceLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITraceLogMongoRepository extends MongoRepository<TraceLogDocument, String> {
    List<TraceLogDocument> findByOrderIdAndClientIdOrderByChangedAtAsc(Long orderId, Long clientId);
}
