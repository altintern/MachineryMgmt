import type { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Overtime Report',
  description: 'View and manage overtime reports',
};

export default function OvertimeReportLayout({
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
