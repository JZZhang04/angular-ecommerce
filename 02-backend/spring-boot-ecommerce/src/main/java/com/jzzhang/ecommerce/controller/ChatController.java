package com.jzzhang.ecommerce.controller;

import com.jzzhang.ecommerce.dto.ChatRequest;
import com.jzzhang.ecommerce.dto.ChatResponse;
import com.jzzhang.ecommerce.service.AiChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = {"http://localhost:4200", "https://ecommerce-angular-front.netlify.app"})
public class ChatController {

    private final AiChatService aiChatService;

    public ChatController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return aiChatService.chat(request);
    }
}
