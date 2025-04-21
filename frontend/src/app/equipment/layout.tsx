'use client';

import { QueryClient, QueryClientProvider } from 'react-query';
import { PropsWithChildren } from 'react';

const queryClient = new QueryClient();

export default function EquipmentLayout({ children }: PropsWithChildren) {
  return (
    <QueryClientProvider client={queryClient}>
      {children}
    </QueryClientProvider>
  );
}
