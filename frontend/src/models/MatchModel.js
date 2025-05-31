import * as Yup from "yup";
import {MatchEvent} from "@/models/MatchEventModel";

export const Match = Yup.object().shape({
    id: Yup.number().required(),
    firstPlayerName: Yup.string().required(),
    secondPlayerName: Yup.string().required(),
    date: Yup.string().required(),
    leagueName: Yup.string().required(),
    countryName: Yup.string().required(),
    matchEvents: Yup.array().of(MatchEvent).required()
}).noUnknown()