'use client';

import React from 'react';
import { Spin } from 'antd';

interface LoadingSpinnerProps {
    tip?: string; // 로딩 메시지
    fullScreen?: boolean; // 전체 화면 로딩 여부
}

const LoadingSpinner: React.FC<LoadingSpinnerProps> = ({ tip = '', fullScreen = false }) => {
    if (fullScreen) {
        return (
            <div
                style={{
                    height: '100vh',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    backgroundColor: 'rgba(255, 255, 255, 0.8)', // 배경색
                    position: 'fixed',
                    top: 0,
                    left: 0,
                    width: '100%',
                    zIndex: 1000,
                }}
            >
                <Spin tip={tip}>
                    <div style={{ height: '100px' }} /> {/* Placeholder */}
                </Spin>
            </div>
        );
    }

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100px' }}>
            <Spin tip={tip}>
                <div style={{ height: '100px' }} /> {/* Placeholder */}
            </Spin>
        </div>
    );
};

export default LoadingSpinner;
