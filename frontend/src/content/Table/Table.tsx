import { Button, Space, Table } from "antd";
import React from "react";
import { MetaData } from "./model";


export class MainTable extends React.Component {
    state: { filteredInfo: any; sortedInfo: any; metadata: any } = {
        filteredInfo: {
            type: null,
            name: null,
            namespace: null,
            doc: null,
            fields: {
                name: null,
                type: null
            }
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
                (result: MetaData) => {
                    console.log('result=>', result)
                    this.setState({
                        metadata: {
                            isLoaded: true,
                            items: result
                        }
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
                type: null,
                name: null,
                namespace: null,
                doc: null,
                fields: {
                    name: null,
                    type: null
                }
            }
        });
    };

    clearAll = () => {
        this.setState({
            filteredInfo: {
                type: null,
                name: null,
                namespace: null,
                doc: null,
                fields: {
                    name: null,
                    type: null
                }
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
                title: "Namespace",
                dataIndex: "namespace",
                key: "namespace",
                filters: null,
                filteredValue: filteredInfo.namespace,
                onFilter: (value: any, record: { namespace: string | any[] }) => record.namespace.includes(value),
                sorter: (a: { namespace: string | any[] }, b: { namespace: string | any[] }) => a.namespace.length - b.namespace.length,
                sortOrder: sortedInfo.columnKey === "namespace" && sortedInfo.order,
                ellipsis: true,
            },
            {
                title: "Type",
                dataIndex: "type",
                key: "type",
                filters: null,
                filteredValue: filteredInfo.type,
                onFilter: (value: any, record: { type: string | any[] }) => record.type.includes(value),
                sorter: (a: { type: string | any[] }, b: { type: string | any[] }) => a.type.length - b.type.length,
                sortOrder: sortedInfo.columnKey === "type" && sortedInfo.order,
                ellipsis: true,
            },
            {
                title: "Doc",
                dataIndex: "doc",
                key: "doc",
                filters: null,
                filteredValue: filteredInfo.doc,
                onFilter: (value: any, record: { doc: string | any[] }) => record.doc.includes(value),
                sorter: (a: { doc: string | any[] }, b: { doc: string | any[] }) => a.doc.length - b.doc.length,
                sortOrder: sortedInfo.columnKey === "doc" && sortedInfo.order,
                ellipsis: true,
            },
            {
                title: "fields",
                dataIndex: "fields['name']",
                key: "fields['name']",
                ellipsis: true,
                render: (fields: any) => {
                    console.log(fields)
                    return fields;
                }
            },
        ];
        return (
            <React.Fragment>
                <Space style={{marginBottom: 16}}>
                    <Button onClick={this.setAgeSort}>Sort item</Button>
                    <Button onClick={this.clearFilters}>Clear filters</Button>
                    <Button onClick={this.clearAll}>Clear filters and sorters</Button>
                </Space>
                <Table columns={columns} dataSource={this.state.metadata.items} onChange={this.handleChange}/>
            </React.Fragment>
        );
    }
}
