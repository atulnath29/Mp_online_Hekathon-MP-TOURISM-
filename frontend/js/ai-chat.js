/* ==========================================================================
   GLOBAL AI CHAT WIDGET MODULE
   ========================================================================== */

document.addEventListener('DOMContentLoaded', () => {
  initAiChatWidget();
});

function initAiChatWidget() {
  const chatToggleBtn = document.getElementById('aiChatToggleBtn');
  const chatDrawer = document.getElementById('aiChatDrawer');
  const closeChatBtn = document.getElementById('closeAiChatBtn');
  const chatForm = document.getElementById('aiChatForm');
  const chatInput = document.getElementById('aiChatInput');
  const contextBar = document.getElementById('aiChatContextBar');

  if (!chatToggleBtn || !chatDrawer) return;

  chatToggleBtn.onclick = () => {
    chatDrawer.classList.toggle('active');
    if (chatDrawer.classList.contains('active')) {
      updateChatContextUI();
      if (chatInput) chatInput.focus();
    }
  };

  if (closeChatBtn) {
    closeChatBtn.onclick = () => chatDrawer.classList.remove('active');
  }

  // Quick Chip Prompts
  const chipBtns = document.querySelectorAll('.chat-chip-btn');
  chipBtns.forEach(btn => {
    btn.onclick = () => {
      const text = btn.textContent.trim();
      if (chatInput) {
        chatInput.value = text;
        submitChatMessage(text);
      }
    };
  });

  if (chatForm) {
    chatForm.onsubmit = (e) => {
      e.preventDefault();
      const message = chatInput.value.trim();
      if (!message) return;
      chatInput.value = '';
      submitChatMessage(message);
    };
  }
}

function updateChatContextUI() {
  const contextBar = document.getElementById('aiChatContextBar');
  if (!contextBar) return;

  const place = AppState.selectedPlaceData ? AppState.selectedPlaceData.name : (AppState.currentTrip.placeName || 'Ujjain');
  const budget = AppState.currentTrip.budget ? `₹${AppState.currentTrip.budget.toLocaleString('en-IN')}` : 'Not set';
  const days = AppState.currentTrip.days || 1;

  contextBar.innerHTML = `📍 <strong>Context:</strong> ${place} • 💰 Budget: ${budget} • 📅 ${days} Days`;
}

async function submitChatMessage(userMessage) {
  const messagesArea = document.getElementById('aiChatMessagesArea');
  if (!messagesArea) return;

  // Append User message
  const userMsgEl = document.createElement('div');
  userMsgEl.className = 'chat-msg user';
  userMsgEl.textContent = userMessage;
  messagesArea.appendChild(userMsgEl);
  messagesArea.scrollTop = messagesArea.scrollHeight;

  // Append Bot loading indicator
  const botMsgEl = document.createElement('div');
  botMsgEl.className = 'chat-msg bot';
  botMsgEl.innerHTML = '<em>Thinking... 🤔</em>';
  messagesArea.appendChild(botMsgEl);
  messagesArea.scrollTop = messagesArea.scrollHeight;

  const payload = {
    message: userMessage,
    currentPage: AppState.activePage,
    selectedPlace: AppState.selectedPlaceData ? AppState.selectedPlaceData.name : AppState.currentTrip.placeName,
    budget: AppState.currentTrip.budget,
    travelers: (AppState.currentTrip.men || 1) + (AppState.currentTrip.women || 0) + (AppState.currentTrip.children || 0),
    days: AppState.currentTrip.days
  };

  try {
    const res = await fetch(`${API_BASE_URL}/ai-chat`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    const data = await res.json();
    if (res.ok && data.reply) {
      botMsgEl.innerHTML = formatMarkdownToHTML(data.reply);
    } else {
      throw new Error(data.error || 'Failed to get response');
    }
  } catch (err) {
    botMsgEl.innerHTML = `Namaste! 🙏 I am pleased to assist you with your trip to <strong>${payload.selectedPlace || 'Madhya Pradesh'}</strong>. Try asking about local food, temple timings, or budget saving tips!`;
  }

  messagesArea.scrollTop = messagesArea.scrollHeight;
}

function formatMarkdownToHTML(text) {
  return text
    .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
    .replace(/\*(.*?)\*/g, '<em>$1</em>')
    .replace(/\n/g, '<br>');
}
