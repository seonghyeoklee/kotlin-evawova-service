'use client';

import { useState, useEffect } from 'react';

interface UpbitTickerResponse {
    market: string;
    trade_date: string;
    trade_time: string;
    trade_price: number;
    change: 'EVEN' | 'RISE' | 'FALL';
    change_rate: number;
    opening_price: number;
    high_price: number;
    low_price: number;
    prev_closing_price: number;
    acc_trade_volume24h: number;
    acc_trade_price24h: number;
    highest_52_week_price: number;
    highest_52_week_date: string;
    lowest_52_week_price: number;
    lowest_52_week_date: string;
}

const useFetchTicker = (markets: string) => {
    const [data, setData] = useState<UpbitTickerResponse[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<Error | null>(null);

    useEffect(() => {
        const fetchTicker = async () => {
            setError(null);

            try {
                const response = await fetch(`http://localhost:8080/api/v1/upbit/ticker?markets=${markets}`);
                const jsonData: UpbitTickerResponse[] = await response.json();
                setData(jsonData);
            } catch (err) {
                setError(err as Error);
            } finally {
                setLoading(false);
            }
        };

        if (markets) {
            fetchTicker();
        }
    }, [markets]);

    return { data, loading, error };
};

export default useFetchTicker;
