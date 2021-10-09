import { Layout } from "antd";
import { BrowserRouter as Router } from "react-router-dom";
import logo from "./img/logo.png";
import { renderRoutes, routes } from "./routes";
import "antd/dist/antd.css";
import "./App.css";
import { useEffect, useState } from "react";
import { getChilds } from "./content/Graph/utils/getChilds";

const { Header, Content, Footer } = Layout;

function App() {
    const [state, setState] = useState({
        data: null,
    });

    function fetchData() {
        fetch("https://bugprod-webtable.herokuapp.com/get-all-metadata", {
            headers: {
                "Content-Type": "application/json",
                sessionKey: "test",
            },
        })
            .then((res: Response) => {
                return res.json();
            })
            .then(
                (result) => {
                    console.log("result=>", result);

                    setState((prevState) => {
                        const updatedValues = {
                            data: {
                                name: "main",
                                children: [...result.map((r) => getChilds(r))],
                            },
                        };

                        return { ...prevState, ...updatedValues };
                    });

                    console.log(state);
                },
                (error) => {}
            );
    }

    useEffect(() => {
        fetchData();
    }, []);

    function onChange(pagination: any, filters: any, sorter: any, extra: any) {
        console.log("params", pagination, filters, sorter, extra);
    }

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
