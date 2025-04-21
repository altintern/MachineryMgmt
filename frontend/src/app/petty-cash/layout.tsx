import type { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Petty Cash',
  description: 'Manage petty cash transactions and records',
};

export default function PettyCashLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="flex-1 space-y-4 p-4 md:p-8 pt-6">
      {children}
    </div>
  );
}
