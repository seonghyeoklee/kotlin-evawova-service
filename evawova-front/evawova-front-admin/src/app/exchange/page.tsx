'use client';

import React, { useEffect, useState, useCallback } from 'react';
import dynamic from 'next/dynamic';
import { Select, Spin } from 'antd';
import { ApexOptions } from 'apexcharts';

const { Option } = Select;

const Chart = dynamic(() => import('react-apexcharts'), { ssr: false });

interface CandleData {
    candle_date_time_utc: string;
    candle_date_time_kst: string;
    opening_price: number;
    high_price: number;
    low_price: number;
    trade_price: number;
    candle_acc_trade_price: number;
    candle_acc_trade_volume: number;
}

const timeUnits = [
    { label: '초', value: 'seconds' },
    { label: '분', value: 'minutes' },
    { label: '일', value: 'days' },
    { label: '주', value: 'weeks' },
    { label: '월', value: 'months' },
    { label: '연', value: 'years' },
];

export default function EnhancedCandleChartPage() {
    const [candles, setCandles] = useState<CandleData[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [timeUnit, setTimeUnit] = useState('minutes');
    const [market] = useState('KRW-BTC');

// fetchCandles를 useCallback으로 메모이제이션
    const fetchCandles = useCallback(async (unit: string) => {
        try {
            setIsLoading(true);
            const response = await fetch(`http://localhost:8080/api/v1/upbit/candles/${unit}?market=${market}&count=200`);
            const data = await response.json();
            setCandles(Array.isArray(data) ? data : []);
        } catch (error) {
            console.error('Failed to fetch candle data:', error);
        } finally {
            setIsLoading(false);
        }
    }, [market]);

    useEffect(() => {
        // Initial fetch
        fetchCandles(timeUnit);

        // Interval for periodic fetch
        const intervalId = setInterval(() => {
            fetchCandles(timeUnit);
        }, 500000);

        // Cleanup interval on unmount or dependency change
        return () => clearInterval(intervalId);
    }, [fetchCandles, timeUnit]);


    if (isLoading || candles.length === 0) {
        return (
            <div
                style={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    height: '100vh',
                    backgroundColor: '#f8f9fa',
                }}
            >
                <Spin size="large" />
            </div>
        );
    }

    // 캔들 차트 데이터
    const chartData = candles.map((candle) => ({
        x: candle.candle_date_time_kst,
        y: [candle.opening_price, candle.high_price, candle.low_price, candle.trade_price],
    }));

    // 이동평균선 계산
    const calculateSMA = (period: number) => {
        return candles.map((_, index, array) => {
            const periodData = array.slice(Math.max(0, index - period + 1), index + 1); // 현재 데이터 포함
            const average = periodData.reduce((sum, data) => sum + data.trade_price, 0) / periodData.length; // 길이에 따른 평균
            return { x: array[index].candle_date_time_kst, y: average };
        });
    };

    const SMA20 = calculateSMA(20); // 20일 이동평균선

    // 거래량 데이터
    const volumeData = candles.map((candle) => ({
        x: candle.candle_date_time_kst,
        y: candle.candle_acc_trade_volume,
    }));

    const supportLevel = Math.min(...candles.map((candle) => candle.low_price));
    const resistanceLevel = Math.max(...candles.map((candle) => candle.high_price));

    // RSI 계산
    const calculateRSI = (period: number) => {
        const rsi: { x: string; y: number | null }[] = [];
        let emaGain = 0;
        let emaLoss = 0;

        for (let i = 0; i < candles.length; i++) {
            if (i === 0) {
                rsi.push({ x: candles[i].candle_date_time_kst, y: null });
                continue;
            }

            const change = candles[i].trade_price - candles[i - 1].trade_price;

            if (i < period) {
                if (change > 0) emaGain += change;
                else emaLoss += Math.abs(change);
                if (i === period - 1) {
                    emaGain /= period;
                    emaLoss /= period;
                }
                rsi.push({ x: candles[i].candle_date_time_kst, y: null });
                continue;
            }

            if (change > 0) {
                emaGain = (emaGain * (period - 1) + change) / period;
                emaLoss = (emaLoss * (period - 1)) / period;
            } else {
                emaGain = (emaGain * (period - 1)) / period;
                emaLoss = (emaLoss * (period - 1) + Math.abs(change)) / period;
            }

            const rs = emaLoss === 0 ? 100 : emaGain / emaLoss;
            const rsiValue = 100 - 100 / (1 + rs);
            rsi.push({ x: candles[i].candle_date_time_kst, y: rsiValue });
        }

        return rsi;
    };
    const RSI14 = calculateRSI(14); // 14일 RSI 계산

    const options: ApexOptions = {
        chart: {
            type: 'candlestick',
            height: 350,
            toolbar: { show: true },
        },
        title: {
            text: `${market} (${timeUnits.find((unit) => unit.value === timeUnit)?.label}) 캔들 차트`,
            align: 'center',
        },
        xaxis: {
            type: 'datetime',
            labels: {
                rotate: -45,
                style: { fontSize: '10px', colors: '#000' },
                formatter: (value) => {
                    const date = new Date(value);
                    return `${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`;
                },
            },
            tickAmount: 6, // 라벨 개수를 제한
        },
        yaxis: {
            tooltip: { enabled: true },
            labels: {
                style: { fontSize: '10px', colors: '#000' },
                formatter: (value: number) => `${value.toLocaleString()} 원`,
            },
        },
        tooltip: {
            shared: true,
            custom: ({ seriesIndex, dataPointIndex, w }) => {
                const dataPoint = w.config.series[seriesIndex]?.data[dataPointIndex];
                if (!dataPoint || !Array.isArray(dataPoint.y)) return '데이터 없음';
                const [open, high, low, close] = dataPoint.y;

                const date = new Date(dataPoint.x);
                const days = ['일', '월', '화', '수', '목', '금', '토']; // 요일 배열
                const year = date.getFullYear();
                const month = (date.getMonth() + 1).toString().padStart(2, '0');
                const day = date.getDate().toString().padStart(2, '0');
                const hours = date.getHours().toString().padStart(2, '0');
                const minutes = date.getMinutes().toString().padStart(2, '0');
                const dayOfWeek = days[date.getDay()]; // 요일 추출

                const formattedDate = `${year}-${month}-${day} ${hours}:${minutes} (${dayOfWeek})`;

                return `
                    <div style="padding: 10px; border-radius: 4px; background-color: #fff; color: #000;">
                        ${formattedDate}<br />
                        <strong>시가:</strong> ${open.toLocaleString('ko-KR')} 원<br />
                        <strong>고가:</strong> ${high.toLocaleString('ko-KR')} 원<br />
                        <strong>저가:</strong> ${low.toLocaleString('ko-KR')} 원<br />
                        <strong>종가:</strong> ${close.toLocaleString('ko-KR')} 원
                    </div>
                `;
            },
        },
        plotOptions: {
            candlestick: {
                colors: {
                    upward: '#DD3D44',
                    downward: '#1275EC',
                },
            },
        },
        annotations: {
            yaxis: [
                {
                    y: supportLevel,
                    borderColor: '#00E396',
                    label: {
                        text: `지지선: ${supportLevel.toLocaleString()} 원`,
                        style: { color: '#fff', background: '#00E396' },
                    },
                },
                {
                    y: resistanceLevel,
                    borderColor: '#FEB019',
                    label: {
                        text: `저항선: ${resistanceLevel.toLocaleString()} 원`,
                        style: { color: '#fff', background: '#FEB019' },
                    },
                },
            ],
        },
    };

    const volumeOptions: ApexOptions = {
        chart: {
            type: 'bar',
            height: 150,
            toolbar: { show: false },
        },
        xaxis: {
            type: 'datetime',
            labels: {
                rotate: -45,
                style: { fontSize: '10px', colors: '#000' },
                formatter: (value) => {
                    const date = new Date(value);
                    return `${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`;
                },
            },
            tickAmount: 6, // 라벨 개수를 제한
        },
        yaxis: {
            labels: {
                style: { fontSize: '10px', colors: '#000' },
                formatter: (value: number) => `${value.toLocaleString()}`,
            },
        },
        colors: ['#008FFB'],
        plotOptions: {
            bar: {
                columnWidth: '60%',
            },
        },
        dataLabels: {
            enabled: false, // 데이터 라벨 비활성화
        },
    };

    const rsiOptions: ApexOptions = {
        chart: {
            type: 'line',
            height: 150,
            toolbar: { show: false },
        },
        xaxis: {
            type: 'datetime',
            labels: {
                rotate: -45,
                style: { fontSize: '10px', colors: '#000' },
                formatter: (value) => {
                    const date = new Date(value);
                    return `${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`;
                },
            },
            tickAmount: 6, // 라벨 개수를 제한
        },
        yaxis: {
            max: 100,
            min: 0,
            labels: {
                style: { fontSize: '10px', colors: '#000' },
            },
        },
        tooltip: {
            shared: true,
        },
    };

    return (
        <div style={{ padding: '24px', backgroundColor: '#f8f9fa', height: '100vh' }}>
            <div style={{ marginBottom: '16px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h2>{market} 캔들 차트</h2>
                <Select value={timeUnit} onChange={(value) => setTimeUnit(value)} style={{ width: 150 }}>
                    {timeUnits.map((unit) => (
                        <Option key={unit.value} value={unit.value}>
                            {unit.label}
                        </Option>
                    ))}
                </Select>
            </div>
            <Chart
                options={options}
                series={[
                    { name: 'Candlestick', type: 'candlestick', data: chartData },
                    { name: '20-Day SMA', type: 'line', data: SMA20 },
                ]}
                type="candlestick"
                height={400}
            />
            <Chart
                options={rsiOptions}
                series={[{ name: 'RSI', type: 'line', data: RSI14 }]}
                type="line"
                height={150}
            />
            <Chart options={volumeOptions} series={[{ data: volumeData }]} type="bar" height={150} />
        </div>
    );
}
