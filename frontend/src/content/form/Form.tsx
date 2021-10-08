import { useForm } from 'effector-forms'
import { useStore } from 'effector-react'
import { loginForm, loginFx } from "./model";

export const LoginForm = () => {
  const { fields, submit, eachValid } = useForm(loginForm)
  const pending = useStore(loginFx.pending)

  const onSubmit = (e: any) => {
    console.log(123, e);
    e.preventDefault()
    submit()
  }

  return (
      <form onSubmit={onSubmit}>
        <input
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
        <input
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
        <button
            disabled={!eachValid || pending}
            type="submit"
        >
          Login
        </button>
      </form>
  )
}
