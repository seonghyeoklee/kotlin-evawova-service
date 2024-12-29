'use client';

import React, { useEffect, useState } from 'react';
import dynamic from 'next/dynamic';
import { Select, Spin } from 'antd'; // 로딩 상태를 위한 Spin 추가
import { ApexOptions } from 'apexcharts'; // ApexOptions 타입 추가

const { Option } = Select;

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

const timeUnits = [
    { label: '초', value: 'seconds' },
    { label: '분', value: 'minutes' },
    { label: '일', value: 'days' },
    { label: '주', value: 'weeks' },
    { label: '월', value: 'months' },
    { label: '년', value: 'years' },
];

export default function CandleChartPage() {
    const [candles, setCandles] = useState<CandleData[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [timeUnit, setTimeUnit] = useState('minutes');
    const [market] = useState('KRW-BTC');
    const [error, setError] = useState<string | null>(null); // 에러 메시지 추가

    const fetchCandles = async (unit: string) => {
        try {
            setIsLoading(true);
            setError(null); // 에러 초기화
            const response = await fetch(`http://localhost:8080/api/v1/upbit/candles/${unit}?market=${market}&count=200`);
            const data = await response.json();

            if (Array.isArray(data)) {
                setCandles(data);
            } else {
                throw new Error('Unexpected API response format');
            }
        } catch (error) {
            console.error('Failed to fetch candle data:', error);
            setError('데이터를 가져오는 데 실패했습니다. 다시 시도해주세요.');
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchCandles(timeUnit);
    }, [timeUnit]);

    const chartData = candles.map((candle) => ({
        x: candle.candle_date_time_kst,
        y: [candle.opening_price, candle.high_price, candle.low_price, candle.trade_price],
    }));

    const options: ApexOptions = {
        chart: {
            type: 'candlestick',
            height: 350,
        },
        title: {
            text: `${market} (${timeUnits.find((unit) => unit.value === timeUnit)?.label}) 캔들 차트`,
            align: 'center',
        },
        xaxis: {
            type: 'datetime',
            labels: {
                rotate: -45,
                style: {
                    fontSize: '10px',
                },
                formatter: (value) => {
                    const date = new Date(value);
                    const days = ['일', '월', '화', '수', '목', '금', '토']; // 요일 배열
                    const year = date.getFullYear();
                    const month = (date.getMonth() + 1).toString().padStart(2, '0');
                    const day = date.getDate().toString().padStart(2, '0');
                    const hours = date.getHours().toString().padStart(2, '0');
                    const minutes = date.getMinutes().toString().padStart(2, '0');
                    const dayOfWeek = days[date.getDay()]; // 요일 추출

                    return `${year}-${month}-${day} ${hours}:${minutes} (${dayOfWeek})`;
                },
            },
            tickAmount: 10,
        },
        yaxis: {
            labels: {
                formatter: (value: number) =>
                    `${value.toLocaleString('ko-KR', {
                        maximumFractionDigits: 0,
                    })} 원`,
            },
            tooltip: {
                enabled: true,
            },
        },
        tooltip: {
            shared: true,
            custom: ({ series, seriesIndex, dataPointIndex, w }) => {
                const dataPoint = w.config.series[seriesIndex]?.data?.[dataPointIndex];

                if (!dataPoint || !Array.isArray(dataPoint.y)) {
                    return `
                        <div style="padding: 10px; border-radius: 4px; background-color: #fff; color: #000;">
                            <strong>데이터 없음</strong>
                        </div>
                    `;
                }
                // `dataPoint.y`는 배열 [open, high, low, close]
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
        responsive: [
            {
                breakpoint: 768,
                options: {
                    chart: {
                        height: 300,
                    },
                    xaxis: {
                        labels: {
                            rotate: -30,
                            style: {
                                fontSize: '8px',
                            },
                        },
                    },
                },
            },
        ],
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
            <div style={{ marginBottom: '16px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h2 style={{ margin: 0 }}>KRW-BTC 캔들 차트</h2>
                <Select
                    value={timeUnit}
                    onChange={(value) => setTimeUnit(value)}
                    style={{ width: 150 }}
                >
                    {timeUnits.map((unit) => (
                        <Option key={unit.value} value={unit.value}>
                            {unit.label}
                        </Option>
                    ))}
                </Select>
            </div>
            <Chart
                options={options}
                series={[{ data: chartData }]}
                type="candlestick"
                height={500}
            />
        </div>
    );
}
