'use client';

import React, { useEffect, useState } from 'react';
import { Table, Typography } from 'antd';
import { ColumnsType } from 'antd/es/table';
import dayjs from 'dayjs'; // 날짜/시간 변환 라이브러리
import { v4 as uuidv4 } from 'uuid';


const { Title } = Typography;

interface TradeData {
    type: string;
    code: string;
    timestamp: number;
    trade_date: string;
    trade_time: string;
    trade_timestamp: number;
    trade_price: number;
    trade_volume: number;
    ask_bid: string;
    prev_closing_price: number;
    change: string;
    change_price: number;
    sequential_id: number;
    best_ask_price: number;
    best_ask_size: number;
    best_bid_price: number;
    best_bid_size: number;
    stream_type: string;
}

export default function TradePage() {
    const [trades, setTrades] = useState<TradeData[]>([]);

    useEffect(() => {
        const ws = new WebSocket('wss://api.upbit.com/websocket/v1');

        ws.onopen = () => {
            const ticket = uuidv4();
            const request = [
                { ticket: ticket },
                {
                    type: "trade",
                    codes: ["KRW-BTC"]
                },
                { format: "DEFAULT" }
            ];
            ws.send(JSON.stringify(request));
        };

        ws.onmessage = async (event) => {
            const blob = event.data;
            if (blob instanceof Blob) {
                const text = await blob.text();
                const trade: TradeData = JSON.parse(text);

                setTrades((prevTrades) => [trade, ...prevTrades.slice(0, 99)]);
            } else {
                console.error("Unexpected message format:", blob);
            }
        };

        ws.onerror = (error) => {
            console.error("WebSocket error:", error);
        };

        return () => {
            if (ws) {
                ws.close();
                console.log('WebSocket connection closed');
            }
        };
    }, []);

    const columns: ColumnsType<TradeData> = [
        {
            title: '시간',
            dataIndex: 'trade_timestamp',
            key: 'trade_timestamp',
            align: 'right',
            render: (timestamp: number) => (
                <span style={{ fontWeight: 'bold' }}>
                    {dayjs(timestamp).format('YYYY-MM-DD HH:mm:ss')}
                </span>
            ),
        },
        {
            title: '종목 코드',
            dataIndex: 'code',
            key: 'code',
            align: 'center',
            render: (code: string) => <span>{code}</span>,
        },
        {
            title: '체결가 (원)',
            dataIndex: 'trade_price',
            key: 'trade_price',
            align: 'right',
            render: (price: number, record) => (
                <span
                    style={{
                        color: record.ask_bid === 'ASK' ? '#1275EC' : '#DD3D44', // 매도: 파랑, 매수: 빨강
                        fontWeight: 'bold',
                    }}
                >
                    {price.toLocaleString()} 원
                </span>
            ),
        },
        {
            title: '체결량',
            dataIndex: 'trade_volume',
            key: 'trade_volume',
            align: 'right',
            render: (volume: number) => (
                <span style={{ fontWeight: 'bold' }}>{volume.toFixed(8)}</span>
            ),
        },
        {
            title: '구분',
            dataIndex: 'ask_bid',
            key: 'ask_bid',
            align: 'center',
            render: (askBid: string) => (
                <span style={{ color: askBid === 'ASK' ? '#1275EC' : '#DD3D44', fontWeight: 'bold' }}>
                    {askBid === 'ASK' ? '매도' : '매수'}
                </span>
            ),
        },
        {
            title: '변동 (원)',
            dataIndex: 'change_price',
            key: 'change_price',
            align: 'right',
            render: (change: number) => (
                <span style={{ color: change > 0 ? '#DD3D44' : '#1275EC', fontWeight: 'bold' }}>
                    {change > 0 ? '+' : ''}
                    {change.toLocaleString()}
                </span>
            ),
        },
        {
            title: '전일 종가 (원)',
            dataIndex: 'prev_closing_price',
            key: 'prev_closing_price',
            align: 'right',
            render: (price: number) => `${price.toLocaleString()} 원`,
        },
        {
            title: '최우선 매도 호가',
            dataIndex: 'best_ask_price',
            key: 'best_ask_price',
            align: 'right',
            render: (price: number) => `${price.toLocaleString()} 원`,
        },
        {
            title: '최우선 매도 잔량',
            dataIndex: 'best_ask_size',
            key: 'best_ask_size',
            align: 'right',
            render: (size: number) => size.toFixed(8),
        },
        {
            title: '최우선 매수 호가',
            dataIndex: 'best_bid_price',
            key: 'best_bid_price',
            align: 'right',
            render: (price: number) => `${price.toLocaleString()} 원`,
        },
        {
            title: '최우선 매수 잔량',
            dataIndex: 'best_bid_size',
            key: 'best_bid_size',
            align: 'right',
            render: (size: number) => size.toFixed(8),
        }
    ];

    return (
        <div
            style={{
                padding: '24px',
                fontFamily: 'Arial, sans-serif',
                height: '100vh',
                overflow: 'auto',
                backgroundColor: '#f8f9fa',
            }}
        >
            <Title level={2} style={{ textAlign: 'center', marginBottom: '16px' }}>
                체결 내역 (KRW-BTC)
            </Title>
            <Table
                dataSource={trades}
                columns={columns}
                pagination={false}
                bordered
                size="small"
                style={{ backgroundColor: '#fff' }}
            />
        </div>
    );
}
