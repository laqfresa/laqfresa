export interface SalesMonth {
    month: string;
    totalDailyMonth: number;
}

export interface SalesWeek {
    weekInitial: string;
    weekFinish: string;
    totalDailyWeek: number;
}

export interface SalesDaily {
    date: string;
    totalDailySales: number;
}