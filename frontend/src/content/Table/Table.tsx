import { Button, Space, Table } from "antd";
import React from "react";
import { getColumnSearchProps } from "./columnSearchProp";

const data = [
    {
        key: "1",
        name: "Item 1",
        age: 32,
        address: "Item",
    },
    {
        key: "2",
        name: "Item 2",
        age: 42,
        address: "Item",
    },
    {
        key: "3",
        name: "Item 3",
        age: "32",
        address: "Item",
    },
    {
        key: "4",
        name: "Item 4",
        age: 32,
        address: "Item",
    },
];

export class MainTable extends React.Component {
    state: { filteredInfo: any; sortedInfo: any; metadata: any } = {
        filteredInfo: {
            age: null,
            name: null
        },
        sortedInfo: {
            columnKey: null,
            order: null
        },
        metadata: {
            error: null,
            isLoaded: false,
            items: []
        }
    };

    constructor(props: any) {
        super(props);
    }

    componentDidMount() {
        fetch("https://bugprod-webtable.herokuapp.com/get-all-metadata", {
            headers: {
                'Content-Type': 'application/json',
                'sessionKey': 'test1'
            },
        })
            .then((res: Response) => {
                return res.json();
            })
            .then(
                (result) => {
                    console.log('result=>', result)
                    this.setState({
                        isLoaded: true,
                        items: result
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }

    handleChange = (pagination: any, filters: any, sorter: any) => {
        console.log("Various parameters", pagination, filters, sorter);
        this.setState({
            filteredInfo: filters,
            sortedInfo: sorter,
        });
    };

    clearFilters = () => {
        this.setState({
            filteredInfo: {
                age: null,
                name: null
            }
        });
    };

    clearAll = () => {
        this.setState({
            filteredInfo: {
                age: null,
                name: null
            },
            sortedInfo: {
                columnKey: null,
                order: null
            },
        });
    };

    setAgeSort = () => {
        this.setState({
            sortedInfo: {
                order: "descend",
                columnKey: "age",
            },
        });
    };

    render() {
        let {sortedInfo, filteredInfo} = this.state;

        const columns: any = [
            {
                title: "Item 1  ",
                dataIndex: "name",
                key: "name",
                filters: [
                    {text: "Joe", value: "Joe"},
                    {text: "Jim", value: "Jim"},
                ],
                filteredValue: filteredInfo.name,
                onFilter: (value: any, record: { name: string | any[] }) => record.name.includes(value),
                sorter: (a: { name: string | any[] }, b: { name: string | any[] }) => a.name.length - b.name.length,
                sortOrder: sortedInfo.columnKey === "name" && sortedInfo.order,
                ellipsis: true,
            },
            {
                title: "Item 2",
                dataIndex: "age",
                key: "age",
                ...getColumnSearchProps("age"),
                filteredValue: filteredInfo.age,
                sorter: (a: { age: number }, b: { age: number }) => a.age - b.age,
                sortOrder: sortedInfo.columnKey === "age" && sortedInfo.order,
                ellipsis: true,
            },
            {
                title: "Item 3",
                dataIndex: "address",
                key: "address",
                filters: [
                    {text: "Item", value: "London"},
                    {text: "Item", value: "New York"},
                ],
                filteredValue: filteredInfo.address,
                onFilter: (value: any, record: { address: string | any[] }) => record.address.includes(value),
                sorter: (a: { address: string | any[] }, b: { address: string | any[] }) =>
                    a.address.length - b.address.length,
                sortOrder: sortedInfo.columnKey === "address" && sortedInfo.order,
                ellipsis: true,
            },
        ];
        return (
            <React.Fragment>
                <Space style={{marginBottom: 16}}>
                    <Button onClick={this.setAgeSort}>Sort item</Button>
                    <Button onClick={this.clearFilters}>Clear filters</Button>
                    <Button onClick={this.clearAll}>Clear filters and sorters</Button>
                </Space>
                <Table columns={columns} dataSource={data} onChange={this.handleChange}/>
            </React.Fragment>
        );
    }
}
