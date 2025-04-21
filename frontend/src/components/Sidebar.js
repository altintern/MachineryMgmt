'use client';

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { cn } from '@/lib/utils';
import { Button } from '@/components/ui/button';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Sheet, SheetContent, SheetTrigger } from '@/components/ui/sheet';
import { Separator } from '@/components/ui/separator';
import { 
    Menu, 
    ChevronLeft, 
    ChevronRight,
    LayoutDashboard,
    Briefcase,
    Wrench,
    Clock,
    Cog,
    FileText,
    FolderTree,
    Tag,
    Boxes,
    Building2,
    Users,
    UserSquare,
    Anchor,
    Clock4,
    Wallet,
    Package,
    ShoppingCart,
    ClipboardList
} from 'lucide-react';

const navItems = [
    { name: 'Equipment', path: '/equipment', icon: <Wrench className="w-4 h-4" /> },
    { name: 'Equipment Utilization', path: '/equipment/utilization', icon: <Clock className="w-4 h-4" /> },
    { name: 'Mast Anchorage', path: '/equipment/mast-anchorage', icon: <Anchor className="w-4 h-4" /> },
    { name: 'Categories', path: '/equipment/categories', icon: <Tag className="w-4 h-4" /> },
    { name: 'Makes', path: '/makes', icon: <Tag className="w-4 h-4" /> },
    { name: 'Models', path: '/models', icon: <Boxes className="w-4 h-4" /> },
    { name: 'Items', path: '/items', icon: <Package className="w-4 h-4" /> },
    { name: 'Maintenance', path: '/maintenance', icon: <Cog className="w-4 h-4" /> },
    { name: 'Maintenance PartUsed', path: '/maintenance-part-used', icon: <FileText className="w-4 h-4" /> },
    { name: 'Maintenance Reading', path: '/maintenance-reading', icon: <FileText className="w-4 h-4" /> },
    { name: 'Projects', path: '/projects', icon: <FolderTree className="w-4 h-4" /> },
    { name: 'Incidents', path: '/incidents', icon: <FileText className="w-4 h-4" /> },
    { name: 'Employees', path: '/employees', icon: <Users className="w-4 h-4" /> },
    { name: 'Employees Assignment', path: '/assignments', icon: <UserSquare className="w-4 h-4" /> },
    { name: 'Departments', path: '/departments', icon: <Building2 className="w-4 h-4" /> },
    { name: 'Designations', path: '/designations', icon: <Users className="w-4 h-4" /> },
    { name: 'Overtime Report', path: '/overtime-report', icon: <Clock4 className="w-4 h-4" /> },
    { name: 'Petty Cash', path: '/petty-cash', icon: <Wallet className="w-4 h-4" /> },
    { name: 'Stock Statement', path: '/stock-statement', icon: <ClipboardList className="w-4 h-4" /> },
    { name: 'MaterialsConsumption', path: '/materials-consumption', icon: <ShoppingCart className="w-4 h-4" /> },
];

export default function Sidebar() {
    const [isCollapsed, setIsCollapsed] = useState(false);
    const [isMobileOpen, setIsMobileOpen] = useState(false);
    const pathname = usePathname();

    // Handle window resize to determine if we're on mobile
    useEffect(() => {
        const handleResize = () => {
            if (window.innerWidth < 1024) {
                setIsCollapsed(true);
            }
        };
        
        // Initial check
        handleResize();
        
        window.addEventListener('resize', handleResize);
        return () => window.removeEventListener('resize', handleResize);
    }, []);

    const toggleSidebar = () => {
        setIsCollapsed(!isCollapsed);
    };

    return (
        <>
            {/* Mobile Sidebar Trigger */}
            <Button
                variant="ghost"
                className="lg:hidden fixed top-4 left-4 z-40 px-2 py-2"
                onClick={() => setIsMobileOpen(true)}
            >
                <Menu className="h-6 w-6" />
            </Button>

            {/* Mobile Sidebar */}
            <Sheet open={isMobileOpen} onOpenChange={setIsMobileOpen}>
                <SheetContent side="left" className="p-0 w-72">
                    <SidebarContent isCollapsed={false} currentPath={pathname} />
                </SheetContent>
            </Sheet>

            {/* Desktop Sidebar */}
            <div 
                className={cn(
                    "fixed top-0 left-0 bottom-0 z-30 hidden lg:block border-r border-border/50 transition-all duration-300 bg-background",
                    isCollapsed ? "w-20" : "w-72"
                )}
            >
                <SidebarContent isCollapsed={isCollapsed} currentPath={pathname} />
                
                {/* Toggle Button */}
                <Button
                    variant="ghost"
                    size="icon"
                    className="absolute top-4 -right-4 h-8 w-8 rounded-full border shadow-md bg-background z-50"
                    onClick={toggleSidebar}
                >
                    {isCollapsed ? 
                        <ChevronRight className="h-4 w-4" /> : 
                        <ChevronLeft className="h-4 w-4" />
                    }
                </Button>
            </div>
            
            {/* Spacer div to push content over - this is crucial */}
            <div className={cn(
                "hidden lg:block transition-all duration-300 flex-none",
                isCollapsed ? "w-20" : "w-72"
            )} />
        </>
    );
}

function SidebarContent({ isCollapsed, currentPath }) {
    return (
        <div className="h-full bg-card/50 backdrop-blur-sm">
            <div className={cn(
                "flex h-16 items-center border-b border-border/50 px-4",
                isCollapsed ? "justify-center" : "gap-2"
            )}>
                <LayoutDashboard className="h-6 w-6 text-primary" />
                {!isCollapsed && <h2 className="text-lg font-semibold">Equipment</h2>}
            </div>
            <ScrollArea className="flex-1 h-[calc(100vh-4rem)]">
                <nav className="grid gap-1 p-2">
                    {navItems.map((item) => {
                        const isActive = currentPath === item.path || currentPath?.startsWith(`${item.path}/`);
                        
                        return (
                            <Link
                                key={item.name}
                                href={item.path}
                                className={cn(
                                    'flex items-center gap-3 rounded-lg px-3 py-2 text-sm',
                                    'transition-colors hover:bg-accent hover:text-accent-foreground',
                                    'focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring',
                                    isActive ? 'bg-accent text-accent-foreground' : 'text-muted-foreground hover:text-foreground',
                                    isCollapsed && 'justify-center px-0'
                                )}
                                title={isCollapsed ? item.name : undefined}
                            >
                                <span className={cn(
                                    isActive ? 'text-foreground' : 'text-muted-foreground/70',
                                    isCollapsed && 'w-full flex justify-center'
                                )}>
                                    {item.icon}
                                </span>
                                {!isCollapsed && item.name}
                            </Link>
                        );
                    })}
                </nav>
            </ScrollArea>
        </div>
    );
}
