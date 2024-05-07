import APIClient from "api/apiClient";
import {CONFIG} from "common/config";

const baseApi = process.env.REACT_APP_API_ENDPOINT

export const getAllPublisher= () => {
    return APIClient.get(baseApi + CONFIG.ENDPOINT_API.PUBLISHER.GET_ALL_PUBLISHER)
}
