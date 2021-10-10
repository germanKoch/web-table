import React from "react";

import { getChilds } from "../Graph/utils/getChilds";
import { Button, TreeSelect } from "antd";
import { TreeNode } from "antd/lib/tree-select";

export class Join extends React.Component {
    // data;
    dataset: string = "";
    joinDataset: string = "";
    field: string = "";
    joinField: string = "";

    constructor(props: any) {
        super(props);
    }

    onChangeFirst(value) {
        this.dataset = value.split(".")[1];
        this.field = value.slice(this.dataset.length + 2);
    }

    onChangeSecond(value) {
        this.joinDataset = value.split(".")[1];
        this.joinField = value.slice(this.joinDataset.length + 2);
    }

    goJoinBro() {
        this.props.onJoin();
        const body = {
            dataset: this.dataset,
            field: this.field,
            joinDataset: this.joinDataset,
            joinField: this.joinField,
        };
        fetch("https://bugprod-webtable.herokuapp.com/join", {
            headers: {
                "Content-Type": "application/json",
                sessionKey: "test",
            },
            method: "POST",
            body: JSON.stringify(body),
        })
            .then((res: Response) => {
                return res.json();
            })
            .then(
                (result) => {
                    console.log("result=>", result);
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error,
                    });
                }
            );
    }

    renderTreeSelect(node, prevNodeName = "") {
        return node?.children?.map((ch) => {
            const value = ch.name;
            return node?.children ? (
                <TreeNode value={`${prevNodeName}.${value}`} title={value} key={Math.random()}>
                    {this.renderTreeSelect(ch, `${prevNodeName}.${value}`)}
                </TreeNode>
            ) : (
                <TreeNode value={`${prevNodeName}.${value}`} title={value} key={Math.random()}></TreeNode>
            );
        });
    }

    render() {
        return this.props.data ? (
            <div
                style={{
                    width: "400px",
                    boxShadow: "rgba(149, 157, 165, 0.2) 0px 8px 24px",
                    padding: "20px",
                    background: "white",
                }}
            >
                <span>Выберите поле из первого датасета</span>
                <TreeSelect
                    showSearch
                    style={{ width: "100%", marginBottom: "10px" }}
                    dropdownStyle={{ maxHeight: 400, overflow: "auto" }}
                    placeholder="Please select"
                    allowClear
                    treeDefaultExpandAll
                    onChange={this.onChangeFirst.bind(this)}
                >
                    {this.renderTreeSelect(this.props.data)}
                </TreeSelect>
                <span>Выберите поле из первого датасета</span>
                <TreeSelect
                    showSearch
                    style={{ width: "100%" }}
                    dropdownStyle={{ maxHeight: 400, overflow: "auto" }}
                    placeholder="Please select"
                    allowClear
                    treeDefaultExpandAll
                    onChange={this.onChangeSecond.bind(this)}
                >
                    {this.renderTreeSelect(this.props.data)}
                </TreeSelect>
                <Button onClick={this.goJoinBro.bind(this)} style={{ marginTop: "20px" }}>
                    Make Join
                </Button>
            </div>
        ) : (
            <div>Ниче нету пока что</div>
        );
    }
}
