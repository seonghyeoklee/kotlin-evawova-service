'use client';

import { useState, useEffect } from 'react';

interface MarketEvent {
    warning: boolean;
    caution: Record<string, boolean>;
}

interface MarketData {
    market: string;
    korean_name: string;
    english_name: string;
    market_event: MarketEvent;
}

const useFetchMarkets = () => {
    const [data, setData] = useState<MarketData[]>([]);
    const [filteredData, setFilteredData] = useState<MarketData[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<Error | null>(null);
    const baseUrl = process.env.NEXT_PUBLIC_API_BASE_URL;

    useEffect(() => {
        async function fetchMarkets() {
            setError(null);

            try {
                const response = await fetch(`${baseUrl}/api/v1/upbit/market`);
                const jsonData: MarketData[] = await response.json();
                setData(jsonData);
                setFilteredData(jsonData);
            } catch (err) {
                setError(err as Error);
            } finally {
                setLoading(false);
            }
        }

        fetchMarkets();
    }, []);

    return { data, filteredData, setFilteredData, loading, error };
};

export default useFetchMarkets;
