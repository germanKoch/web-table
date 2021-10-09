import { Layout, Menu } from "antd";
import { BrowserRouter as Router, Link, NavLink } from "react-router-dom";
import logo from "./img/logo.png";
import { renderRoutes, routes } from "./routes";
import "antd/dist/antd.css";
import "./App.css";
import { useState } from "react";

const { Header, Content, Footer } = Layout;

function App() {
    const [state, setState] = useState({
        current: "home",
    });

    function handleClick(e) {
        console.log("click ", e);
        setState({ current: e.key });
    }

    return (
        <Router>
            <Layout>
                <Header
                    style={{
                        padding: 0,
                        backgroundColor: "#EBF3FE",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "space-between",
                    }}
                >
                    <img src={logo} width={100} height={40} style={{ padding: 5, marginLeft: 15 }} />
                    <Menu
                        onClick={handleClick}
                        selectedKeys={[state.current]}
                        mode="horizontal"
                        style={{ width: "15%" }}
                    >
                        <Menu.Item key="home">
                            <NavLink to="/home">Datasets</NavLink>
                        </Menu.Item>
                        <Menu.Item key="feature">
                            <NavLink to="/feature">Feature</NavLink>
                        </Menu.Item>
                    </Menu>
                </Header>
                <Content style={{ margin: "24px 16px 0" }}>{renderRoutes(routes)}</Content>
                <Footer style={{ textAlign: "center" }}>Багетный прод © 2021</Footer>
            </Layout>
        </Router>
    );
}

export default App;
