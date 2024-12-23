'use client';

import React, { useEffect, useState, useRef } from 'react';
import { Space, Table, Typography } from 'antd';
import { ColumnsType } from 'antd/es/table';
import { useMarkets } from '@/app/MarketsContext';

interface TickerSocketData {
    type: string; // 데이터 타입 (ticker)
    code: string; // 마켓 코드 (ex. KRW-BTC)
    opening_price: number; // 시가
    high_price: number; // 고가
    low_price: number; // 저가
    trade_price: number; // 현재가
    prev_closing_price: number; // 전일 종가
    change: string; // 전일 대비 (RISE, EVEN, FALL)
    change_price: number; // 부호 없는 전일 대비 값
    signed_change_price: number; // 전일 대비 값
    change_rate: number; // 부호 없는 전일 대비 등락율
    signed_change_rate: number; // 전일 대비 등락율
    trade_volume: number; // 가장 최근 거래량
    acc_trade_volume: number; // 누적 거래량 (UTC 0시 기준)
    acc_trade_volume_24h: number; // 24시간 누적 거래량
    acc_trade_price: number; // 누적 거래대금 (UTC 0시 기준)
    acc_trade_price_24h: number; // 24시간 누적 거래대금
    trade_date: string; // 최근 거래 일자 (UTC)
    trade_time: string; // 최근 거래 시각 (UTC)
    trade_timestamp: number; // 체결 타임스탬프 (milliseconds)
    ask_bid: string; // 매수/매도 구분 (ASK, BID)
    acc_ask_volume: number; // 누적 매도량
    acc_bid_volume: number; // 누적 매수량
    highest_52_week_price?: number; // 52주 최고가
    highest_52_week_date?: string; // 52주 최고가 달성일 (yyyy-MM-dd)
    lowest_52_week_price?: number; // 52주 최저가
    lowest_52_week_date?: string; // 52주 최저가 달성일 (yyyy-MM-dd)
    market_state: string; // 거래 상태 (PREVIEW, ACTIVE, DELISTED)
    market_warning: string; // 유의 종목 여부 (NONE, CAUTION)
    timestamp: number; // 타임스탬프 (milliseconds)
    stream_type: string; // 스트림 타입 (SNAPSHOT, REALTIME)
}

const { Title } = Typography;

