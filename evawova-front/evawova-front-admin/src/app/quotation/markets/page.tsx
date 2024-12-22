'use client';

import React, { useState } from 'react';
import {Input, Space, Table, Tag, Typography} from 'antd';
import useFetchMarkets from './useFetchMarkets';

const { Title } = Typography;

interface MarketEvent {
    warning: boolean;
    caution: Record<string, boolean>;
}

interface MarketData {
    market: string;
    korean_name: string;
    english_name: string;
    market_event: MarketEvent;
}

export default function MarketsPage() {
    const [searchValue, setSearchValue] = useState('');
    const [isComposing, setIsComposing] = useState(false);
    const { data, filteredData, setFilteredData, loading, error } = useFetchMarkets();

    const handleSearch = (value: string) => {
        const lowercasedValue = value.toLowerCase();
        const filtered = data.filter(
            (item) =>
                item.market.toLowerCase().includes(lowercasedValue) ||
                item.korean_name.toLowerCase().includes(lowercasedValue) ||
                item.english_name.toLowerCase().includes(lowercasedValue)
        );
        setFilteredData(filtered);
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setSearchValue(e.target.value);
        if (!isComposing) {
            handleSearch(e.target.value);
        }
    };

    const handleCompositionStart = () => {
        setIsComposing(true);
    };

    const handleCompositionEnd = (e: React.CompositionEvent<HTMLInputElement>) => {
        setIsComposing(false);
        handleSearch(e.currentTarget.value);
    };

    const cautionKeyMap: Record<string, string> = {
        PRICE_FLUCTUATIONS: '가격 급등락 경보',
        TRADING_VOLUME_SOARING: '거래량 급등 경보',
        DEPOSIT_AMOUNT_SOARING: '입금량 급등 경보',
        GLOBAL_PRICE_DIFFERENCES: '가격 차이 경보',
        CONCENTRATION_OF_SMALL_ACCOUNTS: '소수 계정 집중 경보',
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
                    <span style={{ color: 'orange', fontWeight: 'bold' }}>
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
                    <span style={{ color: 'red', fontWeight: 'bold' }}>
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

            {/* 주의 사항 */}
            <div
                style={{
                    background: '#f9f9f9',
                    padding: '16px',
                    borderRadius: '8px',
                    textAlign: 'left',
                    marginBottom: 24,
                }}
            >
                <Typography.Text>
                    <ul style={{listStyleType: 'disc', margin: 0}}>
                        <li>
                            <span style={{color: 'orange', fontWeight: 'bold'}}>[유의]</span>
                            <span style={{color: 'red', fontWeight: 'bold'}}>[주의]</span>
                            경보에 대한 구분은 관련{' '}
                            <a
                                href="https://upbit.com/service_center/notice?id=3482"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                공지사항
                            </a>
                            을 참고하시기 바랍니다.
                        </li>
                        <li>
                            <span style={{color: 'red', fontWeight: 'bold'}}>[주의]</span>
                            경보 타입의 자세한 정보는 관련{' '}
                            <a
                                href="https://upbit.com/service_center/notice?id=3606"
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                공지사항
                            </a>
                            을 참고하시기 바랍니다.
                        </li>
                    </ul>
                </Typography.Text>
            </div>

            {/* 검색 */}
            <div style={{marginBottom: 24}}>
                <Input
                    placeholder="시장 코드, 한글명, 영문명으로 검색"
                    value={searchValue}
                    onChange={handleInputChange}
                    onCompositionStart={handleCompositionStart}
                    onCompositionEnd={handleCompositionEnd}
                    style={{width: 300}}
                />
            </div>

            {/* 테이블 */}
            <Table columns={columns} dataSource={filteredData} rowKey="market" bordered loading={loading}/>
        </div>
    );
}
