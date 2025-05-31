import * as Yup from "yup";

export const MatchEvent = Yup.object().shape({
    id: Yup.number().required(),
    name: Yup.string().required(),
    coefficient: Yup.number().required(),
    matchId: Yup.number().required(),
    matchFirstPlayerName: Yup.string().required(),
    matchSecondPlayerName: Yup.string().required(),
    matchDate: Yup.string().required(),
    status: Yup.mixed()
        .oneOf(["WIN", "LOSE", "TBD"])
        .required(),
}).noUnknown()