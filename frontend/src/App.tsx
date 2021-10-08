import { Layout, Menu } from "antd";
import { UploadOutlined, UserOutlined, VideoCameraOutlined } from "@ant-design/icons";
import { BrowserRouter as Router, Link, Route } from "react-router-dom";
import { Filter } from "./content/Filter";
import { LoginForm } from "./content/form/Form";

import "antd/dist/antd.css";
import "./App.css";
import { MainTable } from "./content/Table/Table";

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
                <Sider
                    breakpoint="lg"
                    collapsedWidth="0"
                    onBreakpoint={(broken) => {
                        console.log(broken);
                    }}
                    onCollapse={(collapsed, type) => {
                        console.log(collapsed, type);
                    }}
                >
                    <div className="logo" />
                    <Menu theme="dark" mode="inline" defaultSelectedKeys={["4"]}>
                        <Menu.Item key="1" icon={<UserOutlined />}>
                            <Link to="/">Home</Link>
                        </Menu.Item>
                        <Menu.Item key="2" icon={<VideoCameraOutlined />}>
                            <Link to="/form">form</Link>
                        </Menu.Item>
                        <Menu.Item key="3" icon={<UploadOutlined />}>
                            <Link to="/filter">filter</Link>
                        </Menu.Item>
                    </Menu>
                </Sider>
                <Layout>
                    <Header style={{ padding: 0 }} />
                    <Content style={{ margin: "24px 16px 0" }}>
                        <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                            {/* <Table columns={columns} dataSource={data} onChange={onChange} /> */}
                            <MainTable />
                            <Route path="/filter">
                                <Filter />
                            </Route>
                            <Route path="/form">
                                <LoginForm />
                            </Route>
                        </div>
                    </Content>
                    <Footer style={{ textAlign: "center" }}>© 2021</Footer>
                </Layout>
            </Layout>
        </Router>
    );
}

export default App;
