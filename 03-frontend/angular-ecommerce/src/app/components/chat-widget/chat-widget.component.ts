import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

interface ChatMessage {
  role: 'user' | 'assistant';
  content: string;
}

@Component({
  selector: 'app-chat-widget',
  templateUrl: './chat-widget.component.html',
  styleUrls: ['./chat-widget.component.css']
})
export class ChatWidgetComponent {
  isOpen = false;
  userInput = '';
  messages: ChatMessage[] = [];
  isLoading = false;
  private chatUrl = `${environment.apiBaseUrl}/chat`;

  constructor(private http: HttpClient) {
    const saved = sessionStorage.getItem('chatMessages');
    if (saved) {
      this.messages = JSON.parse(saved);
    }
  }

  toggle() {
    this.isOpen = !this.isOpen;
  }

  sendMessage() {
    const text = this.userInput.trim();
    if (!text || this.isLoading) return;

    this.messages.push({ role: 'user', content: text });
    this.userInput = '';
    this.isLoading = true;

    this.http.post<{ message: string }>(this.chatUrl, {
      messages: this.messages
    }).subscribe({
      next: (res) => {
        this.messages.push({ role: 'assistant', content: res.message });
        sessionStorage.setItem('chatMessages', JSON.stringify(this.messages));
        this.isLoading = false;
      },
      error: () => {
        this.messages.push({ role: 'assistant', content: 'Sorry, something went wrong. Please try again.' });
        this.isLoading = false;
      }
    });
  }

  onKeyDown(event: KeyboardEvent) {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault();
      this.sendMessage();
    }
  }
}
