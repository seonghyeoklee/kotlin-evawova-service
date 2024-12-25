import React from 'react';
import { Row, Col, Card } from 'antd';

const ExchangePage = () => {
    return (
        <div style={{ padding: '24px', backgroundColor: '#f0f2f5', height: '100vh' }}>
            <Row gutter={[16, 16]}>
                {/* 헤더 영역 */}
                <Col span={24}>
                    <Card>
                        <h2>KRW-BTC</h2>
                        <p>Current Price: <strong>₩25,000,000</strong></p>
                        <p>Change: <span style={{ color: 'red' }}>+3.21%</span></p>
                    </Card>
                </Col>

                {/* 메인 콘텐츠 영역 */}
                <Col span={16}>
                    <Card title="Live Chart">
                        {/* 차트 라이브러리 삽입 */}
                        <div>Chart Placeholder</div>
                    </Card>
                </Col>

                <Col span={8}>
                    <Card title="Order Book">
                        {/* 호가창 데이터 삽입 */}
                        <div>Order Book Placeholder</div>
                    </Card>
                </Col>

                {/* 하단 영역 */}
                <Col span={24}>
                    <Card title="Recent Trades">
                        {/* 체결 내역 데이터 삽입 */}
                        <div>Recent Trades Placeholder</div>
                    </Card>
                </Col>
            </Row>
        </div>
    );
};

export default ExchangePage;
