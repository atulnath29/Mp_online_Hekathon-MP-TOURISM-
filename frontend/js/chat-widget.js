/**
 * Madhya Pradesh Tourism - Floating AI Chat Widget
 * Embeddable on all pages (index, place, budget, suggestions, hotels, payment)
 * Interfaces with POST /api/ai-chat via API.sendAiChat()
 */

(function () {
  // Inject Widget Styles
  const style = document.createElement('style');
  style.innerHTML = `
    .ai-chat-launcher {
      position: fixed;
      bottom: 25px;
      right: 25px;
      width: 60px;
      height: 60px;
      border-radius: 50%;
      background: linear-gradient(135deg, #e65100, #ff6d00);
      color: white;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1.8rem;
      box-shadow: 0 8px 25px rgba(230, 81, 0, 0.4);
      cursor: pointer;
      z-index: 2500;
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
      border: 2px solid #ffffff;
    }
    .ai-chat-launcher:hover {
      transform: scale(1.08) rotate(5deg);
      box-shadow: 0 12px 30px rgba(230, 81, 0, 0.5);
    }
    .ai-chat-window {
      position: fixed;
      bottom: 95px;
      right: 25px;
      width: 370px;
      max-width: calc(100vw - 40px);
      height: 520px;
      max-height: calc(100vh - 120px);
      background: #ffffff;
      border-radius: 20px;
      box-shadow: 0 12px 40px rgba(0, 0, 0, 0.2);
      display: flex;
      flex-direction: column;
      z-index: 2500;
      overflow: hidden;
      border: 1px solid #e0e0e0;
      opacity: 0;
      pointer-events: none;
      transform: translateY(20px) scale(0.95);
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    }
    .ai-chat-window.active {
      opacity: 1;
      pointer-events: auto;
      transform: translateY(0) scale(1);
    }
    .ai-chat-header {
      background: linear-gradient(135deg, #e65100, #b23c00);
      color: white;
      padding: 1rem 1.25rem;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    .ai-chat-header-info {
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }
    .ai-chat-avatar {
      width: 38px;
      height: 38px;
      background: rgba(255, 255, 255, 0.2);
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1.2rem;
    }
    .ai-chat-title {
      font-family: 'Outfit', sans-serif;
      font-weight: 700;
      font-size: 1.05rem;
      margin: 0;
      line-height: 1.2;
    }
    .ai-chat-status {
      font-size: 0.75rem;
      color: #ffe082;
      display: flex;
      align-items: center;
      gap: 0.3rem;
    }
    .status-dot {
      width: 7px;
      height: 7px;
      background: #00e676;
      border-radius: 50%;
      display: inline-block;
    }
    .ai-chat-close {
      background: transparent;
      border: none;
      color: white;
      font-size: 1.2rem;
      cursor: pointer;
      opacity: 0.8;
      transition: opacity 0.2s;
    }
    .ai-chat-close:hover {
      opacity: 1;
    }
    .ai-chat-body {
      flex: 1;
      padding: 1.25rem;
      overflow-y: auto;
      background: #faf8f5;
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }
    .chat-msg {
      max-width: 82%;
      padding: 0.75rem 1rem;
      border-radius: 16px;
      font-size: 0.9rem;
      line-height: 1.45;
      word-break: break-word;
    }
    .chat-msg-user {
      align-self: flex-end;
      background: #e65100;
      color: white;
      border-bottom-right-radius: 4px;
      box-shadow: 0 2px 8px rgba(230, 81, 0, 0.2);
    }
    .chat-msg-ai {
      align-self: flex-start;
      background: #ffffff;
      color: #212121;
      border: 1px solid #e0e0e0;
      border-bottom-left-radius: 4px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
    }
    .chat-typing {
      align-self: flex-start;
      background: #ffffff;
      padding: 0.6rem 1rem;
      border-radius: 16px;
      border-bottom-left-radius: 4px;
      font-size: 0.82rem;
      color: #666;
      font-style: italic;
      display: flex;
      align-items: center;
      gap: 0.5rem;
      border: 1px solid #e0e0e0;
    }
    .ai-chat-footer {
      padding: 0.75rem 1rem;
      background: #ffffff;
      border-top: 1px solid #e0e0e0;
      display: flex;
      align-items: center;
      gap: 0.5rem;
    }
    .ai-chat-input {
      flex: 1;
      border: 1px solid #e0e0e0;
      border-radius: 25px;
      padding: 0.65rem 1rem;
      font-size: 0.9rem;
      outline: none;
      transition: border 0.2s;
    }
    .ai-chat-input:focus {
      border-color: #e65100;
    }
    .ai-chat-send {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      background: #e65100;
      color: white;
      border: none;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 1rem;
      transition: background 0.2s;
    }
    .ai-chat-send:hover {
      background: #ff6d00;
    }
  `;
  document.head.appendChild(style);

  // Inject UI Elements into DOM
  document.addEventListener('DOMContentLoaded', () => {
    // Launcher Button
    const launcher = document.createElement('div');
    launcher.className = 'ai-chat-launcher';
    launcher.id = 'aiChatLauncher';
    launcher.innerHTML = '🤖';
    launcher.title = 'Ask MP Tourism AI Assistant';
    document.body.appendChild(launcher);

    // Chat Window
    const chatWin = document.createElement('div');
    chatWin.className = 'ai-chat-window';
    chatWin.id = 'aiChatWindow';
    chatWin.innerHTML = `
      <div class="ai-chat-header">
        <div class="ai-chat-header-info">
          <div class="ai-chat-avatar">🤖</div>
          <div>
            <div class="ai-chat-title">MP Tourism AI Assistant</div>
            <div class="ai-chat-status"><span class="status-dot"></span> Online • Powered by Gemini</div>
          </div>
        </div>
        <button class="ai-chat-close" id="aiChatClose">✕</button>
      </div>
      <div class="ai-chat-body" id="aiChatBody">
        <div class="chat-msg chat-msg-ai">
          Namaste! 🙏 I am your <strong>Madhya Pradesh AI Travel Assistant</strong>. Ask me anything about Ujjain, Khajuraho, Mahakal Aarti timings, tiger safaris, or trip budget planning!
        </div>
      </div>
      <div class="ai-chat-footer">
        <input type="text" class="ai-chat-input" id="aiChatInput" placeholder="Ask AI (e.g. Best time for Kanha safari?)" autocomplete="off">
        <button class="ai-chat-send" id="aiChatSend">➤</button>
      </div>
    `;
    document.body.appendChild(chatWin);

    // Event Listeners
    const launcherBtn = document.getElementById('aiChatLauncher');
    const closeBtn = document.getElementById('aiChatClose');
    const inputEl = document.getElementById('aiChatInput');
    const sendBtn = document.getElementById('aiChatSend');

    launcherBtn.addEventListener('click', toggleChat);
    closeBtn.addEventListener('click', toggleChat);

    sendBtn.addEventListener('click', sendChatMessage);
    inputEl.addEventListener('keydown', (e) => {
      if (e.key === 'Enter') {
        sendChatMessage();
      }
    });
  });

  function toggleChat() {
    const chatWin = document.getElementById('aiChatWindow');
    chatWin.classList.toggle('active');
    if (chatWin.classList.contains('active')) {
      document.getElementById('aiChatInput').focus();
    }
  }

  async function sendChatMessage() {
    const inputEl = document.getElementById('aiChatInput');
    const bodyEl = document.getElementById('aiChatBody');
    const text = inputEl.value.trim();

    if (!text) return;

    // Append User Message
    const userMsgDiv = document.createElement('div');
    userMsgDiv.className = 'chat-msg chat-msg-user';
    userMsgDiv.innerText = text;
    bodyEl.appendChild(userMsgDiv);

    inputEl.value = '';
    bodyEl.scrollTop = bodyEl.scrollHeight;

    // Append Typing Indicator
    const typingDiv = document.createElement('div');
    typingDiv.className = 'chat-typing';
    typingDiv.id = 'aiTypingIndicator';
    typingDiv.innerHTML = `<span>🤖</span> MP AI Assistant is thinking...`;
    bodyEl.appendChild(typingDiv);
    bodyEl.scrollTop = bodyEl.scrollHeight;

    try {
      // Call API
      const pageName = document.title || 'MP Tourism';
      const response = await window.API.sendAiChat(text, { page: pageName });

      // Remove Typing Indicator
      const indicator = document.getElementById('aiTypingIndicator');
      if (indicator) indicator.remove();

      // Append AI Reply
      const aiMsgDiv = document.createElement('div');
      aiMsgDiv.className = 'chat-msg chat-msg-ai';
      aiMsgDiv.innerHTML = formatMarkdownText(response.reply || 'Namaste! How can I assist you further with MP Tourism?');
      bodyEl.appendChild(aiMsgDiv);
    } catch (err) {
      const indicator = document.getElementById('aiTypingIndicator');
      if (indicator) indicator.remove();

      const errorMsgDiv = document.createElement('div');
      errorMsgDiv.className = 'chat-msg chat-msg-ai';
      errorMsgDiv.style.borderLeft = '3px solid #c62828';
      errorMsgDiv.innerHTML = `⚠️ ${err.message || 'Unable to connect to AI Assistant. Please check if the backend server is running.'}`;
      bodyEl.appendChild(errorMsgDiv);
    }

    bodyEl.scrollTop = bodyEl.scrollHeight;
  }

  function formatMarkdownText(text) {
    if (!text) return '';
    return text
      .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
      .replace(/\n/g, '<br>');
  }
})();
