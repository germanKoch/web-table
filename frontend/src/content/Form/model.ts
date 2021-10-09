import { createForm } from "effector-forms";
import { createEffect, forward } from "effector";

export const loginForm = createForm({
    fields: {
        email: {
            init: "", // field's store initial value
            rules: [
                {
                    name: "email",
                    validator: (value: string) => /\S+@\S+\.\S+/.test(value)
                },
            ],
        },
        password: {
            init: "", // field's store initial value
            rules: [
                {
                    name: "required",
                    validator: (value: string) => Boolean(value),
                }
            ],
        },
    },
    validateOn: ["submit"],
})

export const loginFx = createEffect()

forward({
    from: loginForm.formValidated,
    to: loginFx,
})
