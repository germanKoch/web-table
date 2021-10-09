import {useForm} from 'effector-forms'
import {useStore} from 'effector-react'
import {loginForm, loginFx} from "./model";
import {Form, Input, Button, Checkbox} from 'antd';
import {useEffect} from "react";
import {useHistory} from 'react-router-dom';

const LoginForm = () => {
    const {fields, submit, eachValid} = useForm(loginForm)
    const pending = useStore(loginFx.pending)
    const history = useHistory();

    //TODO: remove it
    useEffect(() => {
        if (fields.email['value'] === 'root' && fields.password['value'] === 'root') {
            history.push('/home');
        }
    }, [fields.email['value'], fields.password['value']])


    const onSubmit = (e: any) => {
        if (fields.email['value'] === 'root' && fields.password['value'] === 'root') {
            history.push('/home');
        }
        e.preventDefault()
        submit()
    }

    return (
        <Form
            // @ts-ignore
            onSubmit={onSubmit}
            name="basic"
            labelCol={{span: 5}}
            wrapperCol={{span: 5}}
            initialValues={{remember: true}}
            autoComplete="off"
        >
            <Form.Item
                label="Username"
                name="username"
                rules={[{required: true, message: 'Please input your username!'}]}
            >
                <Input
                    type="text"
                    value={fields.email.value}
                    disabled={pending}
                    onChange={(e) => fields.email.onChange(e.target.value)}
                />
                <div>
                    {fields.email.errorText({
                        "email": "enter email",
                    })}
                </div>
            </Form.Item>

            <Form.Item
                label="Password"
                name="password"
                rules={[{required: true, message: 'Please input your password!'}]}
            >
                <Input
                    type="password"
                    value={fields.password.value}
                    disabled={pending}
                    onChange={(e) => fields.password.onChange(e.target.value)}
                />
                <div>
                    {fields.password.errorText({
                        "required": "password required"
                    })}
                </div>
            </Form.Item>
            <Form.Item name="remember" valuePropName="checked" wrapperCol={{offset: 5, span: 5}}>
                <Checkbox>Remember me</Checkbox>
            </Form.Item>
            <Form.Item wrapperCol={{offset: 5, span: 5}}>
                <Button
                    disabled={!eachValid || pending}
                    // @ts-ignore
                    type="submit"
                >
                    Login
                </Button>
            </Form.Item>
        </Form>

    )
}

export default LoginForm