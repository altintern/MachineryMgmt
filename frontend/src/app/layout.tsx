'use client';

import './globals.css';
import { Inter } from 'next/font/google';
import Sidebar from '@/components/Sidebar';
import { QueryClient, QueryClientProvider } from 'react-query';
import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { theme } from '@/theme';
import { Toaster } from 'react-hot-toast';

const inter = Inter({ subsets: ['latin'] });

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false,
      retry: false,
    },
  },
});

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className={inter.className} style={{ margin: 0, padding: 0 }}>
        <QueryClientProvider client={queryClient}>
          <ThemeProvider theme={theme}>
            <CssBaseline />
            <div className="flex min-h-screen">
              <Sidebar />
              <main className="flex-1 bg-gray-50">
                {children}
              </main>
            </div>
            <Toaster position="top-right" />
          </ThemeProvider>
        </QueryClientProvider>
      </body>
    </html>
  );
}
