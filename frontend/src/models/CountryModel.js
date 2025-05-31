import * as Yup from "yup";

export const Country = Yup.object().shape({
    id: Yup.number().required(),
    name: Yup.string().required(),
}).noUnknown()