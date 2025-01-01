'use client';

import React, { useEffect, useState } from 'react';
import {v4 as uuidv4} from "uuid";

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
    level: number;
}

export default function OrderbookCustomComponent() {
    const [orderbook, setOrderbook] = useState<OrderbookData | null>(null);

    useEffect(() => {
        const ws = new WebSocket('wss://api.upbit.com/websocket/v1');

        ws.onopen = () => {
            console.log("WebSocket connected.");
            const ticket = uuidv4();
            const request = [
                { ticket },
                {
                    type: "orderbook",
                    codes: ["KRW-BTC"]
                },
                { format: "DEFAULT" }
            ];
            ws.send(JSON.stringify(request));
        };

        ws.onmessage = async (event) => {
            const blob = event.data;
            try {
                if (blob instanceof Blob) {
                    const text = await blob.text();
                    const data: OrderbookData = JSON.parse(text);
                    setOrderbook(data);
                } else {
                    console.error("Unexpected message format:", blob);
                }
            } catch (error) {
                console.error("Error processing WebSocket message:", error);
            }
        };

        ws.onerror = (error) => {
            console.error("WebSocket error:", error);
        };

        return () => {
            if (ws) {
                ws.close();
                console.log("WebSocket disconnected.");
            }
        };
    }, []);

    if (!orderbook) return <div>Loading...</div>;

    const maxAskSize = Math.max(...orderbook.orderbook_units.map((unit) => unit.ask_size));
    const maxBidSize = Math.max(...orderbook.orderbook_units.map((unit) => unit.bid_size));

    return (
        <div style={{ padding: '24px', fontFamily: 'Arial, sans-serif', height: '100vh', overflow: 'auto' }}>
            {/* 요약 정보 */}
            <div style={{ textAlign: 'center', marginBottom: '16px' }}>
                <h2>KRW-BTC 호가 정보</h2>
                <p>
                    <strong>총 매도잔량:</strong>{' '}
                    <span style={{ color: '#1275EC', fontSize: '18px' }}>
                        {orderbook.total_ask_size.toFixed(3)} BTC
                    </span>
                </p>
                <p>
                    <strong>총 매수잔량:</strong>{' '}
                    <span style={{ color: '#DD3D44', fontSize: '18px' }}>
                        {orderbook.total_bid_size.toFixed(3)} BTC
                    </span>
                </p>
            </div>

            {/* 좌우 대칭 UI */}
            <div style={{ display: 'flex', gap: '16px' }}>
                {/* 매도 테이블 */}
                <div style={{ flex: 1 }}>
                    <h3 style={{ textAlign: 'right', color: '#1275EC', marginBottom: '8px' }}>매도호가</h3>
                    {orderbook.orderbook_units
                        .sort((a, b) => b.ask_price - a.ask_price)
                        .map((unit, index) => (
                            <div
                                key={index}
                                style={{
                                    display: 'flex',
                                    justifyContent: 'flex-end',
                                    alignItems: 'center',
                                    marginBottom: '4px',
                                }}
                            >
                                {/* 가격 */}
                                <div style={{ width: '30%', textAlign: 'right', marginRight: '8px' }}>
                                    <span style={{ fontWeight: 'bold' }}>{unit.ask_price.toLocaleString()} 원</span>
                                </div>

                                {/* 잔량 막대 */}
                                <div
                                    style={{
                                        width: '70%',
                                        display: 'flex',
                                        justifyContent: 'flex-end',
                                        alignItems: 'center',
                                        position: 'relative',
                                    }}
                                >
                                    {/* 막대 */}
                                    <div
                                        style={{
                                            width: `${(unit.ask_size / maxAskSize) * 100}%`,
                                            backgroundColor: '#7AA5E5', // 연한 파란색
                                            height: '20px',
                                            borderRadius: '4px',
                                            opacity: 0.8,
                                            position: 'relative',
                                        }}
                                    ></div>

                                    {/* 텍스트 */}
                                    <span
                                        style={{
                                            position: 'absolute',
                                            right: '4px',
                                            zIndex: 1,
                                            color: '#000000', // 검정색으로 고정
                                            fontSize: '12px',
                                            fontWeight: 'bold',
                                        }}
                                    >
                                        {unit.ask_size.toFixed(3)}
                                    </span>
                                </div>
                            </div>
                        ))}
                </div>

                {/* 매수 테이블 */}
                <div style={{ flex: 1 }}>
                    <h3 style={{ textAlign: 'left', color: '#DD3D44', marginBottom: '8px' }}>매수호가</h3>
                    {orderbook.orderbook_units
                        .sort((a, b) => b.bid_price - a.bid_price)
                        .map((unit, index) => (
                            <div
                                key={index}
                                style={{
                                    display: 'flex',
                                    justifyContent: 'flex-start',
                                    alignItems: 'center',
                                    marginBottom: '4px',
                                }}
                            >
                                {/* 잔량 막대 */}
                                <div
                                    style={{
                                        width: '70%',
                                        display: 'flex',
                                        justifyContent: 'flex-start',
                                        alignItems: 'center',
                                        position: 'relative',
                                    }}
                                >
                                    {/* 막대 */}
                                    <div
                                        style={{
                                            width: `${(unit.bid_size / maxBidSize) * 100}%`,
                                            backgroundColor: '#E57373', // 연한 빨간색
                                            height: '20px',
                                            borderRadius: '4px',
                                            opacity: 0.8,
                                            position: 'relative',
                                        }}
                                    ></div>

                                    {/* 텍스트 */}
                                    <span
                                        style={{
                                            position: 'absolute',
                                            left: '4px',
                                            zIndex: 1,
                                            color: '#000000', // 검정색으로 고정
                                            fontSize: '12px',
                                            fontWeight: 'bold',
                                        }}
                                    >
                                        {unit.bid_size.toFixed(3)}
                                    </span>
                                </div>

                                {/* 가격 */}
                                <div style={{ width: '30%', textAlign: 'left', marginLeft: '8px' }}>
                                    <span style={{ fontWeight: 'bold' }}>{unit.bid_price.toLocaleString()} 원</span>
                                </div>
                            </div>
                        ))}
                </div>
            </div>
        </div>
    );
}
