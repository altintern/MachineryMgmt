import type { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Mast Anchorage',
  description: 'Manage mast anchorage equipment and details',
};

export default function MastAnchorageLayout({
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
