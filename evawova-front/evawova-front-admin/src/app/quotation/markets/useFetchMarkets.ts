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

    useEffect(() => {
        async function fetchMarkets() {
            setError(null);

            try {
                const response = await fetch(`https://api.upbit.com/v1/market/all?is_details=true`);
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
