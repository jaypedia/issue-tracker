import { setupWorker } from 'msw';

import handlers from '@/mocks/handlers';

// This configures a Service Worker with the given request handlers.
// Service Worker 생성 : 작성한 handler 코드를 setupWorker() 함수의 인자로 넘겨준다.
export const worker = setupWorker(...handlers);
