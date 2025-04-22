import React from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from '@/components/ui/table';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Plus, Pencil, Trash2 } from 'lucide-react';

interface DataTableProps {
  title: string;
  columns: { key: string; label: string }[];
  data: any[];
  onAdd: () => void;
  onEdit: (item: any) => void;
  onDelete: (id: number) => void;
  renderCustomCell?: (column: string, item: any) => React.ReactNode;
}

export default function DataTable({
  title,
  columns,
  data,
  onAdd,
  onEdit,
  onDelete,
  renderCustomCell,
}: DataTableProps) {
  return (
    <Card className="w-full shadow-md">
      <CardHeader className="flex flex-row items-center justify-between bg-muted/20 pb-2">
        <CardTitle className="text-xl font-semibold">{title}</CardTitle>
        <Button onClick={onAdd} className="bg-primary hover:bg-primary/90">
          <Plus className="mr-2 h-4 w-4" />
          Add {title.replace(/s$/, '')}
        </Button>
      </CardHeader>
      <CardContent className="p-0">
        <div className="w-full overflow-auto">
          <Table>
            <TableHeader>
              <TableRow className="border-b border-border/50 bg-muted/30">
                {columns.map((column) => (
                  <TableHead key={column.key} className="py-3 text-left font-medium">
                    {column.label}
                  </TableHead>
                ))}
                <TableHead className="py-3 text-right font-medium">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {data.length === 0 ? (
                <TableRow>
                  <TableCell colSpan={columns.length + 1} className="text-center py-6 text-muted-foreground">
                    No data available
                  </TableCell>
                </TableRow>
              ) : (
                (Array.isArray(data) ? data : []).map((item) => (
                  <TableRow key={item.id} className="border-b border-border/50 hover:bg-muted/10">
                    {columns.map((column) => (
                      <TableCell key={`${item.id}-${column.key}`} className="py-3">
                        {renderCustomCell ? (
                          renderCustomCell(column.key, item)
                        ) : (
                          column.key.includes('.') ? 
                            column.key.split('.').reduce((obj, key) => obj && obj[key], item) !== undefined ? 
                              column.key.split('.').reduce((obj, key) => obj && obj[key], item) : '-' :
                            item[column.key] !== undefined && item[column.key] !== null ? 
                              String(item[column.key]) : '-'
                        )}
                      </TableCell>
                    ))}
                    <TableCell className="py-3 text-right">
                      <div className="flex justify-end gap-2">
                        <Button
                          variant="ghost"
                          size="icon"
                          onClick={() => onEdit(item)}
                          className="h-8 w-8 p-0 text-blue-500 hover:text-blue-600 hover:bg-blue-50"
                        >
                          <Pencil className="h-4 w-4" />
                        </Button>
                        <Button
                          variant="ghost"
                          size="icon"
                          onClick={() => onDelete(item.id)}
                          className="h-8 w-8 p-0 text-red-500 hover:text-red-600 hover:bg-red-50"
                        >
                          <Trash2 className="h-4 w-4" />
                        </Button>
                      </div>
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
        </div>
      </CardContent>
    </Card>
  );
}
