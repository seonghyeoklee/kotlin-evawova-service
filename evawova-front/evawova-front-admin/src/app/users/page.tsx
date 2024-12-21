'use client';

import React from 'react';
import {Table, Typography} from 'antd';

// 유저 데이터 예제
const userData = [
    {key: '1', name: 'John Doe', email: 'john.doe@example.com', role: 'Admin'},
    {key: '2', name: 'Jane Smith', email: 'jane.smith@example.com', role: 'Editor'},
    {key: '3', name: 'Bob Johnson', email: 'bob.johnson@example.com', role: 'Viewer'},
];

// 테이블 컬럼 정의
const columns = [
    {title: 'Name', dataIndex: 'name', key: 'name'},
    {title: 'Email', dataIndex: 'email', key: 'email'},
    {title: 'Role', dataIndex: 'role', key: 'role'},
];

export default function UsersPage() {
    return (
        <div>
            <Typography.Title level={2}>Users</Typography.Title>
            <Table dataSource={userData} columns={columns}/>
        </div>
    );
}