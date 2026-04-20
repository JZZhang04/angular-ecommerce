package com.jzzhang.ecommerce.service;

import com.anthropic.client.AnthropicClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.jzzhang.ecommerce.dto.ChatRequest;
import com.jzzhang.ecommerce.dto.ChatResponse;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AiChatServiceImplTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private AnthropicClient client;

    private AiChatServiceImpl aiChatService;

    @BeforeEach
    void setUp() {
        aiChatService = new AiChatServiceImpl(client, new SimpleMeterRegistry());
    }

    @Test
    void chat_returnsDefaultMessageWhenContentIsEmpty() {
        Message messageMock = mock(Message.class);
        when(messageMock.content()).thenReturn(Collections.emptyList());
        when(client.messages().create(any(MessageCreateParams.class))).thenReturn(messageMock);

        ChatResponse response = aiChatService.chat(buildRequest("user", "hello"));

        assertEquals("Sorry, I could not generate a response.", response.getMessage());
    }

    @Test
    void chat_propagatesExceptionFromApi() {
        when(client.messages().create(any(MessageCreateParams.class)))
                .thenThrow(new RuntimeException("API error"));

        assertThrows(RuntimeException.class, () -> aiChatService.chat(buildRequest("user", "hello")));
    }

    private ChatRequest buildRequest(String role, String content) {
        ChatRequest request = new ChatRequest();
        ChatRequest.ChatMessage msg = new ChatRequest.ChatMessage();
        msg.setRole(role);
        msg.setContent(content);
        request.setMessages(List.of(msg));
        return request;
    }
}
