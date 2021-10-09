import { Layout } from "antd";
import { BrowserRouter as Router } from "react-router-dom";
import logo from "./img/logo.png";
import { renderRoutes, routes } from "./routes";
import "antd/dist/antd.css";
import "./App.css";

const { Header, Content, Footer } = Layout;

function App() {
    return (
        <Router>
            <Layout>
                <Header style={{ padding: 0, backgroundColor: "#EBF3FE" }}>
                    <img src={logo} width={100} height={40} style={{ padding: 5, marginLeft: 15 }} />
                </Header>
                <Content style={{ margin: "24px 16px 0" }}>{renderRoutes(routes)}</Content>
                <Footer style={{ textAlign: "center" }}>Багетный прод © 2021</Footer>
            </Layout>
        </Router>
    );
}

export default App;
