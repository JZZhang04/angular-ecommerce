package com.jzzhang.ecommerce.service;

import com.anthropic.client.AnthropicClient;
import com.anthropic.models.messages.*;
import com.jzzhang.ecommerce.dto.ChatRequest;
import com.jzzhang.ecommerce.dto.ChatResponse;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiChatServiceImpl implements AiChatService {

    private static final String SYSTEM_PROMPT =
        "You are a helpful shopping assistant for an electronics e-commerce store. " +
        "The store sells Keyboards, Headphones, Mouses, and Monitor Stands. " +
        "Help customers find products, answer questions about specifications, " +
        "compare items, and guide their purchase decisions. " +
        "Keep responses concise and friendly.";

    private final AnthropicClient client;
    private final MeterRegistry meterRegistry;

    public AiChatServiceImpl(AnthropicClient client, MeterRegistry meterRegistry) {
        this.client = client;
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ChatResponse chat(ChatRequest request) {
        List<MessageParam> messageParams = request.getMessages().stream()
                .map(msg -> MessageParam.builder()
                        .role("user".equals(msg.getRole()) ? MessageParam.Role.USER : MessageParam.Role.ASSISTANT)
                        .content(msg.getContent())
                        .build())
                .toList();

        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.CLAUDE_HAIKU_4_5)
                .maxTokens(1024L)
                .system(SYSTEM_PROMPT)
                .messages(messageParams)
                .build();

        Timer.Sample sample = Timer.start(meterRegistry);
        Message response = client.messages().create(params);
        sample.stop(meterRegistry.timer("ai.chat.response.time"));

        String replyText = response.content().stream()
                .flatMap(block -> block.text().stream())
                .map(textBlock -> textBlock.text())
                .findFirst()
                .orElse("Sorry, I could not generate a response.");

        return new ChatResponse(replyText);
    }
}
