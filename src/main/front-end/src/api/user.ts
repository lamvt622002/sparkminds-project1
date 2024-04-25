import APIClient from "api/apiClient";
import {CONFIG} from "common/config";

const baseApi = process.env.REACT_APP_API_ENDPOINT

export const changePassword = (oldPassword:string, newPassword:string,  confirmNewPassword:String) => {
    return APIClient.post(baseApi + CONFIG.ENDPOINT_API.USER.CHANGE_PASSWORD, { oldPassword:oldPassword, newPassword:newPassword, confirmNewPassword:confirmNewPassword})
}

export const changeEmail = (password:string, email:string) => {
  return APIClient.post(baseApi + CONFIG.ENDPOINT_API.USER.CHANGE_EMAIL, { password:password, newEmail:email })
}

export const changePhone = (password:string, phoneNumber:string) => {
    return APIClient.post(baseApi + CONFIG.ENDPOINT_API.USER.CHANGE_PHONE, { password:password, phoneNumber:phoneNumber })
}

export const changePhoneVerify = (otp:string) => {
    return APIClient.post(baseApi + CONFIG.ENDPOINT_API.USER.CHANGE_PHONE_VERIFY, { otp:otp})
}