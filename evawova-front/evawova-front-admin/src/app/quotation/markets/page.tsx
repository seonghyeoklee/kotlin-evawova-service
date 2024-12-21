'use client';

import React, { useEffect, useState } from 'react';
import { Table, Tag, Typography, Space } from 'antd';

const { Title } = Typography;

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

    const cautionKeyMap: Record<string, string> = {
        PRICE_FLUCTUATIONS: '가격 급등락 경보 발령 여부',
        TRADING_VOLUME_SOARING: '거래량 급등 경보 발령 여부',
        DEPOSIT_AMOUNT_SOARING: '입금량 급등 경보 발령 여부',
        GLOBAL_PRICE_DIFFERENCES: '가격 차이 경보 발령 여부',
        CONCENTRATION_OF_SMALL_ACCOUNTS: '소수 계정 집중 경보 발령 여부',
    };

    // 테이블 열 정의
    const columns = [
        {
            title: '업비트에서 제공중인 시장 정보',
            dataIndex: 'market',
            key: 'market',
            render: (text: string) => (
                <span
                    style={{
                        color: text.startsWith('KRW') ? 'green' : 'blue',
                        fontWeight: 'bold',
                    }}
                >
                    {text}
                </span>
            ),
        },
        {
            title: '거래 대상 디지털 자산 한글명',
            dataIndex: 'korean_name',
            key: 'korean_name',
        },
        {
            title: '거래 대상 디지털 자산 영문명',
            dataIndex: 'english_name',
            key: 'english_name',
        },
        {
            title: (
                <span>
                    업비트 시장경보
                    <span style={{ color: 'red', fontWeight: 'bold' }}>
                        [유의종목]
                    </span>
                </span>
            ),
            key: 'warning',
            dataIndex: ['market_event', 'warning'],
            render: (warning: boolean) =>
                warning ? <Tag color="orange">주의</Tag> : <Tag color="green">안전</Tag>,
        },
        {
            title: (
                <span>
            업비트 시장경보
            <span style={{ color: 'orange', fontWeight: 'bold' }}>
                [주의종목]
            </span>
        </span>
            ),
            key: 'caution',
            render: (_: any, record: MarketData) => {
                const { caution } = record.market_event;

                const getTagColor = (value: boolean) => (value ? 'red' : 'green');

                return (
                    <Space wrap>
                        {Object.entries(caution).map(([key, value]) => (
                            <Tag
                                color={getTagColor(value)}
                                key={key}
                                style={{ marginBottom: '2px' }}
                            >
                                {cautionKeyMap[key]}
                            </Tag>
                        ))}
                    </Space>
                );
            },
        },
    ];

    return (
        <div
            style={{
                padding: '24px',
                background: '#fff',
                height: 'calc(100vh - 50px)',
                overflow: 'auto',
            }}
        >
            <Title level={2}>종목 코드 조회</Title>

            {/* 주의사항 영역 */}
            <div
                style={{
                    background: '#f9f9f9',
                    padding: '16px',
                    borderRadius: '8px',
                    textAlign: 'left',
                    marginBottom: 24
                }}
            >
                <Typography.Text>
                    <ul style={{listStyleType: 'disc', margin: 0}}>
                        <li>
                            주의 경보에 대한 구분은 관련{' '}
                            <a href="https://upbit.com/service_center/notice?id=3482" target="_blank"
                               rel="noopener noreferrer">
                                공지사항
                            </a>
                            을 참고하시기 바랍니다.
                        </li>
                        <li>
                            주의 경보 타입은 아래와 같으며, 자세한 정보는 관련{' '}
                            <a href="https://upbit.com/service_center/notice?id=3606" target="_blank"
                               rel="noopener noreferrer">
                                공지사항
                            </a>
                            을 참고하시기 바랍니다.
                        </li>
                    </ul>
                </Typography.Text>
            </div>

            {/* 테이블 영역 */}
            <div style={{minWidth: 800, paddingBottom: 24}}>
                <Table
                    columns={columns}
                    dataSource={data}
                    rowKey="market"
                    bordered
                    loading={loading}
                />
            </div>
        </div>
    );
}
