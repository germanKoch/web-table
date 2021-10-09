import React from 'react'
import {MainTable} from "./Table/Table";
import {Graph} from "./Graph/Graph";
import {Row,Col} from "antd";


const MainComponent = () => {
    return (
        <Row className="site-layout-background" style={{padding: 24, minHeight: 360 }}>
            <Col xl={24}>
                <MainTable/>
            </Col>

            <Col xl={24}>
                <Graph />
            </Col>

        </Row>
    )
}
export default MainComponent