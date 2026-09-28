package com.mptourism.planner.controller;

import com.mptourism.planner.dto.ChatRequest;
import com.mptourism.planner.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai-chat")
public class AiChatController {

    @Autowired
    private AiService aiService;

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody ChatRequest request) {
        try {
            if (request.getMessage() == null || request.getMessage().isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Message cannot be empty."));
            }
            String reply = aiService.generateResponse(request);
            return ResponseEntity.ok(Map.of(
                "reply", reply,
                "timestamp", System.currentTimeMillis()
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "AI assistant is taking a short rest: " + e.getMessage()
            ));
        }
    }
}
