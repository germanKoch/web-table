import React, { Suspense, Fragment, lazy } from "react";
import { Switch, Route, Redirect } from "react-router-dom";
import { Spin } from "antd";

// @ts-ignore
export const renderRoutes = (routes = []) => (
    <Suspense fallback={<Spin />}>
        <Switch>
            {routes.map((route, i) => {
                const Layout = route.layout || Fragment;
                const Component = route.component;
                const type = route.type;
                return (
                    <Route
                        key={i}
                        path={route.path}
                        exact={route.exact}
                        render={(props) => (
                            <Layout>
                                {route.routes ? renderRoutes(route.routes) : <Component type={type} {...props} />}
                            </Layout>
                        )}
                    />
                );
            })}
        </Switch>
    </Suspense>
);

export const routes = [
    {
        exact: true,
        path: "/",
        component: lazy((): any => import("./content/Form/Form")),
        title: "Auth",
    },
    {
        exact: true,
        path: "/home",
        component: lazy(() => import("./content/MainComponent")),
        title: "Home",
    },
    {
        exact: true,
        path: "/feature",
        component: lazy(() => import("./content/FeatureComponent")),
        title: "Feature",
    },
];
