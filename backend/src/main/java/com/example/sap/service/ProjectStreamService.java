package com.example.sap.service;

import com.example.sap.dto.ProjectResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
public class ProjectStreamService {

    private final Map<String, List<SseEmitter>> tenantEmitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String tenantId) {
        SseEmitter emitter = new SseEmitter(0L);
        tenantEmitters.computeIfAbsent(tenantId, ignored -> new CopyOnWriteArrayList<>()).add(emitter);
        emitter.onCompletion(() -> remove(tenantId, emitter));
        emitter.onTimeout(() -> remove(tenantId, emitter));
        emitter.onError(error -> remove(tenantId, emitter));
        return emitter;
    }

    public void publishCreated(String tenantId, ProjectResponse payload) {
        List<SseEmitter> emitters = tenantEmitters.getOrDefault(tenantId, List.of());
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("project-created").data(payload));
            } catch (IOException ex) {
                log.debug("Closing stale SSE emitter for tenant {}", tenantId);
                remove(tenantId, emitter);
            }
        }
    }

    private void remove(String tenantId, SseEmitter emitter) {
        tenantEmitters.computeIfPresent(tenantId, (ignored, emitters) -> {
            emitters.remove(emitter);
            return emitters.isEmpty() ? null : emitters;
        });
    }
}
