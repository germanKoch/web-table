import { useForm } from 'effector-forms'
import { useStore } from 'effector-react'
import { loginForm, loginFx } from "./model";
import { Form, Input, Button, Checkbox } from 'antd';

export const LoginForm = () => {
  const { fields, submit, eachValid } = useForm(loginForm)
  const pending = useStore(loginFx.pending)

  const onSubmit = (e: any) => {
    console.log(123, e);
    e.preventDefault()
    submit()
  }

  return (
      <Form onSubmit={onSubmit}
            name="basic"
            labelCol={{ span: 5 }}
            wrapperCol={{ span: 5 }}
            initialValues={{ remember: true }}
            autoComplete="off"
      >
          <Form.Item
              label="Username"
              name="username"
              rules={[{ required: true, message: 'Please input your username!' }]}
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
              label="Username"
              name="username"
              rules={[{ required: true, message: 'Please input your username!' }]}
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
          <Form.Item name="remember" valuePropName="checked" wrapperCol={{ offset: 5, span: 5 }}>
              <Checkbox>Remember me</Checkbox>
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 5, span: 5 }}>
              <Button
                  disabled={!eachValid || pending}
                  type="submit"
              >
                  Login
              </Button>
          </Form.Item>
      </Form>

  )
}
