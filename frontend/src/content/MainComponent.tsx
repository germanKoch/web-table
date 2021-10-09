import { MainTable } from "./Table/Table";
import { Row, Col } from "antd";

const MainComponent = () => {
    return (
        <Row className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
            <Col xl={24}>
                <MainTable />
            </Col>
        </Row>
    );
};
export default MainComponent;
