package com.jzzhang.ecommerce.controller;

import com.jzzhang.ecommerce.dto.ChatRequest;
import com.jzzhang.ecommerce.dto.ChatResponse;
import com.jzzhang.ecommerce.service.AiChatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

    @Mock
    private AiChatService aiChatService;

    @InjectMocks
    private ChatController chatController;

    @Test
    void chat_delegatesToServiceAndReturnsResponse() {
        ChatRequest request = buildRequest("user", "hello");
        ChatResponse expected = new ChatResponse("Hi there!");
        when(aiChatService.chat(request)).thenReturn(expected);

        ChatResponse result = chatController.chat(request);

        assertEquals("Hi there!", result.getMessage());
        verify(aiChatService).chat(request);
    }

    @Test
    void chat_callsServiceExactlyOnce() {
        ChatRequest request = buildRequest("user", "what keyboards do you have?");
        when(aiChatService.chat(any())).thenReturn(new ChatResponse("We have many options!"));

        chatController.chat(request);

        verify(aiChatService, times(1)).chat(request);
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
