package com.jzzhang.ecommerce.service;

import com.jzzhang.ecommerce.dto.ChatRequest;
import com.jzzhang.ecommerce.dto.ChatResponse;

public interface AiChatService {
    ChatResponse chat(ChatRequest request);
}
