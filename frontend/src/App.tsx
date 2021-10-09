import { Layout } from "antd";
import { BrowserRouter as Router } from "react-router-dom";
import logo from './img/logo.png'
import {renderRoutes, routes} from "./routes";
import "antd/dist/antd.css";
import "./App.css";


const { Header, Content, Footer } = Layout;

const columns = [
    {
        title: "Item 1",
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
        title: "Item 2",
        dataIndex: "Item 2",
        sorter: {
            compare: (a: { math: number }, b: { math: number }) => a.math - b.math,
            multiple: 2,
        },
    },
    {
        title: "Item 3",
        dataIndex: "Item 3",
        sorter: {
            compare: (a: { english: number }, b: { english: number }) => a.english - b.english,
            multiple: 1,
        },
    },
];

const data = [
    {
        key: "1",
        name: "Item 1",
        chinese: 98,
        math: 60,
        english: 70,
    },
    {
        key: "2",
        name: "Item 2",
        chinese: 98,
        math: 66,
        english: 89,
    },
    {
        key: "3",
        name: "Item 3",
        chinese: 98,
        math: 90,
        english: 70,
    },
    {
        key: "4",
        name: "Item 4",
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
                    {renderRoutes(routes)}
                </Content>
                <Footer style={{textAlign: "center"}}>Багетный прод © 2021</Footer>
            </Layout>
        </Router>
    );
}

export default App;
