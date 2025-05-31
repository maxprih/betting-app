import * as Yup from "yup";

export const Transaction = Yup.object().shape({
    id: Yup.number().required(),
    amount: Yup.number().required(),
    date: Yup.string().required(),
    type: Yup.string().required(),
}).noUnknown()