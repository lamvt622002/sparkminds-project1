import APIClient from "api/apiClient";
import {CONFIG} from "common/config";

const baseApi = process.env.REACT_APP_API_ENDPOINT

export const getAllCategory = () => {
    return APIClient.get(baseApi + CONFIG.ENDPOINT_API.CATEGORY.GET_ALL_CATEGORY)
}
