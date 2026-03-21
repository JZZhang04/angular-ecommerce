package com.jzzhang.ecommerce.controller;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.MessageParam;
import com.anthropic.models.messages.Model;
import com.jzzhang.ecommerce.dto.ChatRequest;
import com.jzzhang.ecommerce.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Value("${anthropic.api.key}")
    private String anthropicApiKey;

    private static final String SYSTEM_PROMPT =
        "You are a helpful shopping assistant for an electronics e-commerce store. " +
        "The store sells Keyboards, Headphones, Mouses, and Monitor Stands. " +
        "Help customers find products, answer questions about specifications, " +
        "compare items, and guide their purchase decisions. " +
        "Keep responses concise and friendly.";

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {
        AnthropicClient client = AnthropicOkHttpClient.builder()
                .apiKey(anthropicApiKey)
                .build();

        List<MessageParam> messageParams = request.getMessages().stream()
                .map(msg -> {
                    if ("user".equals(msg.getRole())) {
                        return MessageParam.builder()
                                .role(MessageParam.Role.USER)
                                .content(msg.getContent())
                                .build();
                    } else {
                        return MessageParam.builder()
                                .role(MessageParam.Role.ASSISTANT)
                                .content(msg.getContent())
                                .build();
                    }
                })
                .toList();

        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_OPUS_4_6)
                .maxTokens(1024L)
                .system(SYSTEM_PROMPT)
                .messages(messageParams)
                .build();

        Message response = client.messages().create(params);

        String replyText = response.content().stream()
                .flatMap(block -> block.text().stream())
                .map(textBlock -> textBlock.text())
                .findFirst()
                .orElse("Sorry, I could not generate a response.");

        return new ChatResponse(replyText);
    }
}
