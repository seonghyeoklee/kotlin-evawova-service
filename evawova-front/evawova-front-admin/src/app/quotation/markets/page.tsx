'use client';

import React, {useEffect, useState} from 'react';
import {Table, Tag, Typography, Space} from 'antd';

// API 데이터 형식 정의
interface MarketEvent {
    warning: boolean;
    caution: {
        PRICE_FLUCTUATIONS: boolean;
        TRADING_VOLUME_SOARING: boolean;
        DEPOSIT_AMOUNT_SOARING: boolean;
        GLOBAL_PRICE_DIFFERENCES: boolean;
        CONCENTRATION_OF_SMALL_ACCOUNTS: boolean;
    };
}

interface MarketData {
    market: string;
    korean_name: string;
    english_name: string;
    market_event: MarketEvent;
}

export default function MarketsPage() {
    const [data, setData] = useState<MarketData[]>([]);
    const [loading, setLoading] = useState(true);

    // API 데이터 Fetch
    useEffect(() => {
        async function fetchMarkets() {
            try {
                const response = await fetch('http://localhost:8080/api/v1/upbit/market');
                const jsonData: MarketData[] = await response.json();
                setData(jsonData);
            } catch (error) {
                console.error('Error fetching market data:', error);
            } finally {
                setLoading(false);
            }
        }

        fetchMarkets();
    }, []);

    // 테이블 열 정의
    const columns = [
        {
            title: 'Market',
            dataIndex: 'market',
            key: 'market',
            render: (text: string) => <b>{text}</b>, // Bold로 Market 표시
        },
        {
            title: 'Korean Name',
            dataIndex: 'korean_name',
            key: 'korean_name',
        },
        {
            title: 'English Name',
            dataIndex: 'english_name',
            key: 'english_name',
        },
        {
            title: 'Warning',
            key: 'warning',
            dataIndex: ['market_event', 'warning'],
            render: (warning: boolean) =>
                warning ? <Tag color="red">Warning</Tag> : <Tag color="green">Safe</Tag>,
        },
        {
            title: 'Cautions',
            key: 'caution',
            render: (_: any, record: MarketData) => {
                const {caution} = record.market_event;
                const cautionTags = Object.entries(caution).map(([key, value]) =>
                    value ? (
                        <Tag color="orange" key={key}>
                            {key.replace(/_/g, ' ')}
                        </Tag>
                    ) : null
                );
                return <Space>{cautionTags}</Space>;
            },
        },
    ];

    return (
        <div style={{padding: '24px', background: '#fff'}}>
            <Typography.Title level={2}>종목 코드 조회</Typography.Title>
            <Table
                columns={columns}
                dataSource={data}
                loading={loading}
                rowKey="market"
                bordered={true}
            />
        </div>
    );
}