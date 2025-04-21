import type { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Stock Statement',
  description: 'View and manage stock statements and inventory records',
};

export default function StockStatementLayout({
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
