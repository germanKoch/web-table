import { Row, Col, Button } from "antd";
import { useEffect, useState } from "react";
import { Graph } from "./Graph/Graph";
import { getChilds } from "./Graph/utils/getChilds";
import { Join } from "./Join/Join";

const FeatureComponent = () => {
    const [state, setState] = useState({
        data: null,
    });

    function downloadData() {
        fetch("https://bugprod-webtable.herokuapp.com/export", {
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
                    donwloadJson(result);
                    console.log("result=>", result);
                },
                (error) => {}
            );
    }

    function donwloadJson(storageObj) {
        console.log(storageObj);
        var dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(storageObj));
        var dlAnchorElem = document.createElement("a");
        dlAnchorElem.setAttribute("href", dataStr);
        dlAnchorElem.setAttribute("download", "schema.json");
        dlAnchorElem.click();
    }

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

    return (
        <Row className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
            <Col xl={24}>
                <div style={{ display: "flex", alignItems: "end", marginBottom: "20px" }}>
                    <Join onJoin={(e) => fetchData()} data={state.data} />
                    <Button type="primary" style={{ marginLeft: "20px" }} onClick={downloadData}>
                        Выгрузить
                    </Button>
                </div>

                <Graph onUpdate={(e) => fetchData()} data={state.data} />
            </Col>
        </Row>
    );
};

export default FeatureComponent;
