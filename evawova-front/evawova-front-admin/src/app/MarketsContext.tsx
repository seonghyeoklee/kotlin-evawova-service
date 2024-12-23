'use client';

import React, { createContext, useContext, useState } from 'react';

interface MarketsContextProps {
    markets: string[];
    setMarkets: (markets: string[]) => void;
}

const MarketsContext = createContext<MarketsContextProps | undefined>(undefined);

export const MarketsProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [markets, setMarkets] = useState<string[]>([]);

    return (
        <MarketsContext.Provider value={{ markets, setMarkets }}>
            {children}
        </MarketsContext.Provider>
    );
};

export const useMarkets = (): MarketsContextProps => {
    const context = useContext(MarketsContext);
    if (!context) {
        throw new Error('useMarkets must be used within a MarketsProvider');
    }
    return context;
};
