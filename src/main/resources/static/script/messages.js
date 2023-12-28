export const getMessagesProperties = async () => {
    try {
        const response = await fetch('/i18n/getMessages');
        if (response.ok) {
            const messages = await response.json();
            return messages;
        } else {
            console.error('Error fetching messages prop:', response.statusText);
            return null;
        }
    } catch (error) {
        console.error('Error fetching messages prop:', error.message);
        return null;
    }
};