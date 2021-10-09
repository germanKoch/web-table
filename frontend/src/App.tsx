import { Layout } from "antd";
import { BrowserRouter as Router } from "react-router-dom";
import logo from './img/logo.png'

import "antd/dist/antd.css";
import "./App.css";
import { MainTable } from "./content/Table/Table";
import { Graph } from "./content/Graph/Graph";

const { Header, Content, Footer, Sider } = Layout;

const columns = [
    {
        title: "Name",
        dataIndex: "name",
    },
    {
        title: "Chinese Score",
        dataIndex: "chinese",
        sorter: {
            compare: (a: { chinese: number }, b: { chinese: number }) => a.chinese - b.chinese,
            multiple: 3,
        },
    },
    {
        title: "Math Score",
        dataIndex: "math",
        sorter: {
            compare: (a: { math: number }, b: { math: number }) => a.math - b.math,
            multiple: 2,
        },
    },
    {
        title: "English Score",
        dataIndex: "english",
        sorter: {
            compare: (a: { english: number }, b: { english: number }) => a.english - b.english,
            multiple: 1,
        },
    },
];

const data = [
    {
        key: "1",
        name: "John Brown",
        chinese: 98,
        math: 60,
        english: 70,
    },
    {
        key: "2",
        name: "Jim Green",
        chinese: 98,
        math: 66,
        english: 89,
    },
    {
        key: "3",
        name: "Joe Black",
        chinese: 98,
        math: 90,
        english: 70,
    },
    {
        key: "4",
        name: "Jim Red",
        chinese: 88,
        math: 99,
        english: 89,
    },
];

function App() {
    function onChange(pagination: any, filters: any, sorter: any, extra: any) {
        console.log("params", pagination, filters, sorter, extra);
    }

    return (
        <Router>
            <Layout>
                <Header style={{padding: 0, backgroundColor: '#EBF3FE' }}>
                    <img src={logo} width={100} height={40} style={{padding: 5, marginLeft: 15 }} />
                </Header>
                <Content style={{margin: "24px 16px 0"}}>
                    <div className="site-layout-background" style={{padding: 24, minHeight: 360 }}>
                        {/* <Table columns={columns} dataSource={data} onChange={onChange} /> */}
                        {/* <MainTable/> */}
                        <Graph />
                    </div>
                </Content>
                <Footer style={{textAlign: "center"}}>Багетный прод © 2021</Footer>
                <Footer style={{ textAlign: "center" }}>© 2021</Footer>
            </Layout>
        </Router>
    );
}

export default App;
