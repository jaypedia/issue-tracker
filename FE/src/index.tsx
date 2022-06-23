import React from 'react';
import { createRoot } from 'react-dom/client';

import App from '@/App';
import { worker } from '@/mocks/worker';

const root = createRoot(document.getElementById('root') as HTMLElement);

if (process.env.NODE_ENV === 'development') {
  worker.start();
}

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
