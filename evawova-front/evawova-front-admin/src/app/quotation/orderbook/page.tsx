'use client';

import React, { useEffect, useState } from 'react';
import { Table, Typography, Card } from 'antd';
import {ColumnsType} from "antd/es/table";

const { Title } = Typography;

interface OrderbookUnit {
    ask_price: number;
    bid_price: number;
    ask_size: number;
    bid_size: number;
}

interface OrderbookData {
    type: string;
    code: string;
    timestamp: number;
    total_ask_size: number;
    total_bid_size: number;
    orderbook_units: OrderbookUnit[];
    stream_type: string;
}

export default function OrderbookPage() {
    const [orderbook, setOrderbook] = useState<OrderbookData | null>(null);

    useEffect(() => {
        const ws = new WebSocket('ws://localhost:8080/ws/upbit/orderbook?markets=KRW-BTC');

        ws.onmessage = (event) => {
            const data: OrderbookData = JSON.parse(event.data);
            setOrderbook(data);
        };

        return () => {
            ws.close();
        };
    }, []);

    // 매도 테이블 컬럼
    const askColumns: ColumnsType<OrderbookData> = [
        {
            title: '매도잔량',
            dataIndex: 'ask_size',
            key: 'ask_size',
            align: 'right',
            render: (size: number) => <span>{size.toFixed(3)}</span>,
        },
        {
            title: '매도호가',
            dataIndex: 'ask_price',
            key: 'ask_price',
            align: 'right',
            render: (price: number) => (
                <span
                    style={{
                        backgroundColor: '#1275EC', // 매도호가 배경색
                        color: '#FFFFFF', // 글씨 색상
                        padding: '4px 8px',
                        display: 'inline-block',
                        borderRadius: '4px',
                    }}
                >
                    {price.toLocaleString()} 원
                </span>
            ),
        },
        {
            title: '매수호가',
            dataIndex: 'bid_price',
            key: 'bid_price',
            align: 'right',
            render: () => null, // 매도 테이블에서는 매수호가 비워둠
        },
        {
            title: '매수잔량',
            dataIndex: 'bid_size',
            key: 'bid_size',
            align: 'right',
            render: () => null, // 매도 테이블에서는 매수잔량 비워둠
        },
    ];

    // 매수 테이블 컬럼
    const bidColumns: ColumnsType<OrderbookData> = [
        {
            title: '매도잔량',
            dataIndex: 'ask_size',
            key: 'ask_size',
            align: 'right',
            render: () => null, // 매수 테이블에서는 매도잔량 비워둠
        },
        {
            title: '매도호가',
            dataIndex: 'ask_price',
            key: 'ask_price',
            align: 'right',
            render: () => null, // 매수 테이블에서는 매도호가 비워둠
        },
        {
            title: '매수호가',
            dataIndex: 'bid_price',
            key: 'bid_price',
            align: 'right',
            render: (price: number) => (
                <span
                    style={{
                        backgroundColor: '#DD3D44', // 매수호가 배경색
                        color: '#FFFFFF', // 글씨 색상
                        padding: '4px 8px',
                        display: 'inline-block',
                        borderRadius: '4px',
                    }}
                >
                    {price.toLocaleString()} 원
                </span>
            ),
        },
        {
            title: '매수잔량',
            dataIndex: 'bid_size',
            key: 'bid_size',
            align: 'right',
            render: (size: number) => <span>{size.toFixed(3)}</span>,
        },
    ];

    // 매도 데이터: 높은 가격순 정렬
    const askData =
        orderbook?.orderbook_units
            .map((unit, index) => ({
                key: index,
                ask_size: unit.ask_size,
                ask_price: unit.ask_price,
                bid_price: null,
                bid_size: null,
            }))
            .sort((a, b) => b.ask_price - a.ask_price) || []; // 높은 가격순 정렬

    // 매수 데이터: 낮은 가격순 정렬 (기본)
    const bidData =
        orderbook?.orderbook_units
            .map((unit, index) => ({
                key: index,
                ask_size: null,
                ask_price: null,
                bid_price: unit.bid_price,
                bid_size: unit.bid_size,
            })) || [];

    return (
        <div
            style={{
                padding: '24px',
                fontFamily: 'Arial, sans-serif',
                height: 'calc(100vh - 48px)',
                overflow: 'auto',
            }}
        >
            <Card style={{marginBottom: '16px'}}>
                <Title level={4} style={{marginBottom: '0'}}>
                    호가 정보
                </Title>
                {orderbook ? (
                    <div>
                        <p>종목 코드: {orderbook.code}</p>
                        <p>총 매도잔량: {orderbook.total_ask_size.toFixed(3)} BTC</p>
                        <p>총 매수잔량: {orderbook.total_bid_size.toFixed(3)} BTC</p>
                    </div>
                ) : (
                    <p>Loading...</p>
                )}
            </Card>

            {/* 매도 테이블 */}
            <Table
                title={() => (
                    <Title level={5} style={{textAlign: 'center', margin: 0}}>
                        매도호가
                    </Title>
                )}
                dataSource={askData}
                columns={askColumns}
                pagination={false}
                bordered
                style={{marginBottom: '24px', backgroundColor: '#fff'}}
            />

            {/* 매수 테이블 */}
            <Table
                title={() => (
                    <Title level={5} style={{textAlign: 'center', margin: 0}}>
                        매수호가
                    </Title>
                )}
                dataSource={bidData}
                columns={bidColumns}
                pagination={false}
                bordered
                style={{backgroundColor: '#fff'}}
            />
        </div>
    );
}
