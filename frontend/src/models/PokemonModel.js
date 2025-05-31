import * as Yup from "yup";

export const Pokemon = Yup.object().shape({
    id: Yup.number().required(),
    name: Yup.string().required(),
    health: Yup.number().required(),
    damage: Yup.number().required(),
    defense: Yup.number().required(),
    countryName: Yup.string().required(),
}).noUnknown()