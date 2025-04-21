# Equipment Dashboard

A comprehensive equipment management system built with Next.js 14, React Query, and Tailwind CSS. This dashboard provides features for managing equipment, materials consumption, petty cash, stock statements, and more.

## Features

- 🛠 Equipment Management
- 📊 Equipment Utilization Tracking
- 💰 Petty Cash Management
- 📦 Stock Statement
- 🏭 Materials Consumption
- 👥 Employee Management
- 📝 Maintenance Records
- 🏢 Department Management
- ⚙️ Project Management
- 🕒 Overtime Reports

## Prerequisites

- Node.js 18.x or higher
- npm 9.x or higher

## Quick Start

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd equipment-dashboard
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

4. Open [http://localhost:3000](http://localhost:3000) in your browser

## Project Structure

```
src/
├── app/                  # Next.js app directory
├── components/           # Reusable UI components
├── lib/                  # Utility functions and configurations
├── services/            # API service functions
└── styles/              # Global styles and Tailwind CSS
```

## Built With

- [Next.js 14](https://nextjs.org/) - React framework
- [React Query](https://tanstack.com/query/latest) - Data fetching and caching
- [Tailwind CSS](https://tailwindcss.com/) - Styling
- [Radix UI](https://www.radix-ui.com/) - UI components
- [Lucide React](https://lucide.dev/) - Icons
- [React Hook Form](https://react-hook-form.com/) - Form handling
- [Zod](https://zod.dev/) - Schema validation
- [Axios](https://axios-http.com/) - HTTP client

## Development

### Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm start` - Start production server
- `npm run lint` - Run ESLint

### Environment Variables

Create a `.env.local` file in the root directory:

```env
NEXT_PUBLIC_API_URL=your_api_url_here
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
