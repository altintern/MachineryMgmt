'use client';

import './globals.css';
import { Inter } from 'next/font/google';
import Sidebar from '@/components/Sidebar';

const inter = Inter({ subsets: ['latin'] });

export default function RootLayout({ children }) {
    return (
        <html lang="en" suppressHydrationWarning className="dark">
            <body className={`${inter.className} dark bg-background text-foreground`}>
                <Sidebar />
                <main className="min-h-screen lg:ml-72">
                    <div className="p-4 max-w-[2000px] mx-auto">
                        {children}
                    </div>
                </main>
            </body>
        </html>
    );
}
