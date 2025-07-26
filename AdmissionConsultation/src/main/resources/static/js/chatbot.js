function toggleChat() {
    const chat = document.getElementById('chat-window');
    chat.style.display = chat.style.display === 'flex' ? 'none' : 'flex';
}

function handleKey(event) {
    if (event.key === 'Enter') {
        const input = document.getElementById('user-input');
        const message = input.value.trim();
        if (message) {
            appendMessage('Bạn', message);
            input.value = '';

            // 👇 Thêm "typing..." ảo của bot
            const typingId = appendTyping();

            // Gửi tới backend
            fetch('/chatbot', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ message })
            })
                .then(res => res.json())
                .then(data => {
                    removeTyping(typingId);             // ❌ Bỏ "typing..."
                    appendMessage('Bot', data.reply);   // ✅ Thêm tin nhắn thật
                })
                .catch(() => {
                    removeTyping(typingId);
                    appendMessage('Bot', 'Có lỗi xảy ra.');
                });
        }
    }
}


function appendMessage(sender, text) {
    const messages = document.getElementById('chat-messages');
    const messageDiv = document.createElement('div');
    messageDiv.classList.add('message', sender === 'Bạn' ? 'user' : 'bot');
    messageDiv.textContent = text;
    messages.appendChild(messageDiv);
    messages.scrollTop = messages.scrollHeight;
}

function appendTyping() {
    const messages = document.getElementById('chat-messages');
    const div = document.createElement('div');
    div.classList.add('message', 'bot', 'typing');
    div.textContent = 'Bot đang nhập...';
    messages.appendChild(div);
    messages.scrollTop = messages.scrollHeight;
    return div;
}

function removeTyping(typingElement) {
    if (typingElement && typingElement.parentNode) {
        typingElement.parentNode.removeChild(typingElement);
    }
}