export default function UpbitTickerPage() {
    const [tickerData, setTickerData] = useState<TickerSocketData[]>([]);
    const [changedCells, setChangedCells] = useState<{ [key: string]: string }>({});
    const [selectedMarketType, setSelectedMarketType] = useState('KRW');
    const { markets, setMarkets } = useMarkets();

    useEffect(() => {
        const fetchMarkets = async () => {
            const response = await fetch('http://localhost:8080/api/v1/upbit/market?is_details=false');
            const data: { market: string }[] = await response.json();
            setMarkets(data.map(value => value.market).slice(0, 50));
        };

        if (markets.length === 0) {
            fetchMarkets();
        }

        const ws = new WebSocket(`ws://localhost:8080/ws/upbit?markets=${markets.join(',')}`);
        ws.onmessage = (event) => {
            const message: TickerSocketData = JSON.parse(event.data);
            setTickerData((prev) => {
                const index = prev.findIndex((item) => item.code === message.code);
                const updated = [...prev];
                if (index !== -1) {
                    detectChanges(updated[index], message);
                    updated[index] = message;
                } else {
                    updated.push(message);
                }
                return updated;
            });
        };

        return () => {
            if (ws) {
                ws.close();
                console.log('WebSocket connection closed');
            }
        };
    }, [markets]);

    const detectChanges = (prev: TickerSocketData, current: TickerSocketData) => {
        const changes: { [key: string]: string } = {};

        if (prev.trade_price !== current.trade_price) {
            changes[`${current.code}-trade_price`] = current.trade_price > prev.trade_price ? 'red' : 'blue';
        }
        if (prev.signed_change_price !== current.signed_change_price) {
            changes[`${current.code}-signed_change_price`] =
                current.signed_change_price > prev.signed_change_price ? 'red' : 'blue';
        }
        if (prev.signed_change_rate !== current.signed_change_rate) {
            changes[`${current.code}-signed_change_rate`] =
                current.signed_change_rate > prev.signed_change_rate ? 'red' : 'blue';
        }

        setChangedCells((prevChanges) => ({ ...prevChanges, ...changes }));

        setTimeout(() => {
            setChangedCells((prevChanges) => {
                const updatedChanges = { ...prevChanges };
                Object.keys(changes).forEach((key) => delete updatedChanges[key]);
                return updatedChanges;
            });
        }, 1000);
    };

    const filteredTickerData = tickerData.filter((data) => data.code.startsWith(selectedMarketType));

    const columns: ColumnsType<TickerSocketData> = [
        {
            title: 'Market',
            dataIndex: 'code',
            key: 'code',
            render: (code: string) => <strong>{code}</strong>,
        },
        {
            title: 'Current Price',
            dataIndex: 'trade_price',
            key: 'trade_price',
            align: 'right',
            render: (price: number, record) => (
                <span
                    className={changedCells[`${record.code}-trade_price`] ? 'cell-flash' : ''}
                    style={{
                        border: `2px solid ${changedCells[`${record.code}-trade_price`] || 'transparent'}`,
                        padding: '4px',
                        display: 'inline-block',
                    }}
                >
                    {price.toLocaleString('ko-KR', {
                        minimumFractionDigits: 0,
                        maximumFractionDigits: price.toString().split('.')[1]?.length || 0,
                    })} 원
                </span>
            ),
        },
        {
            title: 'Change (Price)',
            dataIndex: 'signed_change_price',
            key: 'signed_change_price',
            align: 'right',
            render: (change: number, record) => (
                <span
                    style={{
                        color: change > 0 ? 'red' : change < 0 ? 'blue' : 'gray',
                        border: `2px solid ${changedCells[`${record.code}-signed_change_price`] || 'transparent'}`,
                        padding: '4px',
                        display: 'inline-block',
                    }}
                >
                    {change > 0 ? '+' : ''}
                    {change.toLocaleString('ko-KR', {
                        minimumFractionDigits: 0,
                        maximumFractionDigits: change.toString().split('.')[1]?.length || 0,
                    })} 원
                </span>
            ),
        },
        {
            title: 'Change (Rate)',
            dataIndex: 'signed_change_rate',
            key: 'signed_change_rate',
            align: 'right',
            render: (rate: number, record) => (
                <span
                    style={{
                        color: rate > 0 ? 'red' : rate < 0 ? 'blue' : 'gray',
                        border: `2px solid ${changedCells[`${record.code}-signed_change_rate`] || 'transparent'}`,
                        padding: '4px',
                        display: 'inline-block',
                    }}
                >
                    {(rate * 100).toFixed(2)}%
                </span>
            ),
        },
        {
            title: '24h Volume',
            dataIndex: 'acc_trade_volume_24h',
            key: 'acc_trade_volume_24h',
            align: 'right',
            render: (volume: number) => <span>{volume.toLocaleString()} 개</span>,
        },
        {
            title: '24h Trade Value',
            dataIndex: 'acc_trade_price_24h',
            key: 'acc_trade_price_24h',
            align: 'right',
            render: (value: number) => <span>{value.toLocaleString()} 원</span>,
        },
    ];

    return (
        <div
            style={{
                padding: '24px',
                fontFamily: 'Arial, sans-serif',
                height: 'calc(100vh - 48px)',
                overflow: 'auto',
            }}
        >
            <Title level={2} style={{ textAlign: 'center', marginBottom: '24px' }}>
                Upbit Real-time Ticker
            </Title>
            <Space style={{ marginBottom: '16px', textAlign: 'center', width: '100%' }}>
                <button
                    onClick={() => setSelectedMarketType('KRW')}
                    style={{
                        padding: '8px 16px',
                        backgroundColor: selectedMarketType === 'KRW' ? '#1890ff' : '#f0f0f0',
                        color: selectedMarketType === 'KRW' ? '#fff' : '#000',
                        border: '1px solid #d9d9d9',
                        borderRadius: '4px',
                        cursor: 'pointer',
                    }}
                >
                    KRW
                </button>
                <button
                    onClick={() => setSelectedMarketType('BTC')}
                    style={{
                        padding: '8px 16px',
                        backgroundColor: selectedMarketType === 'BTC' ? '#1890ff' : '#f0f0f0',
                        color: selectedMarketType === 'BTC' ? '#fff' : '#000',
                        border: '1px solid #d9d9d9',
                        borderRadius: '4px',
                        cursor: 'pointer',
                    }}
                >
                    BTC
                </button>
                <button
                    onClick={() => setSelectedMarketType('USDT')}
                    style={{
                        padding: '8px 16px',
                        backgroundColor: selectedMarketType === 'USDT' ? '#1890ff' : '#f0f0f0',
                        color: selectedMarketType === 'USDT' ? '#fff' : '#000',
                        border: '1px solid #d9d9d9',
                        borderRadius: '4px',
                        cursor: 'pointer',
                    }}
                >
                    USDT
                </button>
            </Space>
            <Table
                dataSource={filteredTickerData}
                columns={columns}
                pagination={false}
                rowKey="code"
                bordered
                style={{ backgroundColor: '#fff' }}
            />
        </div>
    );
}
