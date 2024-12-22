'use client';

import React, { useEffect, useState } from 'react';
import { Table, Tag, Typography } from 'antd';

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

    useEffect(() => {
        const ws = new WebSocket('ws://localhost:8080/ws/upbit');

        ws.onmessage = (event) => {
            const message: TickerSocketData = JSON.parse(event.data);
            console.log('Message received:', message);

            setTickerData((prev) => {
                const index = prev.findIndex((item) => item.code === message.code);
                if (index !== -1) {
                    const updated = [...prev];
                    updated[index] = message;
                    return updated;
                } else {
                    return [message, ...prev];
                }
            });
        };

        return () => ws.close();
    }, []);

    const columns = [
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
            render: (price: number) => <span>{price.toLocaleString()} 원</span>,
        },
        {
            title: 'Change (Price)',
            dataIndex: 'signed_change_price',
            key: 'signed_change_price',
            align: 'right',
            render: (change: number) => (
                <span
                    style={{
                        color: change > 0 ? 'red' : change < 0 ? 'blue' : 'gray',
                        fontWeight: 'bold',
                    }}
                >
                    {change > 0 ? '+' : ''}
                    {change.toLocaleString()} 원
                </span>
            ),
        },
        {
            title: 'Change (Rate)',
            dataIndex: 'signed_change_rate',
            key: 'signed_change_rate',
            align: 'right',
            render: (rate: number) => (
                <span
                    style={{
                        color: rate > 0 ? 'red' : rate < 0 ? 'blue' : 'gray',
                        fontWeight: 'bold',
                    }}
                >
                    {(rate * 100).toFixed(2)}%
                </span>
            ),
        },
        {
            title: 'High Price',
            dataIndex: 'high_price',
            key: 'high_price',
            align: 'right',
            render: (price: number) => <span>{price.toLocaleString()} 원</span>,
        },
        {
            title: 'Low Price',
            dataIndex: 'low_price',
            key: 'low_price',
            align: 'right',
            render: (price: number) => <span>{price.toLocaleString()} 원</span>,
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
        <div style={{ padding: '24px', fontFamily: 'Arial, sans-serif' }}>
            <Title level={2} style={{ textAlign: 'center', marginBottom: '24px' }}>
                Upbit Real-time Ticker
            </Title>
            <Table
                dataSource={tickerData}
                columns={columns}
                pagination={false}
                rowKey="code"
                bordered
                style={{ backgroundColor: '#fff' }}
            />
        </div>
    );
}
