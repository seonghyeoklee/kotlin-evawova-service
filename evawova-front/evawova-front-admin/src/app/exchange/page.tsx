'use client';

import React, { useEffect, useState } from 'react';
import dynamic from 'next/dynamic';
import { ApexOptions } from 'apexcharts'; // ApexOptions 타입 추가

const Chart = dynamic(() => import('react-apexcharts'), { ssr: false });

interface CandleData {
    candle_date_time_utc: string;
    candle_date_time_kst: string;
    opening_price: number;
    high_price: number;
    low_price: number;
    trade_price: number;
    timestamp: number;
}

export default function CandleChartPage() {
    const [candles, setCandles] = useState<CandleData[]>([]);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        let intervalId: NodeJS.Timeout;

        const fetchCandles = async () => {
            try {
                setIsLoading(true);
                const response = await fetch(`http://localhost:8080/api/v1/upbit/candles/seconds?market=KRW-BTC&count=100`);
                const data: CandleData[] = await response.json();
                setCandles(data);
            } catch (error) {
                console.error('Failed to fetch candle data:', error);
            } finally {
                setIsLoading(false);
            }
        };

        // 초기 데이터 가져오기
        fetchCandles();

        intervalId = setInterval(() => {
            fetchCandles();
        }, 3000);

        return () => clearInterval(intervalId);
    }, []);

    const chartData = candles.map((candle) => ({
        x: new Date(candle.candle_date_time_kst),
        y: [candle.opening_price, candle.high_price, candle.low_price, candle.trade_price],
    }));

    // ApexCharts 옵션 설정
    const options: ApexOptions = {
        chart: {
            type: 'candlestick',
            height: 350,
        },
        title: {
            text: `KRW-BTC 캔들 차트`,
            align: 'center',
        },
        xaxis: {
            type: 'datetime',
        },
        yaxis: {
            tooltip: {
                enabled: true,
            },
        },
        plotOptions: {
            candlestick: {
                colors: {
                    upward: '#DD3D44', // 상승 (빨강)
                    downward: '#1275EC', // 하락 (파랑)
                },
            },
        },
    };

    return (
        <div
            style={{
                padding: '24px',
                fontFamily: 'Arial, sans-serif',
                backgroundColor: '#f8f9fa',
                height: '100vh',
                overflow: 'auto',
            }}
        >
            <h2 style={{ textAlign: 'center', marginBottom: '16px' }}>KRW-BTC 캔들 차트</h2>
            {candles.length > 0 ? (
                <Chart
                    options={options}
                    series={[{ data: chartData }]}
                    type="candlestick"
                    height={500}
                />
            ) : (
                <div>No data available</div>
            )}
        </div>
    );
}
