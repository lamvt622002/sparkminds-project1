import APIClient from "api/apiClient";
import {CONFIG} from "common/config";

const baseApi = process.env.REACT_APP_API_ENDPOINT

export const getAllLanguage = () => {
    return APIClient.get(baseApi + CONFIG.ENDPOINT_API.BOOK_LANGUAGE.GET_ALL_BOOK_LANGUAGE)
}
