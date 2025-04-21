'use client';

import React from 'react';
import { Box } from '@mui/material';

export default function IncidentsLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <Box
      component="main"
      sx={{
        flexGrow: 1,
        minHeight: '100vh',
        bgcolor: 'grey.100',
      }}
    >
      {children}
    </Box>
  );
}
