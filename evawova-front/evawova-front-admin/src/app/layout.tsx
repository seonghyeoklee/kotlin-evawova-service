'use client';

import React from 'react';
import {Layout, Menu} from 'antd';
import {AntdRegistry} from '@ant-design/nextjs-registry';
import {
    DashboardOutlined,
    UserOutlined,
    SettingOutlined,
    LineChartOutlined,
    SwapOutlined, BarsOutlined, SyncOutlined
} from '@ant-design/icons';
import Link from "next/link";
import { MarketsProvider } from './MarketsContext';

const {Header, Content, Footer, Sider} = Layout;

// 메뉴 아이템 정의
const menuItems = [
    {
        key: '1',
        icon: <LineChartOutlined />,
        label: '시세 종목 조회',
        children: [
            {
                key: '1-1',
                icon: <BarsOutlined />,
                label: <Link href="/quotation/markets">종목 코드 조회</Link>
            },
            {
                key: '1-2',
                icon: <LineChartOutlined />,
                label: <Link href="/quotation/ticker">현재가 정보</Link>
            },
            {
                key: '1-3',
                icon: <SyncOutlined />,
                label: <Link href="/quotation/trade">체결 정보</Link>
            },
            {
                key: '1-4',
                icon: <SwapOutlined />,
                label: <Link href="/quotation/orderbook">호가 정보</Link>
            },
        ]
    },
    {
        key: '2',
        icon: <LineChartOutlined />,
        label: <Link href="/exchange">거래소</Link>,
    },
    {
        key: '3',
        icon: <DashboardOutlined/>,
        label: <Link href="/dashboard">통계</Link>,
    },
    {
        key: '4',
        icon: <UserOutlined/>,
        label: <Link href="/users">사용자</Link>,
    },
    {
        key: '5',
        icon: <SettingOutlined/>,
        label: <Link href="/settings">설정</Link>,
    },
];

const RootLayout = ({children}: React.PropsWithChildren) => (
    <MarketsProvider>
        <html lang="en">
            <body style={{height: '100%', margin: 0, overflow: 'hidden'}}>
                <AntdRegistry>
                    <Layout style={{minHeight: '100vh'}}>
                        {/* 사이드바 */}
                        <Sider collapsible width={250}>
                            <div
                                style={{
                                    height: 32,
                                    margin: 16,
                                    background: 'rgba(255, 255, 255, 0.3)',
                                    textAlign: 'center',
                                    lineHeight: '32px',
                                    color: 'white',
                                }}
                            >
                                DECO
                            </div>
                            <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline" items={menuItems}/>
                        </Sider>

                        {/* 메인 레이아웃 */}
                        <Layout>
                            {/* 헤더 */}
                            <Header style={{background: '#fff', padding: 0, textAlign: 'center'}}>
                                <h1 style={{margin: 0}}>Evawova Deco Admin</h1>
                            </Header>

                            {/* 콘텐츠 */}
                            <Content style={{margin: 0, padding: 24, background: '#f0f2f5'}}>
                                {children}
                            </Content>

                            {/* 푸터 */}
                            <Footer style={{textAlign: 'center'}}>
                                Evawova Deco Admin ©2024 Created with Ant Design
                            </Footer>
                        </Layout>
                    </Layout>
                </AntdRegistry>
            </body>
        </html>
    </MarketsProvider>
);

export default RootLayout;
