import { Button, Input } from "antd";
import SearchOutlined from "@ant-design/icons/SearchOutlined";

export const getColumnSearchProps = (dataIndex: string) => ({
    filterDropdown: ({ setSelectedKeys, selectedKeys, confirm }) => (
        <div style={{ padding: 8 }}>
            <Input
                placeholder={`Search ${dataIndex}`}
                value={selectedKeys[0]}
                onChange={(e) => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                onPressEnter={() => confirm()}
                style={{ width: 188, marginBottom: 8, display: "block" }}
            />
            <Button type="primary" onClick={() => confirm()} size="small" style={{ width: 90, marginRight: 8 }}>
                Search
            </Button>
        </div>
    ),
    filterIcon: (filtered) => <SearchOutlined type="search" style={{ color: filtered ? "#1890ff" : undefined }} />,
    onFilter: (value, record) => record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
});
