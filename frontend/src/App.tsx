import { Layout, Menu } from 'antd';
import { UploadOutlined, UserOutlined, VideoCameraOutlined } from '@ant-design/icons';
import { BrowserRouter as Router, Link, Route } from 'react-router-dom';
import { Filter } from "./content/Filter";
import { LoginForm } from "./content/form/Form";

import "antd/dist/antd.css";
import "./App.css";

const {Header, Content, Footer, Sider} = Layout;

function App() {

  return (
      <Router>
        <Layout>
          <Sider
              breakpoint="lg"
              collapsedWidth="0"
              onBreakpoint={broken => {
                console.log(broken);
              }}
              onCollapse={(collapsed, type) => {
                console.log(collapsed, type);
              }}
          >
            <div className="logo"/>
            <Menu theme="dark" mode="inline" defaultSelectedKeys={['4']}>
              <Menu.Item key="1" icon={<UserOutlined/>}>
                <Link to="/">Home</Link>
              </Menu.Item>
              <Menu.Item key="2" icon={<VideoCameraOutlined/>}>
                <Link to="/form">form</Link>
              </Menu.Item>
              <Menu.Item key="3" icon={<UploadOutlined/>}>
                <Link to="/filter">filter</Link>
              </Menu.Item>
            </Menu>
          </Sider>
          <Layout>
            <Header style={{padding: 0}}/>
            <Content style={{margin: '24px 16px 0'}}>
              <div className="site-layout-background" style={{padding: 24, minHeight: 360}}>
                Хеловорлд
                <Route path="/filter">
                  <Filter/>
                </Route>
                <Route path="/form">
                  <LoginForm/>
                </Route>
              </div>
            </Content>
            <Footer style={{textAlign: 'center'}}>© 2021</Footer>
          </Layout>
        </Layout>
      </Router>
  );
}

export default App
