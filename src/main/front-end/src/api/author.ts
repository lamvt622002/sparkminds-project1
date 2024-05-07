import APIClient from "api/apiClient";
import {CONFIG} from "common/config";

const baseApi = process.env.REACT_APP_API_ENDPOINT

export const getAllAuthor = () => {
    return APIClient.get(baseApi + CONFIG.ENDPOINT_API.AUTHOR.GET_ALL_AUTHOR)
}
