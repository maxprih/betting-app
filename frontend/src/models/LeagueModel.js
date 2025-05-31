import * as Yup from "yup";

export const League = Yup.object().shape({
    id: Yup.number().required(),
    name: Yup.string().required(),
    startDate: Yup.string().required(),
    endDate: Yup.string().required()
}).noUnknown()