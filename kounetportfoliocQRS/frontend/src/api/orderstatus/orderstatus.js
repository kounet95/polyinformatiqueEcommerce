const BASE_URL = 'http://localhost:8888/ecpolyquery';

// Regarder le statut d'une commande via son orderId (SSE - Server-Sent Events)
export const watchOrderStatus = (orderId, onMessage, onError) => {
  const url = `${BASE_URL}/api/orderstatus/watch/${orderId}`;
  const eventSource = new EventSource(url);

  eventSource.onmessage = (event) => {
    if (onMessage) {
      try {
        const data = JSON.parse(event.data);
        onMessage(data);
      } catch (error) {
        console.error('Erreur lors du parsing des données SSE :', error);
      }
    }
  };

  eventSource.onerror = (error) => {
    console.error('Erreur SSE:', error);
    if (onError) onError(error);
    // Optionnel: eventSource.close();
  };

  return eventSource; // Pour permettre un .close() plus tard si besoin
};

// Regarder le statut d'une commande via le code-barre (SSE - Server-Sent Events)
export const watchOrderStatusByBarcode = (barcode, onMessage, onError) => {
  const url = `${BASE_URL}/api/orderstatus/watch-by-barcode/${barcode}`;
  const eventSource = new EventSource(url);

  eventSource.onmessage = (event) => {
    if (onMessage) {
      try {
        const data = JSON.parse(event.data);
        onMessage(data);
      } catch (error) {
        console.error('Erreur lors du parsing des données SSE :', error);
      }
    }
  };

  eventSource.onerror = (error) => {
    console.error('Erreur SSE:', error);
    if (onError) onError(error);
    // Optionnel: eventSource.close();
  };

  return eventSource;
};