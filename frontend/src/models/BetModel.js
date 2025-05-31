import * as Yup from "yup";
import {MatchEvent} from "@/models/MatchEventModel";

export const Bet = Yup.object().shape({
    id: Yup.number().required(),
    amount: Yup.number().required(),
    date: Yup.string().required(),
    status: Yup.mixed()
        .oneOf(["WIN", "LOSE", "TBD"])
        .required(),
    matchEvents: Yup.array().of(MatchEvent).required(),
    coefficient: Yup.number().required(),
    winnings: Yup.number().required(),
}).noUnknown()